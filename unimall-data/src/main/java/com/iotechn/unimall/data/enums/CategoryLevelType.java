package com.iotechn.unimall.data.enums;

/**
 * Created by rize on 2019/2/11.
 */
public enum CategoryLevelType {
    ONE(0, "一级"),
    TWO(1, "二级"),
    THREE(2, "三级"),
    ;


    private int code;

    private String msg;

    CategoryLevelType(int code, String msg) {
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
