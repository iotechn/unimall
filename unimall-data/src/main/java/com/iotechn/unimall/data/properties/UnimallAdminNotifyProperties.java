package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: 管理员统一推送动态配置
 * User: rize
 * Date: 2020/8/24
 * Time: 21:14
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.dobbinsoft.admin-notify")
public class UnimallAdminNotifyProperties {

    private String enable;

    /**
     * Uninotify 服务器地址
     */
    private String uniNotifyUrl;

    /**
     * Uninotify AppId
     */
    private String uniNotifyAppId;

    /**
     * Uninotify AppSecret
     */
    private String uniNotifyAppSecret;

}
