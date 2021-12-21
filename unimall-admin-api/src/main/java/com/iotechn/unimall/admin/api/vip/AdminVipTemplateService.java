package com.iotechn.unimall.admin.api.vip;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.domain.VipTemplateDO;

@HttpOpenApi(group = "admin.vip.template", description = "VIP模板")
public interface AdminVipTemplateService {

    @HttpMethod(description = "创建", permission = "vip:template:create", permissionParentName = "VIP管理", permissionName = "模板管理")
    public VipTemplateDO create(
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "标题") String title,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "简介") String description,
            @NotNull @Range(min = 1) @HttpParam(name = "dayNum", type = HttpParamType.COMMON, description = "天数") Integer dayNum,
            @NotNull @Range(min = 1) @HttpParam(name = "originalPrice", type = HttpParamType.COMMON, description = "原价") Integer originalPrice,
            @NotNull @Range(min = 1) @HttpParam(name = "price", type = HttpParamType.COMMON, description = "现价") Integer price,
            @NotNull @Range(min = 0, max = 1) @HttpParam(name = "display", type = HttpParamType.COMMON, description = "是否在前端显示") Integer display,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "vip:template:delete", permissionParentName = "VIP管理", permissionName = "模板管理")
    public String delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "ID") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "修改", permission = "vip:template:edit", permissionParentName = "VIP管理", permissionName = "模板管理")
    public VipTemplateDO edit(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "ID") Long id,
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "标题") String title,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "简介") String description,
            @NotNull @Range(min = 1) @HttpParam(name = "dayNum", type = HttpParamType.COMMON, description = "天数") Integer dayNum,
            @NotNull @Range(min = 1) @HttpParam(name = "originalPrice", type = HttpParamType.COMMON, description = "原价") Integer originalPrice,
            @NotNull @Range(min = 1) @HttpParam(name = "price", type = HttpParamType.COMMON, description = "现价") Integer price,
            @NotNull @Range(min = 0, max = 1) @HttpParam(name = "display", type = HttpParamType.COMMON, description = "是否在前端显示") Integer display,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询", permission = "vip:template:list", permissionParentName = "VIP管理", permissionName = "模板管理")
    public Page<VipTemplateDO> list(
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "标题") String title,
            @Range(min = 0, max = 1) @HttpParam(name = "display", type = HttpParamType.COMMON, description = "是否在前端显示") Integer display,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
