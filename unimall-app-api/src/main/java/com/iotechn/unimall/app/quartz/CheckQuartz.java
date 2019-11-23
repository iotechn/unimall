package com.iotechn.unimall.app.quartz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.GroupShopDO;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.GroupShopMapper;
import com.iotechn.unimall.data.mapper.OrderMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderBizService orderBizService;
    @Autowired
    private GroupShopMapper groupShopMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private LockComponent lockComponent;

    /**
     * 订单状态定时轮训
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkOrderStatus() {
        if (lockComponent.tryLock(ORDER_STATUS_LOCK, 15)) {
            try {
                Date now = new Date();
                List<String> nos = orderMapper.selectExpireOrderNos(OrderStatusType.UNPAY.getCode(), new Date(now.getTime() - 1000l * 60 * 15));
                if (!CollectionUtils.isEmpty(nos)) {
                    nos.forEach(no -> {
                        try {
                            OrderDO updateOrderDO = new OrderDO();
                            updateOrderDO.setStatus(OrderStatusType.CANCELED_SYS.getCode());
                            updateOrderDO.setGmtUpdate(now);
                            orderBizService.changeOrderStatus(no, OrderStatusType.UNPAY.getCode(), updateOrderDO);
                        } catch (Exception e) {
                            logger.error("[未付款检测] 异常", e);
                        }
                    });
                }
                //15分钟执行一次
                long minutes = (now.getTime() / (1000 * 60));
                if (minutes % 15 == 0) {
                    List<String> waitConfirmNos = orderMapper.selectExpireOrderNos(OrderStatusType.WAIT_CONFIRM.getCode(), new Date(now.getTime() - 1000l * 60 * 60 * 24 * 7));
                    waitConfirmNos.forEach(item -> {
                        try {
                            OrderDO updateOrderDO = new OrderDO();
                            updateOrderDO.setStatus(OrderStatusType.WAIT_APPRAISE.getCode());
                            updateOrderDO.setGmtUpdate(now);
                            orderBizService.changeOrderStatus(item, OrderStatusType.WAIT_CONFIRM.getCode(), updateOrderDO);
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
    @Scheduled(fixedRate = 60000)
    @Transactional(rollbackFor = Exception.class)
    public void scanAndModifyStatusAndRefund() throws ServiceException {
        Date now = new Date();

        /**
         * 1. 激活 团购时间开始的活动商品
         */
        GroupShopDO activeGroupShopDO = new GroupShopDO();
        activeGroupShopDO.setStatus(StatusType.ACTIVE.getCode());
        activeGroupShopDO.setGmtUpdate(now);
        groupShopMapper.update(activeGroupShopDO, (new EntityWrapper<GroupShopDO>().le("gmt_start", now).and().gt("gmt_end", now).and().eq("status", StatusType.LOCK.getCode())));

        /**
         * 2. 冻结 团购时间结束的活动商品,并根据对应情况处理订单
         */
        List<GroupShopDO> lockGroupShopDOList = groupShopMapper.selectList((new EntityWrapper<GroupShopDO>().eq("status", StatusType.ACTIVE.getCode()).and().gt("gmt_start", now).and().le("gmt_end", now)));

        // 2.1 对过期团购商品冻结.
        GroupShopDO lockGroupShopDO = new GroupShopDO();
        lockGroupShopDO.setStatus(StatusType.LOCK.getCode());
        lockGroupShopDO.setGmtUpdate(now);
        groupShopMapper.update(lockGroupShopDO, (new EntityWrapper<GroupShopDO>()
                .eq("status", StatusType.ACTIVE.getCode())
                .and()
                .gt("gmt_start", now)
                .and()
                .le("gmt_end", now)));

        // 2.2 将团购订单的状态转为对应的退款或待出库状态,对未达人数且自动退款的商品订单进行退款,对达到人数或不自动退款的商品订单转换状态
        for (GroupShopDO groupShopDO : lockGroupShopDOList) {

            // 2.2.1查询团购订单中数据
            List<OrderDO> lockOrderList = orderMapper.selectList((new EntityWrapper<OrderDO>().eq("group_shop_id", groupShopDO.getId())));

            if (CollectionUtils.isEmpty(lockOrderList)) {
                continue;
            }

            // 2.2.2退款
            if (groupShopDO.getNoFullPeopleAutomaticRefund() != StatusType.LOCK.getCode() || groupShopDO.getAlreadyBuyNumber().compareTo(groupShopDO.getMinimumNumber()) < 0) {
                for (OrderDO orderDO : lockOrderList) {
                    orderBizService.groupShopStatusRefund(orderDO.getOrderNo());
                }
                continue;
            }

            // 2.2.3转换订单为待出货状态
            List<Long> collect = lockOrderList.stream().map(s -> s.getId()).collect(Collectors.toList());

            OrderDO orderDO = new OrderDO();
            orderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
            orderMapper.update(orderDO, (new EntityWrapper<OrderDO>().in("id", collect)));
        }


    }
}
