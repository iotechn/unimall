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

    @Value("${cn.easycampus.aliyun.oss.accessId}")
    private String accessId;
    @Value("${cn.easycampus.aliyun.oss.accessKey}")
    private String accessKey;
    @Value("${cn.easycampus.aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${cn.easycampus.aliyun.oss.bucket}")
    private String bucket;
    @Value("${cn.easycampus.aliyun.oss.dir}")
    private String dir;
    @Value("${cn.easycampus.aliyun.oss.callbackUrl}")
    private String callbackUrl;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(endpoint, accessId, accessKey);
    }

}
