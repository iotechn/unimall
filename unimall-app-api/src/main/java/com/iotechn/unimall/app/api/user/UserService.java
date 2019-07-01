package com.iotechn.unimall.app.api.user;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.exception.ServiceException;

/**
 * Created by rize on 2019/6/30.
 */
@HttpOpenApi(group = "user", description = "用户服务")
public interface UserService {

    @HttpMethod(description = "test")
    public String test(
            @HttpParam(name = "say", type = HttpParamType.COMMON, description = "说") String say) throws ServiceException;



}
