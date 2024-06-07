package com.dobbinsoft.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/13.
 */
@Getter
public enum UserLoginType implements BaseEnums<Integer> {
    REGISTER(0, "手机注册"),
    MP_WEIXIN(1, "WX小程序登录"),
    APP_WEIXIN(2, "WX第三方登录"),
    H5_WEIXIN(3, "H5微信登录"),
    MP_ALI(4, "支付宝小程序"),
    APP_ALI(5, "支付宝第三方登录")
    ;

    UserLoginType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
