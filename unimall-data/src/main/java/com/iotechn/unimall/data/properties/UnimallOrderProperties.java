package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotaion.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

@Data
@DynamicConfigProperties(prefix = DynamicConst.ORDER_CONFIG_PREFIX)
public class UnimallOrderProperties {

    private Long autoCancelTime;

    private Long autoConfirmTime;

}
