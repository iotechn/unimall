package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * Created by rize on 2019/2/13.
 */
public enum LocationType implements BaseEnums<Integer> {
    STORE(1, "门店"),
    WAREHOUSE(2, "仓库"),
    VIRTUAL(3, "虚拟仓")
    ;

    LocationType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
