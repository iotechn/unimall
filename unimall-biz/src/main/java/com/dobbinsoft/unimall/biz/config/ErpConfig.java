package com.dobbinsoft.unimall.biz.config;

import com.dobbinsoft.unimall.biz.client.erp.ErpClient;
import com.dobbinsoft.unimall.biz.client.erp.dobbin.MockErpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErpConfig {


    @Bean
    public ErpClient erpClient() {
        return new MockErpClient();
    }

}
