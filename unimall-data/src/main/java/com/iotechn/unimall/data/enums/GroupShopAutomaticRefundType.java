package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/2/13.
 */
public enum GroupShopAutomaticRefundType implements BaseEnums<Integer> {
    NO(0, "发货并不退款"),
    YES(1, "不发货自动退款")
    ;

    GroupShopAutomaticRefundType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
