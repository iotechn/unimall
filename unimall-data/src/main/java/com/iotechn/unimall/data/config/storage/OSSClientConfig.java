package com.iotechn.unimall.data.config.storage;

import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rize on 2019/4/26.
 */
@Configuration
public class OSSClientConfig {

    @Value("${oss.aliyun.oss.accessId}")
    private String accessId;
    @Value("${oss.aliyun.oss.accessKey}")
    private String accessKey;
    @Value("${oss.aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${oss.aliyun.oss.bucket}")
    private String bucket;
    @Value("${oss.aliyun.oss.dir}")
    private String dir;
    @Value("${oss.aliyun.oss.callbackUrl}")
    private String callbackUrl;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(endpoint, accessId, accessKey);
    }

}
