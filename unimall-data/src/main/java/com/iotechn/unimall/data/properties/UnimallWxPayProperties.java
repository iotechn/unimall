package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "com.dobbinsoft.wx-pay")
public class UnimallWxPayProperties {

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String keyPath;

    private String keyContent;

}
