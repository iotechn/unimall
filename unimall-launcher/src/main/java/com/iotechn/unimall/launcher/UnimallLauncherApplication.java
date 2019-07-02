package com.iotechn.unimall.launcher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.iotechn.unimall.data")
@SpringBootApplication(scanBasePackages = {"com.iotechn.unimall"}, exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
@EnableTransactionManagement
public class UnimallLauncherApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnimallLauncherApplication.class, args);
	}

}
