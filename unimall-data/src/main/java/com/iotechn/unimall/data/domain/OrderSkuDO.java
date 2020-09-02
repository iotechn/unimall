package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/7/5.
 */
@Data
@TableName("unimall_order_sku")
public class OrderSkuDO extends SuperDO {

    @TableField("sku_id")
    private Long skuId;

    /**
     * 冗余SPUID方便评论
     */
    @TableField("spu_id")
    private Long spuId;

    @TableField("order_id")
    private Long orderId;

    /**
     * 冗余，方便技术查库
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * SPU 标题
     */
    @TableField("spu_title")
    private String spuTitle;

    /**
     * SKU 标题， 即小规格名称
     */
    private String title;

    @TableField("bar_code")
    private String barCode;

    private Integer num;

    @TableField("original_price")
    private Integer originalPrice;

    /**
     * 单价
     */
    private Integer price;

    /**
     * SKU 或 SPU主图 （优先使用SKU图）
     */
    private String img;

    /**
     * 商品单位
     */
    private String unit;

    /**
     * 商品重量 (g)
     */
    private Integer weight;

    /**
     * 活动类型
     */
    @TableField("activity_type")
    private Integer activityType;

    /**
     * 活动Id
     */
    @TableField("activity_id")
    private Long activityId;

}
