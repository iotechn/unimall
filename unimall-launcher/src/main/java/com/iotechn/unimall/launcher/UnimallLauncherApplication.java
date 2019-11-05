package com.iotechn.unimall.launcher;

import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.plugin.core.inter.IPluginPreOrder;
import com.iotechn.unimall.plugin.core.manager.PluginsManager;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@MapperScan("com.iotechn.unimall.data")
@SpringBootApplication(scanBasePackages = {"com.iotechn.unimall"}, exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
@EnableTransactionManagement
public class UnimallLauncherApplication {

	private static final Logger logger = LoggerFactory.getLogger(UnimallLauncherApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(UnimallLauncherApplication.class, args);
		logger.info("[系统初始化完毕]");
	}

}
