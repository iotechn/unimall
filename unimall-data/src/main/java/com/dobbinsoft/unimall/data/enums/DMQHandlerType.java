package com.dobbinsoft.unimall.data.enums;

import com.dobbinsoft.fw.core.enums.BaseEnums;
import lombok.Getter;

@Getter
public enum DMQHandlerType implements BaseEnums<Integer> {
    ORDER_AUTO_CANCEL(1, "订单自动取消"),
    ORDER_AUTO_CONFIRM(2,"订单自动收货"),
    VIP_ORDER_BUY_OVER(5,"VIP会员卡无法退款成功购买")
    ;

    DMQHandlerType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;

    private String msg;

}
