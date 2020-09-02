package com.iotechn.unimall.admin.api.freight;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午3:37
 */
@HttpOpenApi(group = "admin.freight", description = "运费模板")
public interface AdminFreightTemplateService {

    @HttpMethod(description = "创建", permission = "operation:freight:create", permissionParentName = "运营管理", permissionName = "运费模板管理")
    public String create(
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "模板名称") String title,
            @HttpParam(name = "spuLocation", type = HttpParamType.COMMON, description = "宝贝地址") String spuLocation,
            @NotNull @HttpParam(name = "deliveryDeadline", type = HttpParamType.COMMON, description = "发货期限") Integer deliveryDeadline,
            @NotNull @HttpParam(name = "defaultFreePrice", type = HttpParamType.COMMON, description = "默认满价包邮系列,负数不包邮，0为包邮，正数为满多少包邮") Integer defaultFreePrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultFirstWeight", type = HttpParamType.COMMON, description = "首重，单位克") Integer defaultFirstWeight,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultFirstPrice", type = HttpParamType.COMMON, description = "首费，单位分") Integer defaultFirstPrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultContinueWeight", type = HttpParamType.COMMON, description = "续重") Integer defaultContinueWeight,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultContinuePrice", type = HttpParamType.COMMON, description = "续费") Integer defaultContinuePrice,
            @HttpParam(name = "carriageDOList", type = HttpParamType.COMMON, description = "特殊运费区") List carriageDOList,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "operation:freight:delete", permissionParentName = "运营管理", permissionName = "运费模板管理")
    public String delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "模板ID") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "更新", permission = "operation:freight:edit", permissionParentName = "运营管理", permissionName = "运费模板管理")
    public String edit(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "模板名称") Long id,
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "模板名称") String title,
            @HttpParam(name = "spuLocation", type = HttpParamType.COMMON, description = "宝贝地址") String spuLocation,
            @NotNull @HttpParam(name = "deliveryDeadline", type = HttpParamType.COMMON, description = "发货期限") Integer deliveryDeadline,
            @NotNull @HttpParam(name = "defaultFreePrice", type = HttpParamType.COMMON, description = "默认满价包邮系列") Integer defaultFreePrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultFirstWeight", type = HttpParamType.COMMON, description = "首重，单位克") Integer defaultFirstWeight,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultFirstPrice", type = HttpParamType.COMMON, description = "首费，单位分") Integer defaultFirstPrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultContinueWeight", type = HttpParamType.COMMON, description = "续重") Integer defaultContinueWeight,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultContinuePrice", type = HttpParamType.COMMON, description = "续费") Integer defaultContinuePrice,
            @HttpParam(name = "carriageDOList", type = HttpParamType.COMMON, description = "特殊运费区") List carriageDOList,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询", permission = "operation:freight:list", permissionParentName = "运营管理", permissionName = "运费模板管理")
    public List<FreightTemplateDTO> list(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
