package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/7/1.
 */
@TableName("unimall_spu")
@Data
public class SpuDO extends SuperDO {

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

    //运费模板
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

}
