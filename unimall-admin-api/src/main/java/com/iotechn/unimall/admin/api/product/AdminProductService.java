package com.iotechn.unimall.admin.api.product;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.dto.product.AdminSpuDTO;
import com.iotechn.unimall.data.dto.product.LocationSpuDTO;
import com.iotechn.unimall.data.dto.product.SpuDTO;
import com.iotechn.unimall.data.dto.product.SpuTreeNodeDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/11.
 */
@HttpOpenApi(group = "admin.product", description = "管理员商品服务")
public interface AdminProductService {

    @HttpMethod(description = "获取SPU树")
    public List<SpuTreeNodeDTO> getSpuBigTree(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "创建", permission = "product:product:create", permissionParentName = "商品管理", permissionName = "商品管理")
    public String create(
            @NotNull @HttpParam(name = "spuDTO", type = HttpParamType.COMMON, description = "商品JSON数据") AdminSpuDTO spuDTO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "编辑", permission = "product:product:edit", permissionParentName = "商品管理", permissionName = "商品管理")
    public String edit(
            @NotNull @HttpParam(name = "spuDTO", type = HttpParamType.COMMON, description = "商品JSON数据") AdminSpuDTO spuDTO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "位置列表", permission = "product:product:listlocation", permissionParentName = "商品管理", permissionName = "商品管理")
    public List<LocationSpuDTO> listLocation(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品ID") Long spuId,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "列表", permission = "product:product:list", permissionParentName = "商品管理", permissionName = "商品管理")
    public Page<AdminSpuDTO> list(
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "搜索标题") String title,
            @HttpParam(name = "barcode", type = HttpParamType.COMMON, description = "商品条形码") String barcode,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "商品状态") Integer status,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "详情", permission = "product:product:detail", permissionParentName = "商品管理", permissionName = "商品管理")
    public AdminSpuDTO detail(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "上下架", permission = "product:product:shelves", permissionParentName = "商品管理", permissionName = "商品管理")
    public String shelves(
            @NotNull @HttpParam(name = "locationId", type = HttpParamType.COMMON, description = "位置ID") Long locationId,
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品ID") Long spuId,
            @NotNull @Range(min = 0, max = 1) @HttpParam(name = "status", type = HttpParamType.COMMON, description = "商品想要变为的状态") Integer status,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "编辑库存", permission = "product:product:editstock", permissionParentName = "商品管理", permissionName = "商品管理")
    public String editStock(
            @NotNull @HttpParam(name = "locationId", type = HttpParamType.COMMON, description = "位置ID") Long locationId,
            @NotNull @HttpParam(name = "skuId", type = HttpParamType.COMMON, description = "商品规格ID") Long skuId,
            @NotNull @Range(min = 0) @HttpParam(name = "stock", type = HttpParamType.COMMON, description = "欲设置为的库存") Integer stock,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "product:product:delete", permissionParentName = "商品管理", permissionName = "商品管理")
    public String delete(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "批量删除", permission = "product:product:batchdelete", permissionParentName = "商品管理", permissionName = "商品管理")
    public String batchDelete(
            @NotNull @HttpParam(name = "ids", type = HttpParamType.COMMON, description = "商品Id") String ids,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "重建商品缓存", permission = "product:product:rebuild", permissionParentName = "商品管理", permissionName = "商品管理")
    public String rebuildProductCache(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

}
