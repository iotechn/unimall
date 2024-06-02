package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/13.
 */
@Getter
public enum BizType implements BaseEnums<Integer> {
    GOODS(1, "商品"),
    APPRAISE(2, "评价")
    ;

    BizType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;


}
