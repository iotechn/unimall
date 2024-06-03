package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: ERP开放平台配置
 * User: rize
 * Date: 2020/8/24
 * Time: 21:14
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.dobbinsoft.erp")
public class UnimallErpOpenPlatformProperties {

    private String enable;

    /**
     * dobbin 客户识别代码
     */
    private String dobbinClientCode;

    /**
     * dobbin 服务端公钥
     */
    private String dobbinServerPublicKey;

    /**
     * dobbin 客户端公钥
     */
    private String dobbinClientPublicKey;

    /**
     * dobbin 客户端私钥
     */
    private String dobbinClientPrivateKey;

    /**
     * 租户ID
     */
    private String dobbinTenementId;

}
