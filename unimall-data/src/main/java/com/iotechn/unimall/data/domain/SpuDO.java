package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/1.
 */
@Data
@TableName("unimall_spu")
public class SpuDO extends SuperDO {

    @TableField("id")
    private Long id;

    /**
     * 商品原价
     */
    @TableField("original_price")
    private Integer originalPrice;

    /**
     * 商品价格 单位 分
     */
    private Integer price;

    /**
     * 会员价格
     */
    @TableField("vip_price")
    private Integer vipPrice;

    /**
     * 商品标题
     */
    private String title;

    /**
     * 商品销量
     */
    private Integer sales;

    /**
     * 商品主图（冗余信息）
     */
    private String img;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 商品一句话描述
     */
    private String description;

    /**
     * 商品类目id
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 运费模板Id
     */
    @TableField("freight_template_id")
    private Long freightTemplateId;

    /**
     * 单位
     */
    private String unit;

    /**
     * 商品状态
     */
    private Integer status;

    /**
     * 当前商品所参加的促销活动类型
     */
    @TableField("activity_type")
    private Integer activityType;

    /**
     * 当前商品所参加促销活动ID
     */
    @TableField("activity_id")
    private Long activityId;

    /**
     * 当前商品参加促销活动开始时间
     */
    @TableField("gmt_activity_start")
    private Date gmtActivityStart;

    /**
     * 当前商品参加促销活动结束时间
     */
    @TableField("gmt_activity_end")
    private Date gmtActivityEnd;

    @TableField("gmt_update")
    private Date gmtUpdate;

    @TableField("gmt_create")
    private Date gmtCreate;

}
