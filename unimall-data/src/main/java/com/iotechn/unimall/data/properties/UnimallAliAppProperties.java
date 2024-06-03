package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "com.dobbinsoft.ali-app")
public class UnimallAliAppProperties {

    private String aliGateway;

    private String miniAppId;

    private String miniAppPublicKey1;

    private String miniAppPublicKey2;

    private String miniAppPrivateKey2;

    private String miniNotifyUrl;

    private String appId;

    private String appPublicKey1;

    private String appPublicKey2;

    private String appPrivateKey2;

    private String appNotifyUrl;

    private String webAppId;

    private String webAppPublicKey1;

    private String webAppPublicKey2;

    private String webAppPrivateKey2;

    private String webNotifyUrl;

    private String webReturnUrl;

    private String wapAppId;

    private String wapAppPublicKey1;

    private String wapAppPublicKey2;

    private String wapAppPrivateKey2;

    private String wapNotifyUrl;

    private String wapReturnUrl;

}