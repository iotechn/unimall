package com.dobbinsoft.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * 通用状态枚举  仅激活 冻结
 * Created by rize on 2019/2/13.
 */
@Getter
public enum StatusType implements BaseEnums<Integer> {
    LOCK(0, "冻结"),
    ACTIVE(1, "激活"),
    ;

    StatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;


}
