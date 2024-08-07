package com.dobbinsoft.unimall.admin.api.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.model.request.MatrixPayRefundRequest;
import com.dobbinsoft.fw.pay.model.result.MatrixPayRefundResult;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.component.LockComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.mq.DelayedMessageQueue;
import com.dobbinsoft.fw.support.utils.StringUtils;
import com.dobbinsoft.unimall.biz.service.order.OrderBizService;
import com.dobbinsoft.unimall.data.constant.LockConst;
import com.dobbinsoft.unimall.data.domain.OrderDO;
import com.dobbinsoft.unimall.data.domain.OrderSkuDO;
import com.dobbinsoft.unimall.data.dto.order.OrderDTO;
import com.dobbinsoft.unimall.data.dto.order.OrderStatisticsDTO;
import com.dobbinsoft.unimall.data.enums.DMQHandlerType;
import com.dobbinsoft.unimall.data.enums.OrderStatusType;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.OrderMapper;
import com.dobbinsoft.unimall.data.mapper.OrderSkuMapper;
import com.dobbinsoft.unimall.data.mapper.SkuMapper;
import com.dobbinsoft.unimall.data.properties.UnimallWxPayProperties;
import com.dobbinsoft.unimall.data.properties.UnimallOrderProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by rize on 2019/7/10.
 */
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderServiceImpl.class);

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private MatrixPayService matrixPayService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private DelayedMessageQueue delayedMessageQueue;

    @Autowired
    private UnimallOrderProperties unimallOrderProperties;

    @Autowired
    private UnimallWxPayProperties unimallWxPayProperties;

    @Override
    public Page<OrderDO> list(Integer page, Integer limit, Integer status, String orderNo, Long adminId) throws ServiceException {
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<OrderDO>();
        wrapper.orderByDesc("id");
        if (!StringUtils.isEmpty(orderNo)) {
            wrapper.eq("order_no", orderNo);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        return orderMapper.selectPage(Page.div(page, limit, OrderDO.class), wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(String orderNo, Integer type, Integer sum, Long adminId) throws ServiceException {
        if (lockComponent.tryLock(LockConst.ORDER_REFUND_LOCK + orderNo, 30)) {
            try {
                //1.校验订单状态是否处于退款中
                OrderDO orderDO = orderBizService.checkOrderExistByNo(orderNo, null).get(0);
                if (orderDO.getStatus().intValue() != OrderStatusType.REFUNDING.getCode()) {
                    throw new ServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
                }
                //2.退款处理
                if (type == 0) {
                    //2.1 店主拒绝退款
                    OrderDO updateOrderDO = new OrderDO();
                    // 判断之前是否已经发货，若没发货，则将状态流转到待发货
                    if (orderDO.getGmtShip() != null) {
                        updateOrderDO.setStatus(OrderStatusType.WAIT_CONFIRM.getCode());
                    } else {
                        updateOrderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
                    }
                    updateOrderDO.setGmtUpdate(LocalDateTime.now());
                    //2.2. 更改订单表状态
                    orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    return "ok";
                } else if (type == 1) {
                    if (orderDO.getPayChannel().equalsIgnoreCase(PayChannelType.WX.getCode())) {
                        String keyContentBase64 = unimallWxPayProperties.getKeyContent();
                        if (ObjectUtils.isEmpty(keyContentBase64)) {
                            throw new ServiceException(ExceptionDefinition.ORDER_REFUND_KEY_PATH_ERROR);
                        }
                    }
                    //2.2 店主同意退款
                    //2.2.1 先流转状态
                    OrderDO updateOrderDO = new OrderDO();
                    updateOrderDO.setStatus(OrderStatusType.REFUNDED.getCode());
                    updateOrderDO.setGmtUpdate(LocalDateTime.now());
                    //
                    //订单还库存
                    List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
                    orderSkuList.forEach(item -> {
                        skuMapper.returnSkuStock(item.getSkuId(), item.getNum());
                    });
                    orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    //2.2.2 向微信支付平台发送退款请求
                    Integer refundPrice = null;
                    if (sum != null) {
                        if (sum.intValue() > orderDO.getPayPrice()) {
                            throw new ServiceException(ExceptionDefinition.ORDER_REFUND_SUM_MOST_LOWER_THAN_PAY_PRICE);
                        }
                        refundPrice = sum;
                    } else {
                        refundPrice = orderDO.getPayPrice() - orderDO.getFreightPrice();
                    }
                    MatrixPayRefundRequest payRefundRequest = new MatrixPayRefundRequest();
                    payRefundRequest.setAppid(orderDO.getAppId());
                    payRefundRequest.setPayChannel(PayChannelType.getByCode(orderDO.getPayChannel()));
                    // 判断订单是子单支付还是父单支付
                    String abstractOrderNo;
                    if (orderDO.getSubPay().intValue() == 1) {
                        abstractOrderNo = orderDO.getOrderNo();
                    } else {
                        abstractOrderNo = orderDO.getParentOrderNo();
                    }
                    payRefundRequest.setOutTradeNo(abstractOrderNo);
                    payRefundRequest.setOutRefundNo("refund_" + abstractOrderNo);
                    payRefundRequest.setTotalFee(orderDO.getPayPrice());
                    payRefundRequest.setRefundFee(refundPrice);
                    payRefundRequest.setAppid(orderDO.getAppId());
                    MatrixPayRefundResult matrixPayRefundResult = matrixPayService.refund(payRefundRequest);
                    if (!matrixPayRefundResult.getReturnCode().equals("SUCCESS")) {
                        logger.warn("[在线退款] 失败 : " + matrixPayRefundResult.getReturnMsg());
                        throw new ServiceException(matrixPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    if (!matrixPayRefundResult.getResultCode().equals("SUCCESS")) {
                        logger.warn("[在线退款] 失败 : " + matrixPayRefundResult.getReturnMsg());
                        throw new ServiceException(matrixPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    return "ok";
                } else {
                    throw new ServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
                }
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[在线退款] 异常", e);
                throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(LockConst.ORDER_REFUND_LOCK + orderNo);
            }
        } else {
            throw new ServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String ship(String orderNo, String shipCode, String shipNo, Long adminId) throws ServiceException {
        orderBizService.checkOrderExistByNo(orderNo, null).get(0);
        OrderDO updateOrderDO = new OrderDO();
        LocalDateTime now = LocalDateTime.now();
        updateOrderDO.setGmtUpdate(now);
        updateOrderDO.setGmtShip(now);
        updateOrderDO.setStatus(OrderStatusType.WAIT_CONFIRM.getCode());
        if (!"NONE".equals(shipCode)) {
            updateOrderDO.setShipCode(shipCode);
            updateOrderDO.setShipNo(shipNo);
        }
        //流转订单状态
        orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.WAIT_STOCK.getCode(), updateOrderDO);
        // 发送自动确认收货延时消息
        delayedMessageQueue.publishTask(DMQHandlerType.ORDER_AUTO_CONFIRM.getCode(), orderNo, unimallOrderProperties.getAutoConfirmTime());
        return "ok";
    }

    @Override
    public OrderDTO detail(Long orderId, Long adminId) throws ServiceException {
        return orderBizService.getOrderDetail(orderId, null);
    }

    @Override
    @Deprecated
    public List<OrderDTO> queryToExcel(Long gmtStart, Long gmtEnd, Integer status, Long adminId) throws ServiceException {
//        QueryWrapper wrapper = new QueryWrapper();
//        if (gmtStart != null && gmtEnd != null) {
//            if (gmtStart > gmtStart) {
//                throw new ServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
//            }
//            wrapper.between("gmt_create", new Date(gmtStart), new Date(gmtEnd));
//        }
//        if (status != null) {
//            wrapper.eq("status", status);
//        }
//        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
//        if (orderDOS == null || orderDOS.size() == 0) {
//            return null;
//        }
//        List<OrderDTO> orderDTOList = new ArrayList<>();
//        for (OrderDO temp : orderDOS) {
//            OrderDTO orderDTO = new OrderDTO();
//            BeanUtils.copyProperties(temp, orderDTO);
//            orderDTOList.add(orderDTO);
//        }
//        for (OrderDTO orderDTO : orderDTOList) {
//            List<OrderSkuDO> orderSkuDOList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_no", orderDTO.getOrderNo()));
//            orderDTO.setSkuList(orderSkuDOList);
//        }
//        return orderDTOList;
        return null;
    }

    @Override
    public String editAdminMono(Long orderId, Integer level, String mono, Long adminId) throws ServiceException {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setAdminMonoLevel(level);
        orderDO.setAdminMono(mono);
        orderDO.setGmtUpdate(LocalDateTime.now());
        if (orderMapper.updateById(orderDO) > 0) {
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Deprecated
    public List<OrderStatisticsDTO> statistics(Long gmtStart, Long gmtEnd, Long adminId) throws ServiceException {
//        if (gmtStart == null) {
//            gmtStart = System.currentTimeMillis() - 1000l * 60 * 60 * 24;
//        }
//        if (gmtEnd == null) {
//            gmtEnd = System.currentTimeMillis();
//        }
//        if (gmtStart > gmtStart) {
//            throw new ServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
//        }
//        List<OrderDO> orderDOS = orderMapper.selectList(
//                new QueryWrapper<OrderDO>()
//                        .between("gmt_create", new Date(gmtStart), new Date(gmtEnd))
//                        .select("id"));
//        List<Long> ids = orderDOS.stream().map(item -> item.getId()).collect(Collectors.toList());
//        if (CollectionUtils.isEmpty(ids)) {
//            return null;
//        }
//
//        List<OrderStatisticsDTO> statistics = orderSkuMapper.statistics(ids);
//        return statistics;
        return null;
    }

}
