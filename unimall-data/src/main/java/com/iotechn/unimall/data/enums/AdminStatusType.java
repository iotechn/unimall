package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/11.
 */
@Getter
public enum AdminStatusType implements BaseEnums<Integer> {
    LOCK(0, "冻结"),
    ACTIVE(1, "激活");


    private Integer code;

    private String msg;

    AdminStatusType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
