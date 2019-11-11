package com.iotechn.unimall.admin.api.advertisement;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AdvertisementDO;
import com.iotechn.unimall.data.dto.RecommendDTO;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.model.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:23
 */

@HttpOpenApi(group = "admin.advertisement", description = "广告推销")
public interface AdminAdvertisementService {

    @HttpMethod(description = "创建", permission = "promote:advertisement:create", permissionParentName = "推广管理", permissionName = "广告管理")
    public Boolean addAdvertisement(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "adType", type = HttpParamType.COMMON, description = "广告类型") Integer adType,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "广告标题") String title,
            @NotNull @HttpParam(name = "url", type = HttpParamType.COMMON, description = "广告地址") String url,
            @NotNull @HttpParam(name = "imgUrl", type = HttpParamType.COMMON, description = "广告图片地址") String imgUrl,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "广告状态") Integer status,
            @NotNull @HttpParam(name = "color", type = HttpParamType.COMMON, description = "广告图片颜色") String color) throws ServiceException;

    @HttpMethod(description = "删除", permission = "promote:advertisement:delete", permissionParentName = "推广管理", permissionName = "广告管理")
    public Boolean deleteAdvertisement(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "adType", type = HttpParamType.COMMON, description = "广告类型") Integer adType,
            @NotNull @HttpParam(name = "adId", type = HttpParamType.COMMON, description = "广告ID") Long adId) throws ServiceException;

    @HttpMethod(description = "修改", permission = "promote:advertisement:update", permissionParentName = "推广管理", permissionName = "广告管理")
    public Boolean updateAdvertisement(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "adId", type = HttpParamType.COMMON, description = "广告ID") Long adId,
            @NotNull @HttpParam(name = "adType", type = HttpParamType.COMMON, description = "广告类型") Integer adType,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "广告标题") String title,
            @HttpParam(name = "url", type = HttpParamType.COMMON, description = "广告地址") String url,
            @HttpParam(name = "imgUrl", type = HttpParamType.COMMON, description = "广告图片地址") String imgUrl,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "广告状态") Integer status,
            @HttpParam(name = "color", type = HttpParamType.COMMON, description = "广告图片颜色") String color) throws ServiceException;


    @HttpMethod(description = "查询", permission = "promote:advertisement:query", permissionParentName = "推广管理", permissionName = "广告管理")
    public Page<AdvertisementDO> queryAdvertisement(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "adType", type = HttpParamType.COMMON, description = "广告类型") Integer adType,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer limit,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "广告状态") Integer status) throws ServiceException;

    @HttpMethod(description = "查询", permission = "promote:advertisement:query", permissionParentName = "推广管理", permissionName = "广告管理")
    public Page<AdvertisementDO> queryAllAdvertisement(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer pageSize
    ) throws ServiceException;


}
