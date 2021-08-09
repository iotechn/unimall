package com.iotechn.unimall.runner;

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
                RedisAutoConfiguration.class,
                RedisReactiveAutoConfiguration.class
        })
@MapperScan({
        "com.iotechn.unimall.data.mapper"
})
@EnableOpenPlatform
@EnableDynamicConfig
public class DemoRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRunnerApplication.class, args);
    }

}
