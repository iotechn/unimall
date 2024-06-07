package com.dobbinsoft.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

@Getter
public enum VipOrderStatusType implements BaseEnums<Integer> {

    WAIT_EXCHANGE(10, "待兑换"),
    EXCHANGE_OVER(20, "已兑换"),
    WAIT_BUY(30,"待支付"),
    WAIT_REFUND(40, "退款期"),
    REFUND_OVER(50,"已退款"),
    BUY_OVER(60,"已购买")
    ;

    VipOrderStatusType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
