package com.iotechn.unimall.admin.api.search;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;

/**
 * Description:
 * User: rize
 * Date: 2020/8/15
 * Time: 11:22
 */
@HttpOpenApi(group = "admin.search", description = "管理员搜索服务")
public interface AdminSearchService {

    @HttpMethod(description = "初始化")
    public String init(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "重建数据")
    public String rebuild(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "重新加载搜索引擎属性")
    public String reloadProperties(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;
}
