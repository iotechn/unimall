package com.iotechn.unimall.core.config.sms;

import com.iotechn.unimall.core.notify.AliyunSMSClient;
import com.iotechn.unimall.core.notify.MockSMSClient;
import com.iotechn.unimall.core.notify.QCloudSMSClient;
import com.iotechn.unimall.core.notify.SMSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rize on 2019/7/2.
 */
@Configuration
public class SMSConfig {

    @Value("${sms.enable}")
    private String enable;

    @Bean
    public SMSClient smsClient() {
        if ("qcloud".equals(enable)) {
            return new QCloudSMSClient();
        } else if ("aliyun".equals(enable)) {
            return new AliyunSMSClient();
        } else if ("mock".equals(enable)) {
            return new MockSMSClient();
        } else {
            return new MockSMSClient();
        }
    }
}
