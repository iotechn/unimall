package com.iotechn.unimall.biz.config.mq;

import com.iotechn.unimall.biz.mq.handler.OrderAutoCancelHandler;
import com.iotechn.unimall.biz.mq.handler.OrderAutoConfirmHandle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisNotifyHandlerConfig {

    @Bean
    public OrderAutoCancelHandler orderAutoCancelHandler(){
        return new OrderAutoCancelHandler();
    }

    @Bean
    public OrderAutoConfirmHandle orderAutoConfirmHandle(){ return new OrderAutoConfirmHandle();}
}
