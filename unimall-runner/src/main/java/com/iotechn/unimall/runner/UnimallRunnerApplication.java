package com.iotechn.unimall.runner;

import com.anji.captcha.config.AjCaptchaAutoConfiguration;
import com.dobbinsoft.fw.launcher.manager.IApiManager;
import com.dobbinsoft.fw.support.annotation.EnableDelayedMQ;
import com.dobbinsoft.fw.support.annotation.EnableDynamicConfig;
import com.dobbinsoft.fw.support.annotation.EnableOpenPlatform;
import com.dobbinsoft.fw.support.component.MachineComponent;
import com.iotechn.unimall.admin.api.role.RoleServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

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

    private static final Logger logger = LoggerFactory.getLogger(UnimallRunnerApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(UnimallRunnerApplication.class, args);
        // 设置权限
        IApiManager apiManager = applicationContext.getBean(IApiManager.class);
        RoleServiceImpl roleService = applicationContext.getBean(RoleServiceImpl.class);
        roleService.setPerms(apiManager.getPermissions());
        // 触发获取一个机器号
        MachineComponent machineComponent = applicationContext.getBean(MachineComponent.class);
        machineComponent.getMachineNo();
        logger.info("[Unimall IoC] 系统初始化成功！");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // 当正常退出时，将机器号释放，以供其他机器使用
            if (machineComponent.isInit()) {
                machineComponent.release();
            }
        }));
    }

}
