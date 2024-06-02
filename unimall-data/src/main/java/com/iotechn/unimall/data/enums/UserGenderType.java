package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/13.
 */
@Getter
public enum UserGenderType implements BaseEnums<Integer> {
    UNKNOWN(0, "位置"),
    BOY(1, "男"),
    GIRL(2, "女"),
    ;

    UserGenderType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;


}
