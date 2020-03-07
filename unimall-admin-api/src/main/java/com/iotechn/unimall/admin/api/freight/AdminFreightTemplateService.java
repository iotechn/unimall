package com.iotechn.unimall.admin.api.freight;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
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
    public boolean addFreightTemplate(
            @NotNull @HttpParam(name = "templateName", type = HttpParamType.COMMON, description = "模板名称") String templateName,
            @HttpParam(name = "spuLocation", type = HttpParamType.COMMON, description = "宝贝地址") String spuLocation,
            @NotNull @HttpParam(name = "deliveryDeadline", type = HttpParamType.COMMON, description = "发货期限") Integer deliveryDeadline,
            @NotNull @HttpParam(name = "defaultFreePrice", type = HttpParamType.COMMON, description = "默认满价包邮系列") Integer defaultFreePrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultFirstNum", type = HttpParamType.COMMON, description = "默认首费") Integer defaultFirstNum,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultFirstPrice", type = HttpParamType.COMMON, description = "默认首件数量") Integer defaultFirstPrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultContinueNum", type = HttpParamType.COMMON, description = "默认续费") Integer defaultContinueNum,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultContinuePrice", type = HttpParamType.COMMON, description = "默认续件数量") Integer defaultContinuePrice,
            @HttpParam(name = "freightTemplateCarriageDOList", type = HttpParamType.COMMON, description = "特殊运费区") List freightTemplateCarriageDOList,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId
    ) throws ServiceException;

    @HttpMethod(description = "删除", permission = "operation:freight:delete", permissionParentName = "运营管理", permissionName = "运费模板管理")
    public boolean deleteFreightTemplate(
            @NotNull @HttpParam(name = "templateId", type = HttpParamType.COMMON, description = "模板ID") Long templateId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "更新", permission = "operation:freight:update", permissionParentName = "运营管理", permissionName = "运费模板管理")
    public boolean updateFreightTemplate(
            @NotNull @HttpParam(name = "templateId", type = HttpParamType.COMMON, description = "模板名称") Long templateId,
            @NotNull @HttpParam(name = "templateName", type = HttpParamType.COMMON, description = "模板名称") String templateName,
            @HttpParam(name = "spuLocation", type = HttpParamType.COMMON, description = "宝贝地址") String spuLocation,
            @NotNull @HttpParam(name = "deliveryDeadline", type = HttpParamType.COMMON, description = "发货期限") Integer deliveryDeadline,
            @NotNull @HttpParam(name = "defaultFreePrice", type = HttpParamType.COMMON, description = "默认满价包邮系列") Integer defaultFreePrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultFirstNum", type = HttpParamType.COMMON, description = "默认首费") Integer defaultFirstNum,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultFirstPrice", type = HttpParamType.COMMON, description = "默认首件数量") Integer defaultFirstPrice,
            @NotNull @Range(min = 1) @HttpParam(name = "defaultContinueNum", type = HttpParamType.COMMON, description = "默认续费") Integer defaultContinueNum,
            @NotNull @Range(min = 0) @HttpParam(name = "defaultContinuePrice", type = HttpParamType.COMMON, description = "默认续件数量") Integer defaultContinuePrice,
            @HttpParam(name = "freightTemplateCarriageDOList", type = HttpParamType.COMMON, description = "特殊运费区") List templateCarriageDOList,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询", permission = "operation:freight:query", permissionParentName = "运营管理", permissionName = "运费模板管理")
    public List<FreightTemplateDTO> getAllFreightTemplate(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
