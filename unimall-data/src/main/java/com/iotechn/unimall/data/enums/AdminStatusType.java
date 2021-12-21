package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/2/11.
 */
public enum AdminStatusType implements BaseEnums<Integer> {
    LOCK(0, "冻结"),
    ACTIVE(1, "激活");


    private int code;

    private String msg;

    AdminStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


}
