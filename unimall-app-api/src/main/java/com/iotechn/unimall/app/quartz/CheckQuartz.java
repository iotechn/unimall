package com.iotechn.unimall.app.quartz;

import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.mapper.OrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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

    /**
     * 订单状态定时轮训
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkOrderStatus() {
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
        }
    }

}
