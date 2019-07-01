package com.iotechn.unimall.launcher;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.iotechn.unimall.data")
@SpringBootApplication(scanBasePackages = {"com.iotechn.unimall"})
public class UnimallLauncherApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnimallLauncherApplication.class, args);
	}

}
