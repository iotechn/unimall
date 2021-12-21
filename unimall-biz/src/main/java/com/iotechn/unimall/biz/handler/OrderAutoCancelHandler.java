package com.iotechn.unimall.biz.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.AppServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.mq.DelayedMessageHandler;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.enums.DMQHandlerType;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.OrderSkuMapper;
import com.iotechn.unimall.data.mapper.SkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Date;
import java.util.List;

@Component
public class OrderAutoCancelHandler implements DelayedMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderAutoCancelHandler.class);

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CacheComponent cacheComponent;

    /**
     * @param orderNo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int handle(String orderNo) {
        try {
            OrderDO orderDO = orderBizService.checkOrderExistByNo(orderNo, null).get(0);
            if (orderDO.getStatus() != OrderStatusType.UNPAY.getCode()) {
                throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CANCEL);
            }
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setStatus(OrderStatusType.CANCELED_SYS.getCode());
            updateOrderDO.setGmtUpdate(new Date());
            List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
            orderSkuList.forEach(item -> {
                skuMapper.returnSkuStock(item.getSkuId(), item.getNum());
            });
            orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.UNPAY.getCode(), updateOrderDO);
            logger.info("[订单自动取消] 订单号：" + orderNo);
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    orderSkuList.forEach(item -> {
                        cacheComponent.incrementHashKey(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + item.getSkuId(), item.getNum());
                    });
                }
            });
        } catch (Exception e) {
            logger.error("[订单自动取消任务] 异常", e);
            return 0;
        }
        return 1;
    }

    @Override
    public int getCode() {
        return DMQHandlerType.ORDER_AUTO_CANCEL.getCode();
    }
}
