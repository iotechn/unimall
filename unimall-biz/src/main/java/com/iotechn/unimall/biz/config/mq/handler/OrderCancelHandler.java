package com.iotechn.unimall.biz.config.mq.handler;

import com.iotechn.unimall.data.enums.DMQHandlerType;

public class OrderCancelHandler implements RedisNotifyHandler{

    @Override
    public int handle(String value) {

        return 0;
    }

    @Override
    public int getCode() {
        return DMQHandlerType.ORDER_CANCEL.getCode();
    }
}
