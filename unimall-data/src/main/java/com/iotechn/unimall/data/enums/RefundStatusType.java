package com.iotechn.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;

/**
 * 退款状态
 * Created by rize
 */
public enum RefundStatusType implements BaseEnums<Integer> {
    WAIT_AUDIT(0, "待审核"),
    APPROVE(1, "同意"),
    REJECT(2, "拒绝"),
    ;

    RefundStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
