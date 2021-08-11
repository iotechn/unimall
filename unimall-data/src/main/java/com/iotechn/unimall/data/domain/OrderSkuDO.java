package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.SpuActivityType;
import lombok.Data;

/**
 * Created by rize on 2019/7/5.
 */
@Data
@ApiEntity(description = "订单商品")
@TableName("unimall_order_sku")
public class OrderSkuDO extends SuperDO {

    @ApiField(description = "商品规格ID")
    private Long skuId;

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "订单主表ID")
    private Long orderId;

    @ApiField(description = "订单主表串号")
    private String orderNo;

    @ApiField(description = "商品标题")
    private String spuTitle;

    @ApiField(description = "SKU 标题， 即小规格名称")
    private String title;

    @ApiField(description = "商品条码")
    private String barCode;

    @ApiField(description = "商品数量")
    private Integer num;

    @ApiField(description = "商品原价")
    private Integer originalPrice;

    @ApiField(description = "商品单价")
    private Integer price;

    @ApiField(description = "主图 优先使用SKU图片")
    private String img;

    @ApiField(description = "商品单位")
    private String unit;

    @ApiField(description = "商品重量")
    private Integer weight;

    @ApiField(description = "商品参与活动的类型", enums = SpuActivityType.class)
    private Integer activityType;

    @ApiField(description = "商品参与活动的ID")
    private Long activityId;

}
