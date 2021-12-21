package com.iotechn.unimall.biz.config;

import com.iotechn.unimall.biz.client.erp.ErpClient;
import com.iotechn.unimall.biz.client.erp.dobbin.DobbinErpClient;
import com.iotechn.unimall.biz.client.erp.mock.ErpMockClient;
import com.iotechn.unimall.data.properties.UnimallErpOpenPlatformProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ErpConfig {

    @Autowired
    private UnimallErpOpenPlatformProperties unimallErpOpenPlatformProperties;

    @Bean
    public ErpClient erpClient() {
        if ("mock".equals(unimallErpOpenPlatformProperties.getEnable())) {
            return new ErpMockClient();
        } else if ("dobbin".equals(unimallErpOpenPlatformProperties.getEnable())) {
            return new DobbinErpClient();
        } else {
            return new ErpMockClient();
        }

    }

}
