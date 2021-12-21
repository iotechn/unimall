package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/7/2.
 */
public enum SpuSortType implements BaseEnums<String> {
    TIME("TIME", "创建时间"),
    SALES("SALES", "销量"),
    PRICE("PRICE", "价格"),
    ;

    SpuSortType(String code, String msg) {
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
