package com.dobbinsoft.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/13.
 */
@Getter
public enum UserLevelType implements BaseEnums<Integer> {
    COMMON(0, "普通会员"),
    VIP(1, "VIP会员"),
    ;

    UserLevelType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;


}
