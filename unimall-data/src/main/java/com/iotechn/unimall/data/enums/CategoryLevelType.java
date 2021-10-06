package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/2/11.
 */
public enum CategoryLevelType implements BaseEnums<Integer> {
    ONE(0, "一级"),
    TWO(1, "二级"),
    ;


    private int code;

    private String msg;

    CategoryLevelType(int code, String msg) {
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
