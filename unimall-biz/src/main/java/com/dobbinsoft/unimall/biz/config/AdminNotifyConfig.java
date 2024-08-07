package com.dobbinsoft.unimall.biz.config;

import com.dobbinsoft.unimall.biz.service.notify.AdminNotifyBizService;
import com.dobbinsoft.unimall.biz.service.notify.MockAdminNotifyBizServiceImpl;
import com.dobbinsoft.unimall.biz.service.notify.UniNotifyAdminNotifyBizServiceImpl;
import com.dobbinsoft.unimall.data.properties.UnimallAdminNotifyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/12/27
 * Time: 20:15
 */
@Configuration
public class AdminNotifyConfig {

    @Autowired
    private UnimallAdminNotifyProperties unimallAdminNotifyProperties;

    @Bean
    public AdminNotifyBizService adminNotifyBizService() {
        if ("mock".equalsIgnoreCase(unimallAdminNotifyProperties.getEnable())) {
            return new MockAdminNotifyBizServiceImpl();
        } else if ("uninotify".equalsIgnoreCase(unimallAdminNotifyProperties.getEnable())) {
            return new UniNotifyAdminNotifyBizServiceImpl();
        } else {
            return new MockAdminNotifyBizServiceImpl();
        }
    }

}
