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
}
