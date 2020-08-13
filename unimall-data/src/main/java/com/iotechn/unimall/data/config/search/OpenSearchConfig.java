package com.iotechn.unimall.data.config.search;

import com.iotechn.unimall.data.properties.UnimallOpenSearchProperties;
import com.iotechn.unimall.data.search.SearchInfo;
import com.iotechn.unimall.data.search.SpuSearchInfoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSearchConfig {

    @Bean
    public SearchInfo spuSearchInfoImpl(UnimallOpenSearchProperties unimallOpenSearchProperties){
        return new SpuSearchInfoImpl(unimallOpenSearchProperties.getAccessKeyId(),
                unimallOpenSearchProperties.getAccessKeySecret(),
                unimallOpenSearchProperties.getSpuHost(),
                unimallOpenSearchProperties.getSpuAppName(),
                unimallOpenSearchProperties.getSpuTableName());
    }
}
