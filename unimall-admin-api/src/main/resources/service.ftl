package com.iotechn.unimall.admin.api.${packageName};

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.domain.${doName}DO;

/**
 * Generate Code By Unimall
 */
@HttpOpenApi(group = "admin.${serviceLowCaseName}", description = "${tableName}Service")
public interface ${serviceName}Service {

    @HttpMethod(description = "删除", permission = "${folder}:${serviceLowCaseName}:delete", permissionParentName = "其他", permissionName = "${serviceName}")
    public boolean delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "id") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "查询", permission = "${folder}:${serviceLowCaseName}:list", permissionParentName = "其他", permissionName = "${serviceName}")
    public Page<${doName}DO> list(
            <#list columnDefinitionList! as column>
            <#if column.query>
            @HttpParam(name = "${column.alias}", type = HttpParamType.COMMON, description = "${column.chinese}") ${column.clazzName} ${column.alias},
            </#if>
            </#list>
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "添加", permission = "${folder}:${serviceLowCaseName}:create", permissionParentName = "其他", permissionName = "${serviceName}")
    public ${doName}DO create(
            <#list columnDefinitionList! as column>
            <#if column.insertColumn>
            <#if column.notnull>@NotNull </#if>@HttpParam(name = "${column.alias}", type = HttpParamType.COMMON, description = "${column.chinese}") ${column.clazzName} ${column.alias},
            </#if>
            </#list>
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "编辑", permission = "${folder}:${serviceLowCaseName}:edit", permissionParentName = "其他", permissionName = "${serviceName}")
    public String edit(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "主键") Long id,
            <#list columnDefinitionList! as column>
            <#if column.insertColumn>
            <#if column.notnull>@NotNull </#if>@HttpParam(name = "${column.alias}", type = HttpParamType.COMMON, description = "${column.chinese}") ${column.clazzName} ${column.alias},
            </#if>
            </#list>
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
