package com.iotechn.unimall.data.mq;

import com.iotechn.unimall.data.component.CacheComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.Callable;


public class RedisNotifyDelayedMessageQueueImpl implements DelayedMessageQueue {

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public Boolean publishTask(Callable task, Integer delay) {
        if (delay <= 0) {
            delay = 1;
        }

        String name = UUID.randomUUID().toString().replace("-", "");

        cacheComponent.putObj("TASK:" + name,task);
        cacheComponent.putRaw("TASK:" + name, "", delay);
        return true;
    }
}
