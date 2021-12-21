package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Description: 商品活动价格表，用于记录商品参加促销活动时的价格
 * User: rize
 * Date: 2020/8/4
 * Time: 14:59
 */
@Data
@ApiEntity(description = "商品活动价格表，用于记录商品参加促销活动时的价格")
@TableName("unimall_sku_activity_price")
public class SkuActivityPriceDO extends SuperDO {

    @ApiField(description = "活动类型")
    private Integer activityType;

    @ApiField(description = "活动ID")
    private Long activityId;

    @ApiField(description = "商品规格ID")
    private Long skuId;

    @ApiField(description = "活动价")
    private Integer activityPrice;

}
