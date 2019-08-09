package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by rize on 2019/7/4.
 */
@Data
@TableName("unimall_coupon")
@AllArgsConstructor
@NoArgsConstructor
public class CouponDO extends SuperDO {

    private String title;

    private Integer type;

    private String description;

    private Integer total;

    private Integer surplus;

    private Integer limit;

    private Integer discount;

    private Integer min;

    private Integer status;

    @TableField("category_id")
    private Long categoryId;

    private Integer days;

    @TableField("gmt_start")
    private Date gmtStart;

    @TableField("gmt_end")
    private Date gmtEnd;

}
