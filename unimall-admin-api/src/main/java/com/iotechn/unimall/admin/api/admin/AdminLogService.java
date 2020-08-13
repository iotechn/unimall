package com.iotechn.unimall.admin.api.admin;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AdminLogDO;
import com.iotechn.unimall.data.model.Page;

/**
 * Description:
 * User: rize
 * Date: 2020/8/11
 * Time: 16:30
 */
@HttpOpenApi(group = "admin.log", description = "操作日志服务")
public interface AdminLogService {

    @HttpMethod(description = "管理员日志", permission = "sys:log:list", permissionParentName = "系统管理", permissionName = "操作日志")
    public Page<AdminLogDO> list(
            @HttpParam(name = "targetAdminId", type = HttpParamType.COMMON, description = "管理员Id") Long targetAdminId,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页长度", valueDef = "20") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


}
