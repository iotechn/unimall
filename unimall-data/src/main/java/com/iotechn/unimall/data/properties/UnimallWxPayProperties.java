package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Description:
 * User: rize
 * Date: 2020/8/6
 * Time: 15:12
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.WX_PAY_CONFIG_PREFIX)
public class UnimallWxPayProperties {

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String keyPath;

}
