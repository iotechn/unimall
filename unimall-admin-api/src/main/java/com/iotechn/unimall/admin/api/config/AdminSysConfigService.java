package com.iotechn.unimall.admin.api.config;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.DynamicConfigDO;

import java.util.List;

/**
 * Description:
 * User: rize
 * Date: 2020/8/5
 * Time: 16:45
 */
@HttpOpenApi(group = "admin.sysconfig", description = "管理员系统配置")
public interface AdminSysConfigService {

    @HttpMethod(description = "获取已配置", permission = "sys:config:get", permissionParentName = "系统管理", permissionName = "系统配置")
    public List<DynamicConfigDO> getData(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "保存", permission = "sys:config:save", permissionParentName = "系统管理", permissionName = "系统配置")
    public String save(
            @NotNull @HttpParam(name = "configs", type = HttpParamType.COMMON, description = "配置") String configsStr,
            @NotNull @HttpParam(name = "prefix", type = HttpParamType.COMMON, description = "前缀") String prefix,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

}
