package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/13.
 */
@Getter
public enum CouponType implements BaseEnums<Integer> {
    COMMON(1, "普通券"),
    CDKEY(2, "兑换券"),
    ;

    CouponType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;


}
