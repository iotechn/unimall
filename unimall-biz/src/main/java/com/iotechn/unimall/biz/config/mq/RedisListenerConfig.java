package com.iotechn.unimall.biz.config.mq;

import com.iotechn.unimall.biz.mq.listener.RedisExpiredListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.HashMap;

@Configuration
public class RedisListenerConfig {

    @Value("${spring.redis.database}")
    private int cacheDB;

    @Bean
    public RedisExpiredListener expiredListener(){
        return new RedisExpiredListener();
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory defaultLettuceConnectionFactory, RedisExpiredListener expiredListener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(defaultLettuceConnectionFactory);
        container.addMessageListener(expiredListener, new PatternTopic("__keyevent@" + cacheDB + "__:expired"));
       // container.addMessageListener(new RedisExpiredListener(), new PatternTopic("__keyevent@*__:expired"));
        return container;
    }
}
