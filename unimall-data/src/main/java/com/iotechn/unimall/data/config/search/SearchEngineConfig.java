package com.iotechn.unimall.data.config.search;

import com.iotechn.unimall.data.search.AliOpenSearchEngine;
import com.iotechn.unimall.data.search.SearchEngine;
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

    @Bean
    public SearchEngine searchEngine() {
        return new AliOpenSearchEngine();
    }

}
