package com.iotechn.unimall.data.enums;

/**
 * Created by rize on 2019/2/13.
 */
public enum GroupShopAutomaticRefundType {
    NO(0, "NO"),
    YES(1, "Yes")
    ;

    GroupShopAutomaticRefundType(int code, String msg) {
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
