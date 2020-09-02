package com.iotechn.unimall.app.api.favorite;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.FavoriteDTO;
import com.iotechn.unimall.data.model.Page;

/*
@author kbq
@date  2019/7/5 - 10:13
*/
@HttpOpenApi(group = "favorite", description = "收藏表单")
public interface FavoriteService {

    @HttpMethod(description = "增加收藏记录")
    public String create(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品ID") Long spuId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "删除收藏记录")
    public String delete(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品ID") Long spuId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "查询某一用户收藏记录")
    public Page<FavoriteDTO> list(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId,
            @HttpParam(name = "pageNo", valueDef = "1", type = HttpParamType.COMMON, description = "分页查询偏移量") Integer pageNo,
            @HttpParam(name = "pageSize", valueDef = "10", type = HttpParamType.COMMON, description = "分页查询长度") Integer pageSize) throws ServiceException;

    @HttpMethod(description = "查询某一条收藏记录")
    public FavoriteDTO getFavoriteById(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId,
            @NotNull @HttpParam(name = "collectId", type = HttpParamType.COMMON, description = "收藏记录id") Long collectId,
            @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品ID") Long spuId) throws ServiceException;

    @HttpMethod(description = "判断用户是否收藏")
    public Boolean getFavoriteBySpuId(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

}
