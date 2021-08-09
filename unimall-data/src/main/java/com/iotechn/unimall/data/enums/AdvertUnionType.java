package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/26
 * Time: 18:52
 */
public enum AdvertUnionType implements BaseEnums<Integer> {
    PRODUCT(1, "产品"),
    CATEGORY(2, "类目"),
    KEYWORDS(3, "关键字"),
    PAGE(4, "页面"),;

    AdvertUnionType(int code, String msg) {
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
