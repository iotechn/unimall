package com.iotechn.unimall.admin.api.dashboard;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;

/**
 * Created by rize on 2019/7/15.
 */
@HttpOpenApi(group = "admin.dashboard" , description = "首页数据服务")
public interface DashboardService {

    @HttpMethod(description = "聚合数据")
    public Object integral(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

}
