package com.iotechn.unimall.data.enums;

/**
 * Created by rize on 2019/2/13.
 */
public enum OrderStatusType {
    UNPAY(10, "未付款"),
    WAIT_STOCK(20, "待出库"),
    WAIT_CONFIRM(30, "待收货"),
    WAIT_APPRAISE(40, "待评价"),
    COMPLETE(50, "已完成"),
    CANCELING(60, "取消中"),
    CANCELED(70, "已取消"),
    CANCELED_SYS(80, "已取消（系统）");

    OrderStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
