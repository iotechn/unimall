package com.iotechn.unimall.data.properties;

import com.dobbinsoft.fw.support.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Description:
 * User: rize
 * Date: 2020/8/6
 * Time: 15:12
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.ALI_PAY_CONFIG_PREFIX)
public class UnimallAliPayProperties {

    private String gateway;

    private String mchPrivateKey;

    private String aliPublicKey;

    private String notifyUrl;

}
