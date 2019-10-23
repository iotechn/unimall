package com.iotechn.unimall.admin.api.coupon;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.dto.CouponAdminDTO;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.model.Page;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-12
 * Time: 下午10:47
 */

@HttpOpenApi(group = "admin.coupon", description = "优惠卷")
public interface AdminCouponService {

    @HttpMethod(description = "创建", permission = "promote:coupon:create", permissionParentName = "推广管理", permissionName = "优惠管理")
    public CouponDO addCoupon(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "优惠卷标题") String title,
            @NotNull @HttpParam(name = "type", type = HttpParamType.COMMON, description = "优惠卷类别，如满减") Integer type,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "优惠卷描述") String description,
            @NotNull @HttpParam(name = "total", type = HttpParamType.COMMON, description = "优惠卷总数") Integer total,
            @NotNull @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "用户限制领取") Integer limit,
            @NotNull @HttpParam(name = "discount", type = HttpParamType.COMMON, description = "优惠价格") Integer discount,
            @NotNull @HttpParam(name = "min", type = HttpParamType.COMMON, description = "满足优惠的最低价格") Integer min,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "优惠卷状态") Integer status,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "优惠类别") Long categoryId,
            @HttpParam(name = "days", type = HttpParamType.COMMON, description = "优惠时长") Integer days,
            @HttpParam(name = "gmtStart", type = HttpParamType.COMMON, description = "优惠开始时间") Long gmtStart,
            @HttpParam(name = "gmtEnd", type = HttpParamType.COMMON, description = "优惠结束时间") Long gmtEnd) throws ServiceException;

    @HttpMethod(description = "删除", permission = "promote:coupon:delete", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Boolean deleteCoupon(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "优惠卷ID") Long id) throws ServiceException;

    @HttpMethod(description = "修改", permission = "promote:coupon:update", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Boolean updateCoupon(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "优惠卷ID") Long id,
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "优惠卷标题") String title,
            @NotNull @HttpParam(name = "type", type = HttpParamType.COMMON, description = "优惠卷类别，如满减") Integer type,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "优惠卷描述") String description,
            @NotNull @HttpParam(name = "total", type = HttpParamType.COMMON, description = "优惠卷总数") Integer total,
            @NotNull @HttpParam(name = "surplus", type = HttpParamType.COMMON, description = "优惠卷剩余") Integer surplus,
            @NotNull @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "用户限制领取") Integer limit,
            @NotNull @HttpParam(name = "discount", type = HttpParamType.COMMON, description = "优惠价格") Integer discount,
            @NotNull @HttpParam(name = "min", type = HttpParamType.COMMON, description = "满足优惠的最低价格") Integer min,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "优惠卷状态") Integer status,
            @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "优惠类别") Long categoryId,
            @HttpParam(name = "days", type = HttpParamType.COMMON, description = "优惠时长") Integer days,
            @HttpParam(name = "gmtStart", type = HttpParamType.COMMON, description = "优惠开始时间") Date gmtStart,
            @HttpParam(name = "gmtEnd", type = HttpParamType.COMMON, description = "优惠结束时间") Date gmtEnd) throws ServiceException;

    @HttpMethod(description = "修改", permission = "promote:coupon:update", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Boolean updateCouponStatus(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "优惠卷ID") Long id,
            @NotNull @HttpParam(name = "status", type = HttpParamType.COMMON, description = "优惠卷状态") Integer status) throws ServiceException;


    @HttpMethod(description = "查询", permission = "promote:coupon:query", permissionParentName = "推广管理", permissionName = "优惠管理")
    public Page<CouponAdminDTO> queryCouponByTitle(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "优惠卷标题") String title,
            @HttpParam(name = "type", type = HttpParamType.COMMON, description = "优惠卷类型") Integer type,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "优惠卷状态") Integer status,
            @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit) throws ServiceException;

}
