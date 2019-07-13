package com.iotechn.unimall.core.exception;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ServiceExceptionDefinition;

/**
 * Created by rize on 2019/7/1.
 */
public class ExceptionDefinition {

    public static final ServiceExceptionDefinition THIRD_PART_SERVICE_EXCEPTION =
            new ServiceExceptionDefinition(0, "第三方服务异常");

    public static final ServiceExceptionDefinition APP_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(10000, "系统未知异常");

    public static final ServiceExceptionDefinition PARAM_CHECK_FAILED =
            new ServiceExceptionDefinition(10002, "参数校验失败");

    public static final ServiceExceptionDefinition SYSTEM_BUSY =
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

    public static final ServiceExceptionDefinition USER_INFORMATION_MISSING =
            new ServiceExceptionDefinition(11010, "用户信息缺失，不能添加");

    public static final ServiceExceptionDefinition USER_PHONE_ALREADY_EXIST =
            new ServiceExceptionDefinition(11011, "用户电话已经存在，不能添加");

    public static final ServiceExceptionDefinition USER_CAN_NOT_ACTICE =
            new ServiceExceptionDefinition(11012, "用户处于冻结状态，请联系管理员");


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

    public static final ServiceExceptionDefinition ORDER_STATUS_CHANGE_FAILED =
            new ServiceExceptionDefinition(13013, "订单状态流转失败！");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_REFUND =
            new ServiceExceptionDefinition(13014, "订单状态不支持退款");

    public static final ServiceExceptionDefinition ORDER_REFUND_FAILED =
            new ServiceExceptionDefinition(13015, "微信退款失败 请检查微信账户余额");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_CANCEL =
            new ServiceExceptionDefinition(13016, "订单状态不支持取消");

    public static final ServiceExceptionDefinition ORDER_STATUS_NOT_SUPPORT_CONFIRM =
            new ServiceExceptionDefinition(13016, "订单状态不支持确认收货");

    public static final ServiceExceptionDefinition ORDER_HAS_NOT_SHIP =
            new ServiceExceptionDefinition(13017, "订单尚未发货");

    public static final ServiceExceptionDefinition ORDER_DID_NOT_SET_SHIP =
            new ServiceExceptionDefinition(13018, "订单不需要物流公司");


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
            new ServiceExceptionDefinition(15001, "该商品不允许重复收藏");

    public static final ServiceExceptionDefinition COLLECT_PARAM_CHECK_FAILED  =
            new ServiceExceptionDefinition(15002, "收藏记录传入参数校验失败");


    public static final ServiceExceptionDefinition ADDRESS_QUERY_FAILED  =
            new ServiceExceptionDefinition(16001, "这是个有地址却没有默认地址的用户");

    public static final ServiceExceptionDefinition ADDRESS_DATABASE_QUERY_FAILED  =
            new ServiceExceptionDefinition(16002, "执行语句失败");

    public static final ServiceExceptionDefinition APPRAISE_PARAM_CHECK_FAILED  =
            new ServiceExceptionDefinition(17001, "参数校验失败");

    public static final ServiceExceptionDefinition APPRAISE_ORDER_CHECK_FAILED  =
            new ServiceExceptionDefinition(17002, "当前状态不允许评价");


    public static final ServiceExceptionDefinition FREIGHT_PARAM_CHECK_FAILED  =
            new ServiceExceptionDefinition(18001, "大兄弟，你传给计算邮费的信息不对啊！");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_NOT_EXIST =
            new ServiceExceptionDefinition(18002, "运费模版不存在");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_INSERT_FAILED =
            new ServiceExceptionDefinition(18003, "运费模板主表增加失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_INSERT_FAILED =
            new ServiceExceptionDefinition(18004, "运费模板副表增加失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_DELETE_FAILED =
            new ServiceExceptionDefinition(18005, "运费模板主表删除失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_DELETE_FAILED =
            new ServiceExceptionDefinition(18006, "运费模板副表删除失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_UPDATE_FAILED =
            new ServiceExceptionDefinition(18007, "运费模板主表修改失败");

    public static final ServiceExceptionDefinition FREIGHT_CARRIAGE_UPDATE_FAILED =
            new ServiceExceptionDefinition(18008, "运费模板副表修改失败");

    public static final ServiceExceptionDefinition FREIGHT_TEMPLATE_QUERY_FAILED =
            new ServiceExceptionDefinition(18009, "运费模板主表查询失败");

    public static final ServiceExceptionDefinition FREIGHT_SPU_QUERY_HAS =
            new ServiceExceptionDefinition(18010, "当前仍有商品使用该模板");


    public static final ServiceExceptionDefinition FOOTPRINT_DELETE_FAILED  =
            new ServiceExceptionDefinition(19001, "大兄弟，没有你传的足迹，或者你在误删他人足迹");

    public static final ServiceExceptionDefinition FOOTPRINT_SELECT_PARAM_CHECK  =
            new ServiceExceptionDefinition(19002, "大兄弟，不会传分页数据，你就别传！有默认值");


    public static final ServiceExceptionDefinition GOODS_NOT_EXIST =
            new ServiceExceptionDefinition(20001, "商品并不存在");

    public static final ServiceExceptionDefinition GOODS_SKU_LIST_EMPTY =
            new ServiceExceptionDefinition(20002, "商品的类型（Sku）列表不能为空");

    public static final ServiceExceptionDefinition GOODS_CREATE_HAS_ID =
            new ServiceExceptionDefinition(20003, "创建商品时请不要传入Id");

    public static final ServiceExceptionDefinition GOODS_CREATE_BARCODE_REPEAT =
            new ServiceExceptionDefinition(20004, "商品条码已经存在了 商品Id:${0} 重复Sku:${1}");


    public static final ServiceExceptionDefinition RECOMMEND_SPU_NO_HAS =
            new ServiceExceptionDefinition(21001, "你要加入推荐的商品不存在");

    public static final ServiceExceptionDefinition RECOMMEND_ALREADY_HAS =
            new ServiceExceptionDefinition(21002, "你要加入推荐的商品已推荐");

    public static final ServiceExceptionDefinition RECOMMEND_SQL_ADD_FAILED =
            new ServiceExceptionDefinition(21003, "加入推荐数据库失败");

    public static final ServiceExceptionDefinition RECOMMEND_SQL_DELETE_FAILED =
            new ServiceExceptionDefinition(21004, "删除推荐数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_ADD_FAILED =
            new ServiceExceptionDefinition(22001, "添加广告数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_DELETE_FAILED =
            new ServiceExceptionDefinition(22002, "删除广告数据库失败");

    public static final ServiceExceptionDefinition ADVERTISEMENT_SQL_UPDATE_FAILED =
            new ServiceExceptionDefinition(22003, "修改广告数据库失败");


    public static final ServiceExceptionDefinition ADMIN_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(50000, "管理员系统未知异常");

    public static final ServiceExceptionDefinition ADMIN_NOT_EXIST =
            new ServiceExceptionDefinition(50001, "管理员不存在");

    public static final ServiceExceptionDefinition ADMIN_PASSWORD_ERROR =
            new ServiceExceptionDefinition(50002, "密码错误");

    public static final ServiceExceptionDefinition ADMIN_NOT_BIND_WECHAT =
            new ServiceExceptionDefinition(50003, "管理员尚未绑定微信");

    public static final ServiceExceptionDefinition ADMIN_APPLY_NOT_BELONGS_TO_YOU =
            new ServiceExceptionDefinition(50004, "用户申请表并不属于您");

    public static final ServiceExceptionDefinition ADMIN_APPLY_NOT_SUPPORT_ONE_KEY =
            new ServiceExceptionDefinition(50005, "未定义类型不支持一键发布");

    public static final ServiceExceptionDefinition ADMIN_ROLE_IS_EMPTY =
            new ServiceExceptionDefinition(50006, "管理员角色为空！");

    public static final ServiceExceptionDefinition ADMIN_USER_NAME_REPEAT =
            new ServiceExceptionDefinition(50007, "管理员用户名重复");





    public static ServiceExceptionDefinition buildVariableException(ServiceExceptionDefinition definition, String ...args) {
        String msg = definition.getMsg();
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("${" + i + "}", args[i]);
        }
        return new ServiceExceptionDefinition(definition.getCode(), msg);
    }




}
