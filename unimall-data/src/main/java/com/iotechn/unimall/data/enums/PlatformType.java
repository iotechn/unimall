package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/2/13.
 */
public enum PlatformType implements BaseEnums<Integer> {
    MP_WEIXIN(1, "WX小程序"),
    APP(2, "APP"),
    H5(3, "H5"),
    MP_ALI(4, "支付宝小程序")
    ;

    PlatformType(int code, String msg) {
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
