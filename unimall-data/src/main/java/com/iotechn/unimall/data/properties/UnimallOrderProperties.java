package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

@Data
@DynamicConfigProperties(prefix = DynamicConst.ORDER_CONFIG_PREFIX)
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
