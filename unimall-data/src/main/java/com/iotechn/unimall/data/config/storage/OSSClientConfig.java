package com.iotechn.unimall.data.config.storage;

import com.aliyun.oss.OSSClient;
import com.iotechn.unimall.data.properties.UnimallAliOSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rize on 2019/4/26.
 */
@Configuration
public class OSSClientConfig {

    @Autowired
    private UnimallAliOSSProperties unimallAliOSSProperties;

    @Bean
    public OSSClient ossClient() {
        return new OSSClient(unimallAliOSSProperties.getEndpoint(), unimallAliOSSProperties.getAccessKeyId(), unimallAliOSSProperties.getAccessKeySecret());
    }

}
