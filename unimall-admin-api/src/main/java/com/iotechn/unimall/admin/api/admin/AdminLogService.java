package com.iotechn.unimall.admin.api.admin;

import com.iotechn.unimall.data.domain.AdminLogDO;
import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;

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
