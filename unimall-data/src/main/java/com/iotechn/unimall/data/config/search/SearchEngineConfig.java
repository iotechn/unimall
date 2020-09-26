package com.iotechn.unimall.data.config.search;

import com.iotechn.unimall.data.properties.UnimallSearchEngineProperties;
import com.iotechn.unimall.data.search.AliOpenSearchEngine;
import com.iotechn.unimall.data.search.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 搜索引擎配置
 * User: rize
 * Date: 2020/8/14
 * Time: 15:57
 */
@Configuration
public class SearchEngineConfig {

    @Autowired
    private UnimallSearchEngineProperties unimallSearchEngineProperties;

    @Bean
    public SearchEngine searchEngine() {
        if ("opensearch".equals(unimallSearchEngineProperties.getEnable())) {
            return new AliOpenSearchEngine();
        }
        return null;
    }

}
