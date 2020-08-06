package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 上午11:41
 */
@Data
@TableName("unimall_freight_template_carriage")
public class FreightTemplateCarriageDO extends SuperDO{


    //指定使用该运费计算的运费模板,必有存在
    @TableField("template_id")
    private Long templateId;

    //指定该运费的区域
    @TableField("designated_area")
    private String designatedArea;

    @TableField("free_price")
    private Integer freePrice;

    //首次记件重量，未超过重量不加价
    @TableField("first_weight")
    private Integer firstWeight;

    //首次记件重量价格
    @TableField("first_price")
    private Integer firstPrice;

    //续件一次的重量
    @TableField("continue_weight")
    private Integer continueWeight;

    //续件一次重量的价格
    @TableField("continue_price")
    private Integer continuePrice;


}
