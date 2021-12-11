package com.iotechn.unimall.runner;

import com.anji.captcha.config.AjCaptchaAutoConfiguration;
import com.dobbinsoft.fw.support.annotation.EnableDelayedMQ;
import com.dobbinsoft.fw.support.annotation.EnableDynamicConfig;
import com.dobbinsoft.fw.support.annotation.EnableOpenPlatform;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = {
                "com.iotechn.unimall",
                "com.dobbinsoft.fw"
        },
        exclude = {
                AjCaptchaAutoConfiguration.class,
                RedisAutoConfiguration.class,
                RedisReactiveAutoConfiguration.class
        })
@MapperScan({
        "com.iotechn.unimall.data.mapper"
})
@EnableOpenPlatform
@EnableDynamicConfig
@EnableDelayedMQ
public class UnimallRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnimallRunnerApplication.class, args);
    }

}
