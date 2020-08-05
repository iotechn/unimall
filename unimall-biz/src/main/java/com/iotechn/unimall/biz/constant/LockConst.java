package com.iotechn.unimall.biz.constant;

/**
 * Description:
 * User: rize
 * Date: 2020/8/1
 * Time: 12:11
 */
public class LockConst {
    /**
     * 领取优惠券加的锁
     * COUPON_LOCK: + couponId
     */
    public static final String COUPON_LOCK = "COUPON_LOCK:";

    /**
     *  用户领取优惠券的锁，防止同时领取，导致超领
     *  COUPON_USER_LOCK: + userID + ":" + couponId
     */
    public static final String COUPON_USER_LOCK = "COUPON_USER_LOCK:";

    /**
     * 防止用户重复提交锁
     * TAKE_ORDER: + USERID
     */
    public static final String TAKE_ORDER_LOCK = "TAKE_ORDER_LOCK:";

    /**
     * 订单状态修改锁
     * ORDER_STATUS_LOCK: + orderNO
     */
    public static final String ORDER_STATUS_LOCK = "ORDER_STATUS_LOCK:";

    /**
     * 订单退款乐观锁
     * ORDER_REFUND_LOCK: + orderNO
     */
    public static final String ORDER_REFUND_LOCK = "ORDER_REFUND_LOCK:";

    /**
     * 动态配置锁
     * DYNAMIC_CONFIG_LOCK: + key
     */
    public static final String DYNAMIC_CONFIG_LOCK = "DYNAMIC_CONFIG_LOCK:";

}
