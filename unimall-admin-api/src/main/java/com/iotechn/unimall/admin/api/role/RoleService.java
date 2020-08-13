package com.iotechn.unimall.admin.api.role;


import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.RoleDO;
import com.iotechn.unimall.data.dto.RoleSetPermissionDTO;
import com.iotechn.unimall.data.model.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by rize on 2019/4/11.
 */
@HttpOpenApi(group = "admin.role", description = "角色服务")
public interface RoleService {

    @HttpMethod(description = "列表", permission = "sys:role:list", permissionParentName = "系统管理", permissionName = "角色管理")
    public Page<RoleDO> list(
            @HttpParam(name = "name", type = HttpParamType.COMMON, description = "搜索名称") String name,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "添加", permission = "sys:role:create", permissionParentName = "系统管理", permissionName = "角色管理")
    public RoleDO create(
            @NotNull @HttpParam(name = "role", type = HttpParamType.COMMON, description = "角色对象") RoleDO roleDO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "删除", permission = "sys:role:delete", permissionParentName = "系统管理", permissionName = "角色管理")
    public String delete(
            @NotNull @HttpParam(name = "roleId", type = HttpParamType.COMMON, description = "角色Id") Long roleId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "更新", permission = "sys:role:update", permissionParentName = "系统管理", permissionName = "角色管理")
    public RoleDO update(
            @NotNull @HttpParam(name = "role", type = HttpParamType.COMMON, description = "角色对象") RoleDO roleDO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "角色授权", permission = "sys:role:permissionlist",  permissionParentName = "系统管理", permissionName = "角色管理")
    public String permissionSet(
            @NotNull @HttpParam(name = "roleSetPermissionDTO", type = HttpParamType.COMMON, description = "设置DTO") RoleSetPermissionDTO roleSetPermissionDTO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "权限列表", permission = "sys:permission:list", permissionParentName = "系统管理", permissionName = "角色管理")
    public Map<String,Object> permissionList(
            @NotNull @HttpParam(name = "roleId", type = HttpParamType.COMMON, description = "角色ID") Long roleId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "角色枚举")
    public List<Map<String,Object>> options(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
