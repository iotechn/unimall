package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotaion.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

@Data
@DynamicConfigProperties(prefix = DynamicConst.OPEN_SEARCH_CONFIG_PREFIX)
public class UnimallOpenSearchProperties {

    private String accessKeyId;

    private String accessKeySecret;

}
