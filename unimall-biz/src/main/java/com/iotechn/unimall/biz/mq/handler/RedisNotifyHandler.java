package com.iotechn.unimall.biz.mq.handler;


public interface RedisNotifyHandler {

    /**
     *
     * @param value
     * @return 处理成功的返回大于0结果,失败返回0
     */
    public int handle(String value);

    public int getCode();
}
