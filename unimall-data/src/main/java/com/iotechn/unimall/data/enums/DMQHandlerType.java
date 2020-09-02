package com.iotechn.unimall.data.enums;

public enum DMQHandlerType {
    ORDER_AUTO_CANCEL(1, "订单自动取消"),
    ORDER_AUTO_CONFIRM(2,"订单自动收货")
    ;

    DMQHandlerType(int code, String msg) {
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
