package com.iotechn.unimall.data.enums;

/**
 * Created by rize on 2019/7/2.
 */
public enum SpuActivityType {
    NONE(0, "没有活动"),
    GROUP_SHOP(1, "团购"),
            ;

    SpuActivityType(int code, String msg) {
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
