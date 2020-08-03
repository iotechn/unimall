package com.iotechn.unimall.data.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/26
 * Time: 18:52
 */
public enum AdvertUnionType {
    PRODUCT(1, "产品"),
    CATEGORY(2, "品类"),
    STORE(3, "店铺"),
    CLASSIFY(4, "分类"),
    KEYWORDS(5, "关键字"),
    PAGE(6, "页面"),;

    AdvertUnionType(int code, String msg) {
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
