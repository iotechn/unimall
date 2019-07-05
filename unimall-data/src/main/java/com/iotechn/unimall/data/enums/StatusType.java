package com.iotechn.unimall.data.enums;

/**
 * 通用状态枚举  仅激活 冻结
 * Created by rize on 2019/2/13.
 */
public enum StatusType {
    LOCK(0, "冻结"),
    ACTIVE(1, "激活"),
    ;

    StatusType(int code, String msg) {
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
