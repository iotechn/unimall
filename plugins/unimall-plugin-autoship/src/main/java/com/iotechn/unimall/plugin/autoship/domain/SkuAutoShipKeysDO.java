package com.iotechn.unimall.plugin.autoship.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.iotechn.unimall.data.domain.SuperDO;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/29
 * Time: 20:26
 */
@Data
@TableName("unimall_sku_auto_ship_keys")
public class SkuAutoShipKeysDO extends SuperDO {

    @TableField("sku_id")
    private Long skuId;

    private String cdk;

    /**
     * 可使用次数
     */
    private Integer times;

}
