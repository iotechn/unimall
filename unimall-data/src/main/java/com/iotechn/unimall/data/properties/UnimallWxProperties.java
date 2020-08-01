package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/19
 * Time: 16:12
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.iotechn.unimall.wx")
public class UnimallWxProperties {

    private String miniAppId;

    private String miniAppSecret;

    private String appId;

    private String appSecret;

    private String h5AppId;

    private String h5AppSecret;

    /*** 微信支付相关配置 ***/

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String keyPath;

}
