package com.iotechn.unimall.app.api.order;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;

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



}
