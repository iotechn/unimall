package com.iotechn.unimall.app.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.GroupShopDO;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.enums.GroupShopAutomaticRefundType;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/21.
 */
@Component
@EnableScheduling
public class CheckQuartz {

    private static final Logger logger = LoggerFactory.getLogger(CheckQuartz.class);
    private static final String ORDER_STATUS_LOCK = "ORDER_STATUS_QUARTZ_LOCK";
    private static final String GROUP_SHOP_START_LOCK = "GROUP_SHOP_START_LOCK";
    private static final String GROUP_SHOP_END_LOCK = "GROUP_SHOP_END_LOCK";
    private static final String GROUP_SHOP_LOCK_LOCK = "GROUP_SHOP_LOCK_LOCK";
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderBizService orderBizService;
    @Autowired
    private GroupShopMapper groupShopMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private OrderSkuMapper orderSkuMapper;
    @Autowired
    private LockComponent lockComponent;
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 订单状态定时轮训
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkOrderStatus() {
        if (lockComponent.tryLock(ORDER_STATUS_LOCK, 15)) {
            try {
                Date now = new Date();
                List<OrderDO> nos = orderMapper.selectExpireOrderNos(OrderStatusType.UNPAY.getCode(), new Date(now.getTime() - 1000l * 60 * 15));
                if (!CollectionUtils.isEmpty(nos)) {
                    nos.forEach(no -> {
                        try {
                            OrderDO updateOrderDO = new OrderDO();
                            updateOrderDO.setStatus(OrderStatusType.CANCELED_SYS.getCode());
                            updateOrderDO.setGmtUpdate(now);
                            List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_id", no.getId()));
                            orderSkuList.forEach(item -> {
                                skuMapper.returnSkuStock(item.getSkuId(), item.getNum());
                            });
                            orderBizService.changeOrderStatus(no.getOrderNo(), OrderStatusType.UNPAY.getCode(), updateOrderDO);
                        } catch (Exception e) {
                            logger.error("[未付款检测] 异常", e);
                        }
                    });
                }
                //15分钟执行一次
                long minutes = (now.getTime() / (1000 * 60));
                if (minutes % 15 == 0) {
                    List<OrderDO> waitConfirmNos = orderMapper.selectExpireOrderNos(OrderStatusType.WAIT_CONFIRM.getCode(), new Date(now.getTime() - 1000l * 60 * 60 * 24 * 7));
                    waitConfirmNos.forEach(item -> {
                        try {
                            OrderDO updateOrderDO = new OrderDO();
                            updateOrderDO.setStatus(OrderStatusType.WAIT_APPRAISE.getCode());
                            updateOrderDO.setGmtUpdate(now);
                            List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_id", item.getId()));
                            orderSkuList.forEach(skuItem -> {
                                skuMapper.decSkuFreezeStock(skuItem.getSkuId(), skuItem.getNum());
                            });
                            orderBizService.changeOrderStatus(item.getOrderNo(), OrderStatusType.WAIT_CONFIRM.getCode(), updateOrderDO);
                        } catch (Exception e) {
                            logger.error("[未确认检测] 异常", e);
                        }
                    });
                }
            } catch (Exception e) {
                logger.error("[订单状态检测定时任务] 异常", e);
            } finally {
                lockComponent.release(ORDER_STATUS_LOCK);
            }
        }
    }

    /**
     * 设定60s跑一次,团购商品到期自动退款,改变状态
     */
    @Scheduled(cron = "10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void groupShopStart() throws Exception {
        if (lockComponent.tryLock(GROUP_SHOP_START_LOCK, 30)) {
            try {
                Date now = new Date();
                /**
                 * 1. 激活 团购时间开始的活动商品
                 */
                // 1.1 查询在活动期间,且冻结状态的团购商品
                List<GroupShopDO> groupShopDOList = groupShopMapper.selectList(new EntityWrapper<GroupShopDO>()
                        .le("gmt_start", now).and()
                        .gt("gmt_end", now).and()
                        .eq("status", StatusType.LOCK.getCode()));
                if (groupShopDOList != null) {
                    for (GroupShopDO groupShopDO : groupShopDOList) {
                        groupShopDO.setGmtUpdate(now);
                        groupShopDO.setStatus(StatusType.ACTIVE.getCode());
                        SpuDO spuDO = spuMapper.selectById(groupShopDO.getSpuId());
                        if (spuDO == null) {
                            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXITS);
                        }

                        // 1.2 检查商品是不是已经下架
                        if (spuDO.getStatus().equals(StatusType.ACTIVE.getCode())) {
                            if (groupShopMapper.updateById(groupShopDO) <= 0) {
                                throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_UPDATE_SQL_QUERY_ERROR);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("[团购开始 定时任务] 异常", e);
                throw e;
            } finally {
                lockComponent.release(GROUP_SHOP_START_LOCK);
            }
        }


    }

    @Scheduled(cron = "10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void groupShopEnd() throws Exception {
        if (lockComponent.tryLock(GROUP_SHOP_END_LOCK, 30)) {
            try {
                Date now = new Date();
                /**
                 * 2. 冻结 团购时间结束的活动商品,并根据对应情况处理订单
                 */
                Wrapper<GroupShopDO> wrapper = new EntityWrapper<GroupShopDO>()
                        .eq("status", StatusType.ACTIVE.getCode())
                        .andNew()
                        .gt("gmt_start", now)
                        .or()
                        .le("gmt_end", now);
                List<GroupShopDO> lockGroupShopDOList = groupShopMapper.selectList(wrapper);
                // 2.2 将团购订单的状态转为对应的退款或待出库状态,对未达人数且自动退款的商品订单进行退款,对达到人数或不自动退款的商品订单转换状态
                if (!CollectionUtils.isEmpty(lockGroupShopDOList)) {
                    // 2.1 对过期团购商品冻结.
                    GroupShopDO lockGroupShopDO = new GroupShopDO();
                    lockGroupShopDO.setStatus(StatusType.LOCK.getCode());
                    lockGroupShopDO.setGmtUpdate(now);
                    groupShopMapper.update(lockGroupShopDO, wrapper);
                    for (GroupShopDO groupShopDO : lockGroupShopDOList) {
                        // 2.2.1查询团购订单中数据
                        List<OrderDO> lockOrderList = orderMapper.selectList(
                                new EntityWrapper<OrderDO>()
                                        .eq("group_shop_id", groupShopDO.getId())
                                        .eq("status", OrderStatusType.GROUP_SHOP_WAIT.getCode()));

                        if (CollectionUtils.isEmpty(lockOrderList)) {
                            continue;
                        }

                        if (groupShopDO.getAutomaticRefund() == GroupShopAutomaticRefundType.YES.getCode() && groupShopDO.getAlreadyBuyNumber().compareTo(groupShopDO.getMinimumNumber()) < 0) {
                            // 2.2.2.1.退款
                            logger.info("[团购结束] 退款逻辑 groupShopId:" + groupShopDO.getId());
                            for (OrderDO orderDO : lockOrderList) {
                                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                    @Override
                                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                                        try {
                                            //对订单退款保证原子性，仅退款成功的单子，变更状态
                                            orderBizService.groupShopStatusRefund(orderDO.getOrderNo());
                                            logger.info("[团购订单退款] 完成 orderNo:" + orderDO.getOrderNo());
                                        } catch (Exception e) {
                                            logger.error("[团购订单退款] 异常 orderNo:" + orderDO.getOrderNo() + "; errmsg:" + e.getMessage());
                                            transactionStatus.setRollbackOnly();
                                        }
                                    }
                                });
                            }
                        } else {
                            logger.info("[团购结束] 发货逻辑 groupShopId:" + groupShopDO.getId());
                            // 2.2.2.2 转换订单为待出货状态 (非自动退款场景)
                            List<Long> collect = lockOrderList.stream().map(s -> s.getId()).collect(Collectors.toList());
                            OrderDO orderDO = new OrderDO();
                            orderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
                            orderMapper.update(orderDO, (
                                    new EntityWrapper<OrderDO>()
                                            .in("id", collect)
                                            .eq("status", OrderStatusType.GROUP_SHOP_WAIT.getCode())));
                        }

                    }
                }
            } catch (Exception e) {
                logger.error("[团购结束 定时任务] 异常", e);
                throw e;
            } finally {
                lockComponent.release(GROUP_SHOP_END_LOCK);
            }
        }

    }

    @Scheduled(cron = "10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void groupShopLock() throws Exception {
        if (lockComponent.tryLock(GROUP_SHOP_LOCK_LOCK, 30)) {
            try {
                Date now = new Date();
                /**
                 * 3 冻结 团购活动期间却被下架的商品
                 */
                EntityWrapper<GroupShopDO> groupShopDOEntityWrapper = new EntityWrapper<>();

                // 3.1 从团购中查询活动期间的商品
                groupShopDOEntityWrapper.eq("status", StatusType.ACTIVE.getCode())
                        .and()
                        .le("gmt_start", now)
                        .and()
                        .gt("gmt_end", now);
                List<GroupShopDO> groupShopDOS = groupShopMapper.selectList(groupShopDOEntityWrapper);
                if (!CollectionUtils.isEmpty(groupShopDOS)) {
                    List<Long> spuIdList = groupShopDOS.stream().map(t -> t.getSpuId()).collect(Collectors.toList());

                    // 3.2 在团购中查询给出spuID,是否有被下架的商品
                    List<SpuDO> spuDOS = spuMapper.selectList(new EntityWrapper<SpuDO>().in("id", spuIdList).eq("status", StatusType.LOCK.getCode()));
                    if (!CollectionUtils.isEmpty(spuDOS)) {
                        List<Long> collect = spuDOS.stream().map(t -> t.getId()).collect(Collectors.toList());
                        GroupShopDO groupShopDO = new GroupShopDO();
                        groupShopDO.setStatus(StatusType.LOCK.getCode());
                        groupShopDO.setGmtUpdate(now);
                        groupShopMapper.update(groupShopDO, (new EntityWrapper<GroupShopDO>().in("spu_id", collect)));
                    }
                }
            } catch (Exception e) {
                logger.error("[团购锁定 定时任务] 异常", e);
                throw e;
            } finally {
                lockComponent.release(GROUP_SHOP_LOCK_LOCK);
            }
        }

    }

}
