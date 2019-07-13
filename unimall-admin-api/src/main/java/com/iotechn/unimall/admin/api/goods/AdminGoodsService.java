package com.iotechn.unimall.admin.api.goods;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.dto.goods.SpuTreeNodeDTO;
import com.iotechn.unimall.data.model.Page;

import java.util.List;

/**
 * Created by rize on 2019/7/11.
 */
@HttpOpenApi(group = "admin.goods", description = "管理员商品服务")
public interface AdminGoodsService {

    @HttpMethod(description = "获取SPU树")
    public List<SpuTreeNodeDTO> getSpuBigTree(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;


    @HttpMethod(description = "创建", permission = "operation:goods:create", permissionParentName = "商品管理", permissionName = "商品上架")
    public String create(
            @NotNull @HttpParam(name = "spuDTO", type = HttpParamType.COMMON, description = "商品JSON数据") SpuDTO spuDTO,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


    @HttpMethod(description = "编辑", permission = "operation:goods:edit", permissionParentName = "商品管理", permissionName = "商品上架")
    public String edit(
            @NotNull @HttpParam(name = "spuDTO", type = HttpParamType.COMMON, description = "商品JSON数据") SpuDTO spuDTO,
            @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "列表", permission = "operation:goods:list", permissionParentName = "运营管理", permissionName = "订单管理")
    public Page<SpuDTO> list(
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "20") Integer limit,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "搜索标题") String title,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

    @HttpMethod(description = "详情", permission = "operation:goods:detail", permissionParentName = "运营管理", permissionName = "订单管理")
    public SpuDTO detail(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员Id") Long adminId) throws ServiceException;

}
