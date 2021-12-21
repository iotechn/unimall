package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/2/13.
 */
public enum UserLevelType implements BaseEnums<Integer> {
    COMMON(0, "普通会员"),
    VIP(1, "VIP会员"),
    ;

    UserLevelType(int code, String msg) {
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
