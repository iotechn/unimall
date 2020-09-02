package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/4.
 */
@Data
@TableName("unimall_coupon")
public class CouponDO extends SuperDO {

    private String title;

    private Integer type;

    private String description;

    private Integer total;

    private Integer surplus;

    @TableField("`limit`")
    private Integer limit;

    private Integer discount;

    @TableField("`min`")
    private Integer min;

    /**
     * 0:下架
     * 1: 正常
     */
    private Integer status;

    @TableField("category_id")
    private Long categoryId;

    private Integer days;

    @TableField("gmt_start")
    private Date gmtStart;

    @TableField("gmt_end")
    private Date gmtEnd;

}
