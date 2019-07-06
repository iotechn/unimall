package com.iotechn.unimall.app.api.appraise;

/*
@author kbq
@date  2019/7/6 - 10:18
*/

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.AppraiseDTO;

import java.util.List;

@HttpOpenApi(group = "appraise", description = "评价商品")
public interface AppraiseService {

    @HttpMethod(description = "增加评价")
    public Boolean addAppraise(@NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId
            , @NotNull @HttpParam(name = "skuId", type = HttpParamType.COMMON, description = "商品skuId") Long skuId
            , @NotNull @HttpParam(name = "orderId", type = HttpParamType.COMMON, description = "订单Id") Long orderId
            , @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId
            , @HttpParam(name = "content", type = HttpParamType.COMMON, description = "评论内容") String content
            , @NotNull @Range(min = 1, max = 5) @HttpParam(name = "score", type = HttpParamType.COMMON, description = "评价星数") Integer score
            , @HttpParam(name = "imgUrl", type = HttpParamType.COMMON, description = "图片url,以，号分隔") String imgUrl) throws ServiceException;

    @HttpMethod(description = "根据评论Id删除评论")
    public Boolean deleteAppraise(@NotNull @HttpParam(name = "appraiseId", type = HttpParamType.COMMON, description = "评论ID") Long appraiseId
            , @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId) throws ServiceException;


    @HttpMethod(description = "查询用户所有评论")
    public List<AppraiseDTO> getUserAllAppraise(@NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId
            , @HttpParam(name = "page", type = HttpParamType.COMMON, valueDef = "1", description = "查询页数") Integer page
            , @HttpParam(name = "size", type = HttpParamType.COMMON, valueDef = "10", description = "查询长度") Integer size) throws ServiceException;

    @HttpMethod(description = "查询商品的所有评论")
    public List<AppraiseDTO> getSpuAllAppraise(@NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId
            , @NotNull @HttpParam(name="spuId",type = HttpParamType.COMMON,description = "商品Id") Long spuId
            , @HttpParam(name = "page", type = HttpParamType.COMMON, valueDef = "1", description = "查询页数") Integer page
            , @HttpParam(name = "size", type = HttpParamType.COMMON, valueDef = "10", description = "查询长度") Integer size) throws ServiceException;

    @HttpMethod(description = "查询某一条评论")
    public AppraiseDTO getOneById(@NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId
            , @NotNull @HttpParam(name="AppraiseId",type = HttpParamType.COMMON,description = "评论Id") Long AppraiseId) throws ServiceException;


}
