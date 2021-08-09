package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/2/13.
 */
public enum PayChannelType implements BaseEnums<String> {
    WEPAY("WX", "微信支付"),
    ALIPAY("ALI", "支付宝"),
    OFFLINE("OFFLINE", "线下支付"),
    ;

    PayChannelType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;

    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
