package com.dobbinsoft.unimall.admin.api.admin;


import com.dobbinsoft.unimall.data.dto.admin.AdminDTO;
import com.dobbinsoft.fw.core.Const;
import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;

/**
 * Created by rize on 2019/4/8.
 */
@HttpOpenApi(group = "admin", description = "管理员服务")
public interface AdminService {

    @HttpMethod(description = "管理员登录 返回 AccessToken")
    public String login(
            @NotNull @HttpParam(name = "username", type = HttpParamType.COMMON, description = "用户名") String username,
            @NotNull @HttpParam(name = "password", type = HttpParamType.COMMON, description = "密码") String password,
            @NotNull @HttpParam(name = "verifyCode", type = HttpParamType.COMMON, description = "验证码") String verifyCode) throws ServiceException;

    @HttpMethod(description = "管理员登出")
    public String logout(
            @NotNull @HttpParam(name = Const.ADMIN_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "访问Token") String accessToken,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "管理员信息")
    public AdminDTO info(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "列表", permission = "sys:admin:list", permissionParentName = "系统管理", permissionName = "管理员")
    public Page<AdminDTO> list(
            @HttpParam(name = "username", type = HttpParamType.COMMON, description = "管理员名称搜索") String username,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页长度", valueDef = "20") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "创建", permission = "sys:admin:create", permissionParentName = "系统管理", permissionName = "管理员")
    public AdminDTO create(
            @NotNull @HttpParam(name = "adminDTO", type = HttpParamType.COMMON, description = "欲创建的admin对象JSON") AdminDTO adminDTO,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "IP") String ip,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "修改", permission = "sys:admin:edit", permissionParentName = "系统管理", permissionName = "管理员")
    public String edit(
            @NotNull @HttpParam(name = "adminDTO", type = HttpParamType.COMMON, description = "欲修改的admin对象JSON") AdminDTO adminDTO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "sys:admin:delete", permissionParentName = "系统管理", permissionName = "管理员")
    public String delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "目标删除Id") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "更改密码")
    public String newPassword(
            @HttpParam(name = Const.ADMIN_ACCESS_TOKEN, type = HttpParamType.HEADER, description = "访问Token") String accessToken,
            @NotNull @HttpParam(name = "oldPassword", type = HttpParamType.COMMON, description = "老密码") String oldPassword,
            @NotNull @HttpParam(name = "newPassword", type = HttpParamType.COMMON, description = "新密码") String newPassword,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "发送登陆短信")
    public Boolean sendLoginMsg(
            @NotNull @HttpParam(name = "username", type = HttpParamType.COMMON, description = "用户名") String username,
            @NotNull @HttpParam(name = "password", type = HttpParamType.COMMON, description = "密码") String password) throws ServiceException;

    @HttpMethod(description = "绑定通知公众号")
    public String bindUniNotify(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
