package com.iotechn.unimall.data.properties;

import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
import com.iotechn.unimall.data.constant.DynamicConst;
import lombok.Data;

/**
 * Description:
 * User: rize
 * Date: 2020/8/7
 * Time: 10:35
 */
@Data
@DynamicConfigProperties(prefix = DynamicConst.ADVERT_CONFIG_PREFIX)
public class UnimallAdvertProperties {

    /**
     * 首页TOP N 个数
     */
    private Integer topSalesNum;

}
