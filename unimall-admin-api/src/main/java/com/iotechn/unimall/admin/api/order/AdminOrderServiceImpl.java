package com.iotechn.unimall.admin.api.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.iotechn.unimall.biz.mq.DelayedMessageQueue;
import com.iotechn.unimall.data.constant.LockConst;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.biz.service.user.UserBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.dto.order.OrderStatisticsDTO;
import com.iotechn.unimall.data.enums.DMQHandlerType;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.UserLoginType;
import com.iotechn.unimall.data.mapper.OrderMapper;
import com.iotechn.unimall.data.mapper.OrderSkuMapper;
import com.iotechn.unimall.data.mapper.SkuMapper;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.properties.UnimallOrderProperties;
import com.iotechn.unimall.data.properties.UnimallWxAppProperties;
import com.iotechn.unimall.data.properties.UnimallWxPayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/10.
 */
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderServiceImpl.class);

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private DelayedMessageQueue delayedMessageQueue;

    @Autowired
    private UnimallWxAppProperties unimallWxProperties;

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
                if (orderDO.getStatus() != OrderStatusType.REFUNDING.getCode()) {
                    throw new AdminServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
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
                    updateOrderDO.setGmtUpdate(new Date());
                    orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    return "ok";
                } else if (type == 1) {
                    String keyPath = unimallWxPayProperties.getKeyPath();
                    if (!new File(keyPath).exists()) {
                        throw new AdminServiceException(ExceptionDefinition.ORDER_REFUND_KEY_PATH_ERROR);
                    }
                    //2.2 店主同意退款
                    //2.2.1 先流转状态
                    OrderDO updateOrderDO = new OrderDO();
                    updateOrderDO.setStatus(OrderStatusType.REFUNDED.getCode());
                    updateOrderDO.setGmtUpdate(new Date());
                    //订单还库存
                    List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
                    orderSkuList.forEach(item -> {
                        skuMapper.returnSkuStock(item.getSkuId(), item.getNum());
                    });
                    orderBizService.changeOrderSubStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    Long userId = orderDO.getUserId();
                    UserDO userDO = userBizService.getUserById(userId);
                    Integer loginType = userDO.getLoginType();
                    //2.2.2 向微信支付平台发送退款请求
                    Integer refundPrice = null;
                    if (sum != null) {
                        if (sum.intValue() > orderDO.getPayPrice()) {
                            throw new AdminServiceException(ExceptionDefinition.ORDER_REFUND_SUM_MOST_LOWER_THAN_PAY_PRICE);
                        }
                        refundPrice = sum;
                    } else {
                        refundPrice = orderDO.getPayPrice() - orderDO.getFreightPrice();
                    }
                    WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                    wxPayRefundRequest.setAppid(loginType == UserLoginType.MP_WEIXIN.getCode() ? unimallWxProperties.getMiniAppId() : unimallWxProperties.getMiniAppSecret());
                    // 判断订单是子单支付还是父单支付
                    String abstractOrderNo;
                    if (orderDO.getSubPay().intValue() == 1) {
                        abstractOrderNo = orderDO.getOrderNo();
                    } else {
                        abstractOrderNo = orderDO.getParentOrderNo();
                    }
                    wxPayRefundRequest.setOutTradeNo(abstractOrderNo);
                    wxPayRefundRequest.setOutRefundNo("refund_" + abstractOrderNo);
                    wxPayRefundRequest.setTotalFee(orderDO.getPayPrice());
                    wxPayRefundRequest.setRefundFee(refundPrice);
                    WxPayRefundResult wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
                    if (!wxPayRefundResult.getReturnCode().equals("SUCCESS")) {
                        logger.warn("[微信退款] 失败 : " + wxPayRefundResult.getReturnMsg());
                        throw new AdminServiceException(wxPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    if (!wxPayRefundResult.getResultCode().equals("SUCCESS")) {
                        logger.warn("[微信退款] 失败 : " + wxPayRefundResult.getReturnMsg());
                        throw new AdminServiceException(wxPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    return "ok";
                } else {
                    throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
                }
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[微信退款] 异常", e);
                throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(LockConst.ORDER_REFUND_LOCK + orderNo);
            }
        } else {
            throw new AdminServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String ship(String orderNo, String shipCode, String shipNo, Long adminId) throws ServiceException {
        orderBizService.checkOrderExistByNo(orderNo, null).get(0);
        OrderDO updateOrderDO = new OrderDO();
        Date now = new Date();
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
    public List<OrderDTO> queryToExcel(Long gmtStart, Long gmtEnd, Integer status, Long adminId) throws ServiceException {
        QueryWrapper wrapper = new QueryWrapper();
        if (gmtStart != null && gmtEnd != null) {
            if (gmtStart > gmtStart) {
                throw new AdminServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
            }
            wrapper.between("gmt_create", new Date(gmtStart), new Date(gmtEnd));
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (orderDOS == null || orderDOS.size() == 0) {
            return null;
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderDO temp : orderDOS) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(temp, orderDTO);
            orderDTOList.add(orderDTO);
        }
        for (OrderDTO orderDTO : orderDTOList) {
            List<OrderSkuDO> orderSkuDOList = orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_no", orderDTO.getOrderNo()));
            orderDTO.setSkuList(orderSkuDOList);
        }
        return orderDTOList;
    }

    @Override
    public String editAdminMono(Long orderId, Integer level, String mono, Long adminId) throws ServiceException {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setAdminMonoLevel(level);
        orderDO.setAdminMono(mono);
        orderDO.setGmtUpdate(new Date());
        if (orderMapper.updateById(orderDO) > 0) {
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public List<OrderStatisticsDTO> statistics(Long gmtStart, Long gmtEnd, Long adminId) throws ServiceException {
        if (gmtStart == null) {
            gmtStart = System.currentTimeMillis() - 1000l * 60 * 60 * 24;
        }
        if (gmtEnd == null) {
            gmtEnd = System.currentTimeMillis();
        }
        if (gmtStart > gmtStart) {
            throw new AdminServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(
                new QueryWrapper<OrderDO>()
                        .between("gmt_create", new Date(gmtStart), new Date(gmtEnd))
                        .select("id"));
        List<Long> ids = orderDOS.stream().map(item -> item.getId()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }

        List<OrderStatisticsDTO> statistics = orderSkuMapper.statistics(ids);
        return statistics;
    }

}
