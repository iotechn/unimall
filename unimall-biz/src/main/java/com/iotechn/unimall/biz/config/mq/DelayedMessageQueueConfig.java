package com.iotechn.unimall.biz.config.mq;

import com.iotechn.unimall.biz.mq.handler.RedisNotifyHandler;
import com.iotechn.unimall.biz.mq.DelayedMessageQueue;
import com.iotechn.unimall.biz.mq.RedisNotifyDelayedMessageQueueImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DelayedMessageQueueConfig {

    @Bean
    public Map<Integer, RedisNotifyHandler> messageHandleRouter(List<RedisNotifyHandler> redisNotifyHandlerListdfssfsd) {
        return redisNotifyHandlerListdfssfsd.stream().collect(Collectors.toMap(RedisNotifyHandler::getCode, v -> v));
    }

    @Bean
    public DelayedMessageQueue delayedMessageQueue(){
        return new RedisNotifyDelayedMessageQueueImpl();
    }
}
