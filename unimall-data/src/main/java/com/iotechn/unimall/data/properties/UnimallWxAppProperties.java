package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/19
 * Time: 16:12
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.WX_APP_CONFIG_PREFIX)
public class UnimallWxAppProperties {

    private String miniAppId;

    private String miniAppSecret;

    private String appId;

    private String appSecret;

    private String h5AppId;

    private String h5AppSecret;


}
