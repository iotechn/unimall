package com.iotechn.unimall.biz.config;

import com.iotechn.unimall.biz.client.erp.ErpClient;
import com.iotechn.unimall.biz.client.erp.dobbin.MockErpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErpConfig {


    @Bean
    public ErpClient erpClient() {
        return new MockErpClient();
    }

}
