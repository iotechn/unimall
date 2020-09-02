package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

@Data
@DynamicConfigProperties(prefix = DynamicConst.OPEN_SEARCH_CONFIG_PREFIX)
public class UnimallSearchEngineProperties {

    /**
     *  db : 不使用
     *  opensearch: 阿里云OpenSearch
     */
    private String enable;

    private String openSearchAccessKeyId;

    private String openSearchAccessKeySecret;

    /**
     * API 网关地址
     */
    private String openSearchHost;

}
