package com.iotechn.unimall.app.exception;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ServiceExceptionDefinition;

/**
 * Created by rize on 2019/7/1.
 */
public class AppExceptionDefinition {

    public static final ServiceExceptionDefinition APP_PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(10002, "参数校验失败");

    public static final ServiceExceptionDefinition USER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(11000, "用户系统未知异常");

    public static final ServiceExceptionDefinition USER_SEND_VERIFY_FAILED =
            new ServiceExceptionDefinition(11001, "发送验证码失败");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_EXIST =
            new ServiceExceptionDefinition(11002, "验证码未发送或已过期");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_CORRECT =
            new ServiceExceptionDefinition(11003, "验证码不正确");

    public static final ServiceExceptionDefinition USER_PHONE_HAS_EXISTED =
            new ServiceExceptionDefinition(11004, "手机已经被注册");

    public static final ServiceExceptionDefinition USER_PHONE_NOT_EXIST =
            new ServiceExceptionDefinition(11005, "手机尚未绑定账号");

    public static final ServiceExceptionDefinition USER_PHONE_OR_PASSWORD_NOT_CORRECT =
            new ServiceExceptionDefinition(11006, "手机号或密码错误!");


    public static final ServiceExceptionDefinition CART_UPDATE_FAILED =
            new ServiceExceptionDefinition(12001, "购物车更新失败");


    public static final ServiceExceptionDefinition ORDER_SKU_CANNOT_EMPTY =
            new ServiceExceptionDefinition(13001, "订单商品不能为空");

    public static final ServiceExceptionDefinition COUPON_ISSUE_OVER =
            new ServiceExceptionDefinition(14001, "优惠券已经领完～");

    public static final ServiceExceptionDefinition COUPON_YOU_HAVE_OBTAINED =
            new ServiceExceptionDefinition(14002, "您已经领取过了～");

    public static final ServiceExceptionDefinition COUPON_ACTIVITY_NOT_START =
            new ServiceExceptionDefinition(14003, "优惠券活动还没开始");

    public static final ServiceExceptionDefinition COUPON_ACTIVITY_HAS_END =
            new ServiceExceptionDefinition(14004, "优惠券活动已经结束");

    public static final ServiceExceptionDefinition COUPON_HAS_LOCKED =
            new ServiceExceptionDefinition(14005, "优惠券活动已经冻结！");



}
