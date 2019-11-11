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
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.appraise.AppraiseRequestDTO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.model.Page;

@HttpOpenApi(group = "appraise", description = "评价商品")
public interface AppraiseService {

    @HttpMethod(description = "增加评价")
    public Boolean addAppraise(
            @NotNull @HttpParam(name = "appraiseRequestDTO", type = HttpParamType.COMMON, description = "来自订单的评价数据") AppraiseRequestDTO appraiseRequestDTO,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId) throws ServiceException;

    @HttpMethod(description = "根据评论Id删除评论")
    public Boolean deleteAppraiseById(
            @NotNull @HttpParam(name = "appraiseId", type = HttpParamType.COMMON, description = "评论ID") Long appraiseId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId) throws ServiceException;


    @HttpMethod(description = "查询用户所有评论")
    public Page<AppraiseResponseDTO> getUserAllAppraise(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, valueDef = "1", description = "查询页数") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, valueDef = "10", description = "查询长度") Integer pageSize) throws ServiceException;


    @HttpMethod(description = "查询商品的所有评论")
    public Page<AppraiseResponseDTO> getSpuAllAppraise(
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, valueDef = "1", description = "查询页数") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, valueDef = "10", description = "查询长度") Integer pageSize) throws ServiceException;

    @HttpMethod(description = "查询某一条评论")
    public AppraiseResponseDTO getOneById(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "上传用户ID") Long userId,
            @NotNull @HttpParam(name = "AppraiseId", type = HttpParamType.COMMON, description = "评论Id") Long appraiseId) throws ServiceException;


}
