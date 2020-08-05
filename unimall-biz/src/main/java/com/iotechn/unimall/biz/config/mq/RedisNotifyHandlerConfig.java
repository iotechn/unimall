package com.iotechn.unimall.biz.config.mq;

import com.iotechn.unimall.biz.config.mq.handler.OrderCancelHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisNotifyHandlerConfig {

    @Bean
    public OrderCancelHandler orderCancelHandler(){
        return new OrderCancelHandler();
    }
}
