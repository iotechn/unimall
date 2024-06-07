package com.dobbinsoft.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

/**
 * Created by rize on 2019/2/11.
 */
@Getter
public enum CategoryLevelType implements BaseEnums<Integer> {
    ONE(0, "一级"),
    TWO(1, "二级"),
    ;


    private Integer code;

    private String msg;

    CategoryLevelType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
