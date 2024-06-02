package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/13.
 */
@Getter
public enum OrderStatusType implements BaseEnums<Integer> {
    UNPAY(10, "未付款"),
    GROUP_SHOP_WAIT(12,"等待团购活动结束"),
    WAIT_STOCK(20, "待出库"),
    WAIT_CONFIRM(30, "待收货"),
    WAIT_APPRAISE(40, "待评价"),
    COMPLETE(50, "已完成"),
    REFUNDING(60, "退款中"),
    REFUNDED(70, "已退款"),
    CANCELED(80, "已取消"),
    CANCELED_SYS(90, "已取消（系统）");

    OrderStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;


    /**
     * 判断定订单是否可退款
     * @return
     */
    public static boolean refundable(int orderStatus) {
        return orderStatus == WAIT_STOCK.getCode() || orderStatus == WAIT_CONFIRM.getCode();
    }

}
