package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description: 商品活动价格表，用于记录商品参加促销活动时的价格
 * User: rize
 * Date: 2020/8/4
 * Time: 14:59
 */
@Data
@TableName("unimall_sku_activity_price")
public class SkuActivityPriceDO extends SuperDO {

    @TableField("activity_type")
    private Integer activityType;

    @TableField("activity_id")
    private Long activityId;

    @TableField("sku_id")
    private Long skuId;

    @TableField("activity_price")
    private Integer activityPrice;

}
