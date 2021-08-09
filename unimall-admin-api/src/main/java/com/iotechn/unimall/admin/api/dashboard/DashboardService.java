package com.iotechn.unimall.admin.api.dashboard;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;

/**
 * Created by rize on 2019/7/15.
 */
@HttpOpenApi(group = "admin.dashboard" , description = "首页数据服务")
public interface DashboardService {

    @HttpMethod(description = "聚合数据")
    public Object integral(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

}
