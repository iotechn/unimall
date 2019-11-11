package com.iotechn.unimall.admin.api.recommend;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.RecommendDO;
import com.iotechn.unimall.data.dto.RecommendDTO;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.annotations.Param;

import java.rmi.ServerException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午3:42
 */

@HttpOpenApi(group = "admin.recommend", description = "推荐商品")
public interface AdminRecommendService {

    @HttpMethod(description = "创建", permission = "promote:recommend:create", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Boolean addRecommend(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "spuId", type = HttpParamType.COMMON, description = "商品Id") Long spuId,
            @NotNull @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "推荐类型") Integer recommendType) throws ServiceException;

    @HttpMethod(description = "删除", permission = "promote:recommend:delete", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Boolean deleteRecommend(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "推荐id") Long id,
            @NotNull @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "推荐类型") Integer recommendType) throws ServiceException;

    @HttpMethod(description = "查询", permission = "promote:recommend:query", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Page<RecommendDTO> queryRecommendByType(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "recommendType", type = HttpParamType.COMMON, description = "推荐类型") Integer recommendType,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer pageSize) throws ServiceException;

    @HttpMethod(description = "查询", permission = "promote:recommend:query", permissionParentName = "推广管理", permissionName = "推荐管理")
    public Page<RecommendDTO> queryAllRecommend(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页面长度", valueDef = "10") Integer pageSize) throws ServiceException;

}
