package com.iotechn.unimall.biz.config.mq.handler;


public interface RedisNotifyHandler {

    public int handle(String value);

    public int getCode();
}
