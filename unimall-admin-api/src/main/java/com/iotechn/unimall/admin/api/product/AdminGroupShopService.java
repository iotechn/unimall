package com.iotechn.unimall.admin.api.product;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.dto.product.GroupShopDTO;

@HttpOpenApi(group = "admin.groupshop", description = "管理团购商品服务")
public interface AdminGroupShopService {

    @HttpMethod(description = "增加", permission = "product:groupshop:create", permissionParentName = "商品管理", permissionName = "团购商品管理")
    public String create(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "spuId") Long spuId,
            @NotNull @HttpParam(name = "gmtStart", type = HttpParamType.COMMON, description = "团购开始时间戳") Long gmtStart,
            @NotNull @HttpParam(name = "gmtEnd", type = HttpParamType.COMMON, description = "团购结束时间戳") Long gmtEnd,
            @NotNull @Range(min = 1) @HttpParam(name = "minNum", type = HttpParamType.COMMON, description = "团购最低人数") Integer minNum,
            @NotNull @HttpParam(name = "automaticRefund", type = HttpParamType.COMMON, description = "团购人数未满是否自动退款") Integer automaticRefund,
            @NotNull @HttpParam(name = "groupShopSkuList", type = HttpParamType.COMMON, description = "团购sku链表") String groupShopSkuListStr,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "product:groupshop:delete", permissionParentName = "商品管理", permissionName = "团购商品管理")
    public String delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "团购商品的Id") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws  ServiceException;

    @HttpMethod(description = "修改", permission = "product:groupshop:edit", permissionParentName = "商品管理", permissionName = "团购商品管理")
    public GroupShopDTO edit(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "团购商品spuID") Long id,
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "spuId") Long spuId,
            @NotNull @HttpParam(name = "gmtStart", type = HttpParamType.COMMON, description = "团购开始时间戳") Long gmtStart,
            @NotNull @HttpParam(name = "gmtEnd", type = HttpParamType.COMMON, description = "团购结束时间戳") Long gmtEnd,
            @NotNull @Range(min = 1) @HttpParam(name = "minNum", type = HttpParamType.COMMON, description = "团购最低人数") Integer minNum,
            @NotNull @HttpParam(name = "automaticRefund", type = HttpParamType.COMMON, description = "团购人数未满是否自动退款") Integer automaticRefund,
            @NotNull @HttpParam(name = "groupShopSkuList", type = HttpParamType.COMMON, description = "团购sku链表") String groupShopSkuDOListStr,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询", permission = "product:groupshop:list", permissionParentName = "商品管理", permissionName = "团购商品管理")
    public Page<GroupShopDTO> list(
            @HttpParam(name = "id", type = HttpParamType.COMMON, description = "团购商品的ID") Long id,
            @HttpParam(name = "spuName", type = HttpParamType.COMMON, description = "团购商品的名称") String spuName,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "团购商品的状态") Integer status,
            @Range(min = 1) @HttpParam(name = "page", type = HttpParamType.COMMON, description = "团购商品页码", valueDef = "1") Integer page,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "团购商品长度", valueDef = "20") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

}
