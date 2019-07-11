package com.iotechn.unimall.data.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午6:46
 */

public enum  AdvertisementType {

    CAROUSEL(1,"轮播"),
    LISTFIGURE(2,"商户列表三图")
    ;

    AdvertisementType(int code, String msg) {
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

