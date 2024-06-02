package com.iotechn.unimall.data.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("com.dobbinsoft.order")
public class UnimallOrderProperties {

    /**
     * 单位是s
     */
    private Integer autoCancelTime;

    /**
     * 单位是s
     */
    private Integer autoConfirmTime;

}
