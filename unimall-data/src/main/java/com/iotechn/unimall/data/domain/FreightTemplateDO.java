package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019-07-07
 * Time: 上午11:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("unimall_freight_template")
public class FreightTemplateDO extends SuperDO{

    private String title;

    //商品发货地址
    @TableField("spu_location")
    private String spuLocation;

    //多久时间内发货，一天还是几天
    @TableField("delivery_deadline")
    private Integer deliveryDeadline;

    //0包邮 -1永不包邮，正数表示满好多包邮
    @TableField("default_free_price")
    private Integer defaultFreePrice;

    /**
     * 第一次计价后，可以包含的商品重量
     * default:为了区分特殊地区字段，加的
     */
    @TableField("default_first_weight")
    private Integer defaultFirstWeight;

    /**
     * 第一次计价的价格
     */
    @TableField("default_first_price")
    private Integer defaultFirstPrice;

    /**
     * 商品数量超过了第一次计价后的商品重量，会续加一次价格，
     * 每续加一次价格，包含的商品重量
     */
    @TableField("default_continue_weight")
    private Integer defaultContinueWeight;

    /**
     * 续加计价的价格
     */
    @TableField("default_continue_price")
    private Integer defaultContinuePrice;
}
