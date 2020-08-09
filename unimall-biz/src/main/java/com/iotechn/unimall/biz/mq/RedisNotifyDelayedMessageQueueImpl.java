package com.iotechn.unimall.biz.mq;

import com.iotechn.unimall.data.component.CacheComponent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.concurrent.Callable;


public class RedisNotifyDelayedMessageQueueImpl implements DelayedMessageQueue {

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public Boolean publishTask(Integer code, String value, Integer delay) {
        if(delay < 0){
            delay = 1;
        }
        if(value == null){
            value = "";
        }
        StringBuilder sb = new StringBuilder("TASK:");
        sb.append(UUID.randomUUID().toString().replace("-","") + ":");
        sb.append(code + ":");
        sb.append(value);
        cacheComponent.putRaw(sb.toString(),"",delay);
        return true;
    }

    @Override
    public Boolean publishTask(Callable task, Integer delay) {
        throw new RuntimeException();
    }
}
