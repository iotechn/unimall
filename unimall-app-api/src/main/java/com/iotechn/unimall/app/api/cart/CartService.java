package com.iotechn.unimall.app.api.cart;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.CartDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/3.
 */
@HttpOpenApi(group = "cart", description = "购物车服务")
public interface CartService {

    @HttpMethod(description = "加入购物车")
    public Boolean addCartItem(
            @NotNull @HttpParam(name = "skuId", type = HttpParamType.COMMON, description = "商品Id") Long skuId,
            @Range(min = 1) @HttpParam(name = "num", type = HttpParamType.COMMON, description = "加入数量", valueDef = "1") Integer num,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "减少购物车")
    public Boolean subCartItem(
            @NotNull @HttpParam(name = "skuId", type = HttpParamType.COMMON, description = "商品Id") Long skuId,
            @Range(min = 1) @HttpParam(name = "num", type = HttpParamType.COMMON, description = "加入数量", valueDef = "1") Integer num,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "将购物车商品删除")
    public Boolean removeCartItem(
            @NotNull @HttpParam(name = "cartId", type = HttpParamType.COMMON, description = "商品ID(传入值有问题，skuId)") Long cartId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "批量删除购物车Item")
    public Boolean removeCartItemBatch(
            @NotNull @HttpParam(name = "cartIdList", type = HttpParamType.COMMON, description = "购物车id列表用,隔开") String cartIdList,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "清空购物车")
    public Boolean removeCartAll(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "调整购物车商品数量")
    public Integer updateCartItemNum(
            @NotNull @HttpParam(name = "cartId", type = HttpParamType.COMMON, description = "购物车ID") Long cartId,
            @Range(min = 1) @NotNull @HttpParam(name = "num", type = HttpParamType.COMMON, description = "商品数量") Integer num,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "购物车商品数量")
    public Integer countCart(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "获取用户购物车列表")
    public List<CartDTO> getCartList(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

}
