package com.iotechn.unimall.admin.api.appraise;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.model.Page;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-15
 * Time: 下午3:41
 */
@HttpOpenApi(group = "admin.appraise", description = "评论")
public interface AdminAppraise {

    @HttpMethod(description = "删除", permission = "operation:appraise:delete", permissionParentName = "运营管理", permissionName = "评论管理")
    public String delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "评论id") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询", permission = "operation:appraise:list", permissionParentName = "运营管理", permissionName = "评论管理")
    public Page<AppraiseResponseDTO> list(
            @HttpParam(name = "id", type = HttpParamType.COMMON, description = "评论id") Long id,
            @HttpParam(name = "userName", type = HttpParamType.COMMON, description = "用户姓名") String userName,
            @HttpParam(name = "spuName", type = HttpParamType.COMMON, description = "商品名字") String spuName,
            @HttpParam(name = "orderId", type = HttpParamType.COMMON, description = "订单ID") Long orderId,
            @HttpParam(name = "score", type = HttpParamType.COMMON, description = "评论id") Integer score,
            @HttpParam(name = "content", type = HttpParamType.COMMON, description = "评论id") String content,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码") Integer pageNo,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
