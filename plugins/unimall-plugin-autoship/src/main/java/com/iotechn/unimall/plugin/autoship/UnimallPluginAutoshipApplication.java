package com.iotechn.unimall.plugin.autoship;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.iotechn.unimall.plugin.autoship.mapper*")
@SpringBootApplication
public class UnimallPluginAutoshipApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnimallPluginAutoshipApplication.class, args);
    }

}
