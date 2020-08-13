package com.iotechn.unimall.admin.api.user;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.model.Page;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-11
 * Time: 下午7:30
 */

@HttpOpenApi(group = "admin.user", description = "用户管理")
public interface AdminUserService {

    @HttpMethod(description = "创建", permission = "user:user:create", permissionParentName = "用户管理", permissionName = "用户管理")
    public Boolean create(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "user", type = HttpParamType.COMMON, description = "用户信息") UserDO user) throws ServiceException;

    @HttpMethod(description = "删除", permission = "user:user:delete", permissionParentName = "用户管理", permissionName = "用户管理")
    public Boolean delete(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "用户Id") Long id,
            @NotNull @HttpParam(name = "nickname", type = HttpParamType.COMMON, description = "用户Id") String nickname) throws ServiceException;

    @HttpMethod(description = "修改", permission = "user:user:edit", permissionParentName = "用户管理", permissionName = "用户管理")
    public Boolean edit(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "user", type = HttpParamType.COMMON, description = "用户信息") UserDO user) throws ServiceException;

    @HttpMethod(description = "激活冻结", permission = "user:user:edit", permissionParentName = "用户管理", permissionName = "用户管理")
    public Boolean editStatus(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.COMMON, description = "用户信息") Long userId,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "用户信息") Integer status) throws ServiceException;

    @HttpMethod(description = "查询", permission = "user:user:list", permissionParentName = "用户管理", permissionName = "用户管理")
    public Page<UserDO> list(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "id", type = HttpParamType.COMMON, description = "用户ID") Long id,
            @HttpParam(name = "nickname", type = HttpParamType.COMMON, description = "用户昵称") String nickname,
            @HttpParam(name = "level", type = HttpParamType.COMMON, description = "用户等级") Integer level,
            @HttpParam(name = "gender", type = HttpParamType.COMMON, description = "用户性别") Integer gender,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "用户状态") Integer status,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "当前页码") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度") Integer limit) throws ServiceException;

}
