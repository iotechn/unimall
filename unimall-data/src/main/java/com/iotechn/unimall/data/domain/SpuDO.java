package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.iotechn.unimall.data.annotation.SearchField;
import com.iotechn.unimall.data.annotation.SearchTable;
import com.iotechn.unimall.data.enums.SearchEngineTokenizerType;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/1.
 */
@Data
@SearchTable("unimall_spu")
@TableName("unimall_spu")
public class SpuDO extends SuperDO {

    @SearchField("id")
    @TableField("id")
    private Long id;

    /**
     * 商品原价
     */
    @SearchField("original_price")
    @TableField("original_price")
    private Integer originalPrice;

    /**
     * 商品价格 单位 分
     */
    @SearchField("price")
    private Integer price;

    /**
     * 会员价格
     */
    @SearchField("vip_price")
    @TableField("vip_price")
    private Integer vipPrice;

    /**
     * 商品标题
     */
    @SearchField(value = "title", tokenizer = SearchEngineTokenizerType.STAND)
    private String title;

    /**
     * 商品销量
     */
    @SearchField("sales")
    private Integer sales;

    /**
     * 商品主图（冗余信息）
     */
    @SearchField("img")
    private String img;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 商品一句话描述
     */
    @SearchField(value = "description", tokenizer = SearchEngineTokenizerType.STAND)
    private String description;

    /**
     * 商品类目id
     */
    @SearchField("category_id")
    @TableField("category_id")
    private Long categoryId;

    /**
     * 运费模板Id
     */
    @SearchField("freight_template_id")
    @TableField("freight_template_id")
    private Long freightTemplateId;

    /**
     * 单位
     */
    @SearchField("unit")
    private String unit;

    /**
     * 商品状态
     */
    @SearchField("status")
    private Integer status;

    /**
     * 当前商品所参加的促销活动类型
     */
    @SearchField("activity_type")
    @TableField("activity_type")
    private Integer activityType;

    /**
     * 当前商品所参加促销活动ID
     */
    @SearchField("activity_id")
    @TableField("activity_id")
    private Long activityId;

    /**
     * 当前商品参加促销活动开始时间
     */
    @SearchField("gmt_activity_start")
    @TableField("gmt_activity_start")
    private Date gmtActivityStart;

    /**
     * 当前商品参加促销活动结束时间
     */
    @SearchField("gmt_activity_end")
    @TableField("gmt_activity_end")
    private Date gmtActivityEnd;

    @SearchField("gmt_update")
    @TableField("gmt_update")
    private Date gmtUpdate;

    @SearchField("gmt_create")
    @TableField("gmt_create")
    private Date gmtCreate;

}
