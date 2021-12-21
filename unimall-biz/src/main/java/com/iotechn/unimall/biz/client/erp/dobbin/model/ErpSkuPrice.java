package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 商品价格，字段增加请注意：价格需要以 Price 结尾 或 level 开头。前端是循环去展开价格的。
 * User: rize
 * Date: 2021/3/28
 * Time: 14:53
 */
@Data
public class ErpSkuPrice extends SuperDO implements Serializable {

    private Long skuId;
    /**
     * 删除单位，同时删除商品 价格
     */
    private String unit;

    private Integer purchasePrice;

    private Integer purchaseMaxPrice;

    /**
     * 成本价
     */
    private Integer costPrice;

    private Integer deliveryPrice;

    private Integer wholesalePrice;

    /**
     * 零售价
     */
    private Integer salesPrice;

    /**
     * 最低销售价
     */
    private Integer salesMinPrice;

    private Integer level1;

    private Integer level2;

    private Integer level3;

    private Integer level4;

    private Integer level5;

}
