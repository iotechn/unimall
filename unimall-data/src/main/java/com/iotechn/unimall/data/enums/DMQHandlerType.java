package com.iotechn.unimall.data.enums;

public enum DMQHandlerType {
    ORDER_CANCEL(1, "定时取消订单");

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
