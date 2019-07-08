package com.iotechn.unimall.app.exception;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ServiceExceptionDefinition;

/**
 * Created by rize on 2019/7/1.
 */
public class AppExceptionDefinition {

    public static final ServiceExceptionDefinition APP_PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(10002, "参数校验失败");

    public static final ServiceExceptionDefinition APP_SYSTEM_BUSY =
            new ServiceExceptionDefinition(10007, "系统繁忙～");

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

    public static final ServiceExceptionDefinition USER_THIRD_PART_LOGIN_FAILED =
            new ServiceExceptionDefinition(11007, "用户第三方登录失败");

    public static final ServiceExceptionDefinition USER_THIRD_UNEXPECT_RESPONSE =
            new ServiceExceptionDefinition(11008, "第三方登录期望之外的错误");

    public static final ServiceExceptionDefinition USER_THIRD_PART_NOT_SUPPORT =
            new ServiceExceptionDefinition(11009, "未知的第三方登录平台");


    public static final ServiceExceptionDefinition CART_UPDATE_FAILED =
            new ServiceExceptionDefinition(12001, "购物车更新失败");

    public static final ServiceExceptionDefinition ORDER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(13000, "订单系统未知异常");

    public static final ServiceExceptionDefinition ORDER_SKU_CANNOT_EMPTY =
            new ServiceExceptionDefinition(13001, "订单商品不能为空");

    public static final ServiceExceptionDefinition ORDER_SYSTEM_BUSY =
            new ServiceExceptionDefinition(13002, "订单系统繁忙~");

    public static final ServiceExceptionDefinition ORDER_SKU_STOCK_NOT_ENOUGH =
            new ServiceExceptionDefinition(13003, "商品库存不足～");

    public static final ServiceExceptionDefinition ORDER_SKU_NOT_EXIST =
            new ServiceExceptionDefinition(13004, "商品并不存在~");

    public static final ServiceExceptionDefinition ORDER_PRICE_MUST_GT_ZERO =
            new ServiceExceptionDefinition(13005, "订单金额必须大于0");

    public static final ServiceExceptionDefinition ORDER_PRICE_CHECK_FAILED =
            new ServiceExceptionDefinition(13006, "订单金额校验失败！");

    public static final ServiceExceptionDefinition ORDER_COUPON_NOT_EXIST =
            new ServiceExceptionDefinition(13007, "优惠券不存在或已使用！");

    public static final ServiceExceptionDefinition ORDER_COUPON_PRICE_NOT_ENOUGH =
            new ServiceExceptionDefinition(13008, "优惠券金额未达到指定值");

    public static final ServiceExceptionDefinition ORDER_COUPON_DISCOUNT_CHECK_FAILED =
            new ServiceExceptionDefinition(13009, "订单优惠券金额校验失败");

    public static final ServiceExceptionDefinition ORDER_ADDRESS_NOT_BELONGS_TO_YOU =
            new ServiceExceptionDefinition(13010, "收货地址不属于您！");

    public static final ServiceExceptionDefinition ORDER_NOT_EXIST =
            new ServiceExceptionDefinition(13011, "订单并不存在");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_PAY =
            new ServiceExceptionDefinition(13012, "订单状态不支持支付");



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

    public static final ServiceExceptionDefinition COUPON_STRATEGY_INCORRECT =
            new ServiceExceptionDefinition(14006, "优惠券策略不正确");


    public static final ServiceExceptionDefinition COLLECT_ALREADY_EXISTED =
            new ServiceExceptionDefinition(15001, "该商品已经收藏");

    public static final ServiceExceptionDefinition COLLECT_PARAM_CHECK_FAILED  =
            new ServiceExceptionDefinition(15002, "收藏记录传入参数校验失败");

    public static final ServiceExceptionDefinition ADDRESS_QUERY_FAILED  =
            new ServiceExceptionDefinition(16001, "这是个有地址却没有默认地址的用户");

    public static final ServiceExceptionDefinition ADDRESS_DATABASE_QUERY_FAILED  =
            new ServiceExceptionDefinition(16002, "执行语句失败");

    public static final ServiceExceptionDefinition APPRAISE_PARAM_CHECK_FAILED  =
            new ServiceExceptionDefinition(17001, "参数校验失败");

    public static final ServiceExceptionDefinition APPRAISE_Order_CHECK_FAILED  =
            new ServiceExceptionDefinition(17002, "当前状态不允许评价");

}
