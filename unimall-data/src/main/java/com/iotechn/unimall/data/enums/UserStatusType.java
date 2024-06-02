package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * 通用状态枚举  仅激活 冻结 未完善手机号
 * Created by rize on 2019/2/13.
 */
@Getter
public enum UserStatusType implements BaseEnums<Integer> {
    LOCK(0, "冻结"),
    ACTIVE(1, "激活"),
    WAIT_PHONE(2, "未完善手机号"),
    ;

    UserStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;


}
