package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotaion.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

@Data
@DynamicConfigProperties(prefix = DynamicConst.OPEN_SEARCH_CONFIG_PREFIX)
public class UnimallOpenSearchProperties {

    /**
     * db : 数据库
     *  search: 开放搜索
     */
    private String enable;

    private String accessKeyId;

    private String accessKeySecret;

    private String spuAppName;

    private String spuHost;

    private String spuTableName;

}
