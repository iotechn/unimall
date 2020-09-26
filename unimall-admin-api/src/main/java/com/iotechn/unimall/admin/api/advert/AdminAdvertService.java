package com.iotechn.unimall.admin.api.advert;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AdvertDO;
import com.iotechn.unimall.data.model.Page;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:23
 */

@HttpOpenApi(group = "admin.advert", description = "广告推销")
public interface AdminAdvertService {

    @HttpMethod(description = "创建", permission = "promotion:advert:create", permissionParentName = "推广管理", permissionName = "广告管理")
    public String create(
            @NotNull @HttpParam(name = "type", type = HttpParamType.COMMON, description = "广告类型") Integer type,
            @NotNull @HttpParam(name = "unionType", type = HttpParamType.COMMON, description = "关联类型") Integer unionType,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "广告标题") String title,
            @HttpParam(name = "unionValue", type = HttpParamType.COMMON, description = "关联值") String unionValue,
            @NotNull @HttpParam(name = "imgUrl", type = HttpParamType.COMMON, description = "广告图片地址") String imgUrl,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "广告状态") Integer status,
            @HttpParam(name = "color", type = HttpParamType.COMMON, description = "广告图片颜色", valueDef = "rgb(255,255,255)") String color,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "promotion:advert:delete", permissionParentName = "推广管理", permissionName = "广告管理")
    public String delete(
            @NotNull @HttpParam(name = "type", type = HttpParamType.COMMON, description = "广告类型") Integer type,
            @NotNull @HttpParam(name = "adId", type = HttpParamType.COMMON, description = "广告ID") Long adId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "修改", permission = "promotion:advert:edit", permissionParentName = "推广管理", permissionName = "广告管理")
    public String edit(
            @NotNull @HttpParam(name = "adId", type = HttpParamType.COMMON, description = "广告ID") Long adId,
            @NotNull @HttpParam(name = "type", type = HttpParamType.COMMON, description = "广告类型") Integer type,
            @NotNull @HttpParam(name = "unionType", type = HttpParamType.COMMON, description = "关联类型") Integer unionType,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "广告标题") String title,
            @HttpParam(name = "unionValue", type = HttpParamType.COMMON, description = "关联值") String unionValue,
            @HttpParam(name = "imgUrl", type = HttpParamType.COMMON, description = "广告图片地址") String imgUrl,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "广告状态") Integer status,
            @HttpParam(name = "color", type = HttpParamType.COMMON, description = "广告图片颜色") String color,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询", permission = "promotion:advert:list", permissionParentName = "推广管理", permissionName = "广告管理")
    public Page<AdvertDO> list(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "type", type = HttpParamType.COMMON, description = "广告类型") Integer type,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer limit,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "广告状态") Integer status) throws ServiceException;

}
