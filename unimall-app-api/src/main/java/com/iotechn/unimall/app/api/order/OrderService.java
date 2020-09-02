package com.iotechn.unimall.app.api.order;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.data.model.Page;

/**
 * Created by rize on 2019/7/4.
 */
@HttpOpenApi(group = "order", description = "订单服务")
public interface OrderService {

    @HttpMethod(description = "提交订单")
    public String takeOrder(
            @NotNull @HttpParam(name = "orderRequest", type = HttpParamType.COMMON, description = "订单请求实例") OrderRequestDTO orderRequest,
            @NotNull @HttpParam(name = "channel", type = HttpParamType.COMMON, description = "订单提交渠道") String channel,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "userId") Long userId) throws ServiceException;

    @HttpMethod(description = "获取订单分页")
    public Page<OrderDTO> getOrderPage(
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer pageSize,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "订单状态") Integer status,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取订单详情")
    public OrderDTO getOrderDetail(
            @NotNull @HttpParam(name = "orderId", type = HttpParamType.COMMON, description = "订单号") Long orderId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "微信小程序预先支付")
    public Object wxPrepay(
            @HttpParam(name = "parentOrderNo", type = HttpParamType.COMMON, description = "父单串号") String parentOrderNo,
            @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单串号") String orderNo,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "ip地址") String ip,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "线下支付")
    public Object offlinePrepay(
            @HttpParam(name = "parentOrderNo", type = HttpParamType.COMMON, description = "父单串号") String parentOrderNo,
            @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单串号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "用户申请退款")
    public String refund(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单串号") String orderNo,
            @HttpParam(name = "reason", type = HttpParamType.COMMON, description = "退款理由") String reason,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "取消订单")
    public String cancel(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "确认订单")
    public String confirm(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "查询物流")
    public ShipTraceDTO queryShip(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号") String orderNo,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "预览邮费")
    public Integer previewFreight(
            @NotNull @HttpParam(name = "orderRequest", type = HttpParamType.COMMON, description = "用户欲下单") OrderRequestDTO orderRequest,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

}
