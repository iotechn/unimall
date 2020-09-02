package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/7/2.
 */
@Data
@TableName("unimall_sku")
public class SkuDO extends SuperDO {

    @TableField("spu_id")
    private Long spuId;

    @TableField("bar_code")
    private String barCode;

    /**
     * SKU显示文字
     */
    private String title;

    @TableField("specification")
    private String specification;

    private String img;

    @TableField("original_price")
    private Integer originalPrice;

    private Integer price;

    @TableField("vip_price")
    private Integer vipPrice;

    private Integer stock;

    private Integer weight;

}
