package com.iotechn.unimall.app.quartz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.constant.LockConst;
import com.iotechn.unimall.data.domain.GroupShopDO;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.enums.DMQHandlerType;
import com.iotechn.unimall.data.enums.GroupShopAutomaticRefundType;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.biz.mq.DelayedMessageQueue;
import com.iotechn.unimall.data.properties.UnimallOrderProperties;
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

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderBizService orderBizService;
    @Autowired
    private GroupShopMapper groupShopMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private UnimallOrderProperties unimallOrderProperties;
    @Autowired
    private LockComponent lockComponent;
    @Autowired
    private DelayedMessageQueue delayedMessageQueue;
    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 此定时任务是算是保险，防止Redis延迟消息丢失的情况
     * 订单状态定时轮训,检查是否存在需要取消订单，需要收货订单
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkOrderStatus() {
        if (lockComponent.tryLock(LockConst.SCHEDULED_ORDER_STATUS_CHECK_LOCK, 30)) {
            Date now = new Date();
            // 1.检查是否存在需要自动取消的订单（即redis过期回调失败），将检查出的订单延时一秒放入延时队列
            QueryWrapper<OrderDO> cancelWrapper = new QueryWrapper<OrderDO>();
            cancelWrapper.select("id", "order_no");
            cancelWrapper.eq("status", OrderStatusType.UNPAY.getCode());
            Date cancelTime = new Date(now.getTime() - unimallOrderProperties.getAutoCancelTime() * 1000);
            cancelWrapper.lt("gmt_update", cancelTime);
            List<OrderDO> cancelList = orderMapper.selectList(cancelWrapper);
            if (!CollectionUtils.isEmpty(cancelList)) {
                cancelList.stream().forEach(item -> {
                    delayedMessageQueue.publishTask(DMQHandlerType.ORDER_AUTO_CANCEL.getCode(), item.getOrderNo(), 1);
                });
            }

            // 2.检查是否存在需要自动收货的订单（即redis过期回调失败），将检查出的订单延时一秒放入延时队列
            QueryWrapper<OrderDO> confirmWrapper = new QueryWrapper<OrderDO>();
            confirmWrapper.select("id", "order_no");
            confirmWrapper.eq("status", OrderStatusType.WAIT_CONFIRM.getCode());
            Date confirmTime = new Date(now.getTime() - unimallOrderProperties.getAutoConfirmTime() * 1000);
            confirmWrapper.lt("gmt_update", confirmTime);
            List<OrderDO> confirmList = orderMapper.selectList(confirmWrapper);
            if (!CollectionUtils.isEmpty(confirmList)) {
                confirmList.stream().forEach(item -> {
                    delayedMessageQueue.publishTask(DMQHandlerType.ORDER_AUTO_CONFIRM.getCode(), item.getOrderNo(), 1);
                });
            }
        }
    }

    /**
     * 设定60s跑一次,团购商品到期自动退款,改变状态
     */
    @Scheduled(cron = "10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void groupShopStart() throws Exception {
        if (lockComponent.tryLock(LockConst.GROUP_SHOP_START_LOCK, 30)) {
            try {
                Date now = new Date();
                /**
                 * 1. 激活 团购时间开始的活动商品
                 */
                // 1.1 查询在活动期间,且冻结状态的团购商品
                List<GroupShopDO> groupShopDOList = groupShopMapper.selectList(new QueryWrapper<GroupShopDO>()
                        .le("gmt_start", now)
                        .gt("gmt_end", now)
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
                lockComponent.release(LockConst.GROUP_SHOP_START_LOCK);
            }
        }


    }

    @Scheduled(cron = "10 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void groupShopEnd() throws Exception {
        if (lockComponent.tryLock(LockConst.GROUP_SHOP_END_LOCK, 30)) {
            try {
                Date now = new Date();
                /**
                 * 2. 冻结 团购时间结束的活动商品,并根据对应情况处理订单
                 */
                QueryWrapper<GroupShopDO> wrapper = new QueryWrapper<GroupShopDO>()
                        .eq("status", StatusType.ACTIVE.getCode())
                        .and(i -> i
                                .gt("gmt_start", now)
                                .or()
                                .le("gmt_end", now));

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
                                new QueryWrapper<OrderDO>()
                                        .eq("group_shop_id", groupShopDO.getId())
                                        .eq("status", OrderStatusType.GROUP_SHOP_WAIT.getCode()));

                        if (CollectionUtils.isEmpty(lockOrderList)) {
                            continue;
                        }

                        if (groupShopDO.getAutomaticRefund() == GroupShopAutomaticRefundType.YES.getCode() && groupShopDO.getBuyerNum().compareTo(groupShopDO.getMinNum()) < 0) {
                            // 2.2.2.1.退款
                            logger.info("[团购结束] 退款逻辑 GroupShopId:" + groupShopDO.getId());
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
                            logger.info("[团购结束] 发货逻辑 GroupShopId:" + groupShopDO.getId());
                            // 2.2.2.2 转换订单为待出货状态 (非自动退款场景)
                            List<Long> collect = lockOrderList.stream().map(s -> s.getId()).collect(Collectors.toList());
                            OrderDO orderDO = new OrderDO();
                            orderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
                            orderMapper.update(orderDO, (
                                    new QueryWrapper<OrderDO>()
                                            .in("id", collect)
                                            .eq("status", OrderStatusType.GROUP_SHOP_WAIT.getCode())));
                        }

                    }
                }
            } catch (Exception e) {
                logger.error("[团购结束 定时任务] 异常", e);
                throw e;
            } finally {
                lockComponent.release(LockConst.GROUP_SHOP_END_LOCK);
            }
        }
    }

}
