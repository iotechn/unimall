package com.iotechn.unimall.data.enums;

/**
 * Created by rize on 2019/2/13.
 */
public enum BizType {
    GOODS(1, "商品"),
    APPRAISE(2, "评价")
    ;

    BizType(int code, String msg) {
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
