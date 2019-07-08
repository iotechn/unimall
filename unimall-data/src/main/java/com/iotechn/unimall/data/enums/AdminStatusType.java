package com.iotechn.unimall.data.enums;

/**
 * Created by rize on 2019/2/11.
 */
public enum AdminStatusType {
    LOCK(0, "冻结"),
    ACTIVE(1, "激活");


    private int code;

    private String msg;

    AdminStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


}
