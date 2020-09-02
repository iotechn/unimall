package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Description: 管理员统一推送动态配置
 * User: rize
 * Date: 2020/8/24
 * Time: 21:14
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.ADMIN_NOTIFY_CONFIG_PREFIX)
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
