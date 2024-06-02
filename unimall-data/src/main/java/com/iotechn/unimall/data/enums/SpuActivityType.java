package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/7/2.
 */
@Getter
public enum SpuActivityType implements BaseEnums<Integer> {
    NONE(0, "没有活动"),
    GROUP_SHOP(1, "团购"),
            ;

    SpuActivityType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
