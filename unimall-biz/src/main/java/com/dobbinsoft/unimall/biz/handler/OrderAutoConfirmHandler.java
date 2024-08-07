package com.dobbinsoft.unimall.biz.handler;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.mq.DelayedMessageHandler;
import com.dobbinsoft.unimall.biz.service.order.OrderBizService;
import com.dobbinsoft.unimall.data.domain.OrderDO;
import com.dobbinsoft.unimall.data.enums.DMQHandlerType;
import com.dobbinsoft.unimall.data.enums.OrderStatusType;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

@Component
public class OrderAutoConfirmHandler implements DelayedMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderAutoConfirmHandler.class);

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public int handle(String orderNo) {
        Integer execute = transactionTemplate.execute(transactionStatus -> {
            try {
                OrderDO orderDO = orderBizService.checkOrderExistByNo(orderNo, null).get(0);
                if (orderDO.getStatus().intValue() != OrderStatusType.WAIT_CONFIRM.getCode()) {
                    throw new ServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CONFIRM);
                }
                OrderDO updateOrderDO = new OrderDO();
                updateOrderDO.setStatus(OrderStatusType.WAIT_APPRAISE.getCode());
                updateOrderDO.setGmtUpdate(LocalDateTime.now());
                orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.WAIT_CONFIRM.getCode(), updateOrderDO);
                logger.info("[订单自动收货] 订单号：" + orderNo);
            } catch (Exception e) {
                transactionStatus.setRollbackOnly();
                logger.error("[订单自动收货任务] 异常", e);
                return 0;
            }
            return 1;
        });
        return execute;
    }

    @Override
    public int getCode() {
        return DMQHandlerType.ORDER_AUTO_CONFIRM.getCode();
    }
}
