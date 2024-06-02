package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "com.dobbinsoft.wx-app")
public class UnimallWxAppProperties {

    private String miniAppId;

    private String miniAppSecret;

    private String appId;

    private String appSecret;

    private String h5AppId;

    private String h5AppSecret;


}