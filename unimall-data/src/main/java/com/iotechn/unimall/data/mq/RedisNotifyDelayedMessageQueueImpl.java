package com.iotechn.unimall.data.mq;

import com.iotechn.unimall.data.component.CacheComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;


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
}
