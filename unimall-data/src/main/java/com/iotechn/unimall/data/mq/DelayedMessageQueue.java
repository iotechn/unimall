package com.iotechn.unimall.data.mq;

import java.util.concurrent.Callable;

public interface DelayedMessageQueue {


    /**
     * 添加延迟秒数,ring使用的
     * * @param delay seconds
     * @return
     */
    public Boolean publishTask(Callable task,Integer delay);

}
