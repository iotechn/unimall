package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/7/2.
 */
public enum SpuActivityType implements BaseEnums<Integer> {
    NONE(0, "没有活动"),
    GROUP_SHOP(1, "团购"),
            ;

    SpuActivityType(int code, String msg) {
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
