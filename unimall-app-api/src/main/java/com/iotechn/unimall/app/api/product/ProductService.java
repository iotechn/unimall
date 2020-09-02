package com.iotechn.unimall.app.api.product;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.model.Page;

/**
 * Created by rize on 2019/7/1.
 */
@HttpOpenApi(group = "product", description = "商品服务")
public interface ProductService {

    @HttpMethod(description = "搜索商品列表")
    public Page<SpuDO> getProductPage(
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer pageSize,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "搜索分类") Long categoryId,
            @HttpParam(name = "orderBy", type = HttpParamType.COMMON, description = "排序 id 或 sales", valueDef = "id") String orderBy,
            @HttpParam(name = "isAsc", type = HttpParamType.COMMON, description = "是否升序", valueDef = "false") Boolean isAsc,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "搜索标题") String title) throws ServiceException;

    @HttpMethod(description = "获取商品详情")
    public SpuDTO getProduct(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

}
