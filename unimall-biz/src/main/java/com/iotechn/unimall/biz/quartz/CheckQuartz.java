package com.iotechn.unimall.biz.quartz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.Const;
import com.dobbinsoft.fw.core.exception.AdminServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.LockComponent;
import com.dobbinsoft.fw.support.component.MachineComponent;
import com.dobbinsoft.fw.support.mq.DelayedMessageQueue;
import com.iotechn.unimall.biz.client.erp.ErpClient;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.data.constant.LockConst;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.enums.*;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.properties.UnimallOrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
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
import java.util.Set;
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
    private UserMapper userMapper;
    @Autowired
    private VipOrderMapper vipOrderMapper;
    @Autowired
    private ErpClient erpClient;
    @Autowired
    private UnimallOrderProperties unimallOrderProperties;
    @Autowired
    private LockComponent lockComponent;
    @Autowired
    private DelayedMessageQueue delayedMessageQueue;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private StringRedisTemplate userRedisTemplate;
    @Autowired
    private MachineComponent machineComponent;

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
        }


    }

    @Scheduled(cron = "10 * * * * ?")
    public void groupShopEnd() throws Exception {
        if (lockComponent.tryLock(LockConst.GROUP_SHOP_END_LOCK, 30)) {
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
                for (GroupShopDO groupShopDO : lockGroupShopDOList) {
                    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                        @Override
                        protected void doInTransactionWithoutResult(TransactionStatus parentTransactionStatus) {
                            try {
                                // 2.1 对过期团购活动冻结.
                                GroupShopDO lockGroupShopDO = new GroupShopDO();
                                lockGroupShopDO.setId(groupShopDO.getId());
                                lockGroupShopDO.setStatus(StatusType.LOCK.getCode());
                                lockGroupShopDO.setGmtUpdate(now);
                                groupShopMapper.updateById(lockGroupShopDO);

                                // 2.2.1查询团购订单中数据
                                List<OrderDO> lockOrderList = orderMapper.selectList(
                                        new QueryWrapper<OrderDO>()
                                                .eq("group_shop_id", groupShopDO.getId())
                                                .eq("status", OrderStatusType.GROUP_SHOP_WAIT.getCode()));

                                if (CollectionUtils.isEmpty(lockOrderList)) {
                                    // 未有团购订单，直接空过
                                    return;
                                }

                                if (groupShopDO.getAutomaticRefund() == GroupShopAutomaticRefundType.YES.getCode() && groupShopDO.getBuyerNum().compareTo(groupShopDO.getMinNum()) < 0) {
                                    // 2.2.2.1.退款
                                    logger.info("[团购结束] 退款逻辑 GroupShopId:" + groupShopDO.getId());
                                    for (OrderDO orderDO : lockOrderList) {
                                        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                            @Override
                                            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                                                try {
                                                    // 此处存在RPC，不一定能保证能保证一致性，所以单独加上事务，不要影响其他的订单
                                                    orderBizService.groupShopStatusRefund(orderDO.getOrderNo());
                                                    logger.info("[团购订单退款] 完成 orderNo:" + orderDO.getOrderNo());
                                                } catch (Exception e) {
                                                    logger.error("[团购订单退款] 异常 orderNo:" + orderDO.getOrderNo(), e);
                                                    transactionStatus.setRollbackOnly();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    logger.info("[团购结束] 发货逻辑 GroupShopId:" + groupShopDO.getId());
                                    // 2.2.2.2 转换订单为待出货状态 (非自动退款场景)
                                    for (OrderDO orderDO : lockOrderList) {
                                        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                            @Override
                                            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                                                try {
                                                    // 此处存在RPC，不一定能保证能保证一致性，所以单独加上事务，不要影响其他的订单
                                                    OrderDO updateOrderDO = new OrderDO();
                                                    updateOrderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
                                                    orderBizService.changeOrderSubStatus(orderDO.getOrderNo(), OrderStatusType.GROUP_SHOP_WAIT.getCode(), updateOrderDO);
                                                    erpClient.takeSalesHeader(orderDO.getOrderNo());
                                                    logger.info("[团购订单发货] 完成 orderNo:" + orderDO.getOrderNo());
                                                } catch (Exception e) {
                                                    logger.error("[团购订单发货] 异常 orderNo:" + orderDO.getOrderNo(), e);
                                                    transactionStatus.setRollbackOnly();
                                                }
                                            }
                                        });
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("[团购结束] 定时任务 异常 id=" + groupShopDO.getId(), e);
                                parentTransactionStatus.setRollbackOnly();
                            }
                        }
                    });
                }
            }
        }
    }


    /**
     * 会员过期设置
     *
     * @throws Exception
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void downLevel() throws Exception {
        if (lockComponent.tryLock(LockConst.VIP_EXPIRE_LOCK, 30)) {
            Date now = new Date();
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    List<Long> userIds = null;
                    try {
                        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
                        wrapper.eq("level", UserLevelType.VIP.getCode());
                        wrapper.le("gmt_vip_expire", now);
                        wrapper.select("id");
                        userIds = userMapper.selectList(wrapper.select("id")).stream().map(UserDO::getId).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(userIds)) {
                            UserDO update = new UserDO();
                            update.setLevel(UserLevelType.COMMON.getCode());
                            userMapper.update(update, wrapper);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        return;
                    }
                    // 事务提交成功！更新用户session
                    if (!CollectionUtils.isEmpty(userIds)) {
                        for (Long userId : userIds) {
                            Set<String> keys = userRedisTemplate.keys(Const.USER_REDIS_PREFIX + userId + "-*");
                            if (!CollectionUtils.isEmpty(keys)) {
                                for (String key : keys) {
                                    String userJson = userRedisTemplate.opsForValue().get(key);
                                    UserDTO userDTO = JSONObject.parseObject(userJson, UserDTO.class);
                                    userDTO.setLevel(UserLevelType.COMMON.getCode());
                                    userRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(userDTO));
                                }

                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 会员支付超时设置
     *
     * @throws Exception
     */
    @Scheduled(cron = "20 * * * * ?")
    public void checkVipOrderStatus() throws Exception {
        if (lockComponent.tryLock(LockConst.VIP_PAY_TIMEOUT_LOCK, 30)) {
            try {
                QueryWrapper<VipOrderDO> wrapper = new QueryWrapper<>();
                wrapper.eq("status", VipOrderStatusType.WAIT_REFUND.getCode());
                wrapper.le("gmt_pay", new Date(new Date().getTime() - 7 * 24 * 60 * 60 * 1000l));
                List<VipOrderDO> vipOrderDOS = vipOrderMapper.selectList(wrapper);
                if (!CollectionUtils.isEmpty(vipOrderDOS)) {
                    for (VipOrderDO vipOrderDO : vipOrderDOS) {
                        delayedMessageQueue.publishTask(DMQHandlerType.VIP_ORDER_BUY_OVER.getCode(), String.valueOf(vipOrderDO.getId()), 1);
                    }
                }
            } finally {
                lockComponent.release(LockConst.VIP_PAY_TIMEOUT_LOCK);
            }
        }
    }

    /**
     * 续约机器号
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void renewMachineNo() {
        if (this.machineComponent.isInit()) {
            this.machineComponent.renew();
        }
    }

}
