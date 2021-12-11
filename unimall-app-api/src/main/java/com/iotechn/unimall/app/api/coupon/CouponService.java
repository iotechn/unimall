package com.iotechn.unimall.app.api.coupon;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.dto.CouponUserDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/4.
 */
@HttpOpenApi(group = "coupon", description = "优惠券服务")
public interface CouponService {

    @HttpMethod(description = "领取优惠券")
    public String obtainCoupon(
            @NotNull @HttpParam(name = "couponId", type = HttpParamType.COMMON, description = "优惠券Id") Long couponId,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取用户可领取优惠券")
    public List<CouponDTO> getObtainableCoupon(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取用户优惠券")
    public List<CouponUserDTO> getUserCoupons(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "获取会员用户可领取优惠券")
    public List<CouponDTO> getVipCoupons() throws ServiceException;

}
