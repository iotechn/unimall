package com.iotechn.unimall.biz.mq;

import com.iotechn.unimall.data.component.CacheComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.Callable;


public class RedisNotifyDelayedMessageQueueImpl implements DelayedMessageQueue {

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public Boolean publishTask(Integer code, String value, Integer delay) {
        if(delay < 0){
            delay = 1;
        }
        cacheComponent.putRaw(assembleKey(code,value),"",delay);
        return true;
    }

    @Override
    public Boolean deleteTask(Integer code, String value) {
        cacheComponent.del(assembleKey(code,value));
        return true;
    }

    @Override
    public Long getTaskTime(Integer code, String value) {
        return cacheComponent.getKeyExpire(assembleKey(code,value));
    }

    @Override
    public Boolean publishTask(Callable task, Integer delay) {
        throw new RuntimeException();
    }

    private String assembleKey(Integer code, String value){
        if(value == null){
            value = "";
        }
        StringBuilder sb = new StringBuilder("TASK:");
        sb.append(code + ":");
        sb.append(value);
        return sb.toString();
    }
}
