package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description:
 * User: rize
 * Date: 2020/8/7
 * Time: 10:35
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.dobbinsoft.advert")
public class UnimallAdvertProperties {

    /**
     * 首页TOP N 个数
     */
    private Integer topSalesNum;

}
