package com.iotechn.unimall.app.config.wx;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.iotechn.unimall.data.properties.UnimallWxPayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rize on 2019/6/9.
 */
@Configuration
public class WxConfig {

    @Autowired
    private UnimallWxPayProperties properties;

    @Bean
    public WxPayService wxPayService() {
        WxPayService wxPayService = new WxPayServiceImpl() {
            @Override
            public WxPayConfig getConfig() {
                WxPayConfig wxPayConfig = new WxPayConfig();
                wxPayConfig.setMchId(properties.getMchId());
                wxPayConfig.setMchKey(properties.getMchKey());
                wxPayConfig.setNotifyUrl(properties.getNotifyUrl());
                wxPayConfig.setKeyPath(properties.getKeyPath());
                return wxPayConfig;
            }
        };
        return wxPayService;
    }
}
