package com.iotechn.unimall.app.api.order;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.exception.ServiceException;

/**
 * Created by rize on 2019/7/4.
 */
@HttpOpenApi(group = "order", description = "订单服务")
public interface OrderService {

    @HttpMethod(description = "提交订单")
    public String takeOrder() throws ServiceException;

}
