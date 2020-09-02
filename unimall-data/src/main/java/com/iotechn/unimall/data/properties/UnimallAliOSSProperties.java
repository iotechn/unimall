package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Description:
 * User: rize
 * Date: 2020/8/6
 * Time: 15:42
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.OSS_CONFIG_PREFIX)
public class UnimallAliOSSProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String dir;

    private String baseUrl;

    private String bucket;

}
