package com.iotechn.unimall.data.constant;

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
     * 子订单状态修改锁
     * ORDER_STATUS_LOCK: + orderNO
     */
    public static final String ORDER_SUB_STATUS_LOCK = "ORDER_STATUS_LOCK:";

    /**
     * 父订单状态修改锁
     * ORDER_PARENT_STATUS_LOCK: + parentOrderNo
     */
    public static final String ORDER_PARENT_STATUS_LOCK = "ORDER_PARENT_STATUS_LOCK:";

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

    /**
     * 定时任务中：审查订单状态的锁 集群时，一台执行就够了
     * SCHEDULED_ORDER_STATUS_CHECK_LOCK
     */
    public static final String SCHEDULED_ORDER_STATUS_CHECK_LOCK = "SCHEDULED_ORDER_STATUS_CHECK_LOCK";

    /**
     * 团购开始定时任务
     * GROUP_SHOP_START_LOCK
     */
    public static final String GROUP_SHOP_START_LOCK = "GROUP_SHOP_START_LOCK";

    /**
     * 团购结束定时任务
     * GROUP_SHOP_END_LOCK
     */
    public static final String GROUP_SHOP_END_LOCK = "GROUP_SHOP_END_LOCK";

    /**
     *
     */
    public static final String GROUP_SHOP_LOCK_LOCK = "GROUP_SHOP_LOCK_LOCK";
}
