package com.iotechn.unimall.data.properties;

import com.dobbinsoft.fw.support.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Description: ERP开放平台配置
 * User: rize
 * Date: 2020/8/24
 * Time: 21:14
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.ADMIN_ERP_OPEN_PLATFORM_PREFIX)
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
