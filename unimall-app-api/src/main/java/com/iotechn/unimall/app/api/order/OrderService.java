package com.iotechn.unimall.app.api.order;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.data.model.Page;

import java.util.List;

/**
 * Created by rize on 2019/7/4.
 */
@HttpOpenApi(group = "order", description = "订单服务")
public interface OrderService {

    @HttpMethod(description = "提交订单")
    public String takeOrder(
            @NotNull @HttpParam(name = "orderRequest", type = HttpParamType.COMMON, description = "订单请求实例") OrderRequestDTO orderRequest,
            @NotNull @HttpParam(name = "channel", type = HttpParamType.COMMON, description = "订单提交渠道") String channel,
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "userId") Long userId) throws ServiceException;


    @HttpMethod(description = "获取订单分页")
    public Page<OrderDTO> getOrderPage(
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer pageSize,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "订单状态") Integer status,
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取订单详情")
    public OrderDTO getOrderDetail(
            @NotNull @HttpParam(name = "orderId", type = HttpParamType.COMMON, description = "订单号") Long orderId,
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "微信小程序预先支付")
    public Object wxPrepay(
            @NotNull @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单串号") String orderNo,
            @HttpParam(name = "ip", type = HttpParamType.IP, description = "ip地址") String ip,
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

}