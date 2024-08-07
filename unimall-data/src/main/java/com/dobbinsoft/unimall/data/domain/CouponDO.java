package com.dobbinsoft.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.dobbinsoft.unimall.data.enums.CouponType;
import com.dobbinsoft.unimall.data.enums.StatusType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by rize on 2019/7/4.
 */
@Data
@ApiEntity(description = "优惠券领域模型")
@TableName("unimall_coupon")
public class CouponDO extends SuperDO {

    @NotNull
    @ApiField(description = "优惠券标题")
    private String title;

    @NotNull
    @ApiField(description = "类型", enums = CouponType.class)
    private Integer type;

    /**
     * 是否是vip专享
     * 0:不是，1:是
     */
    @NotNull
    @ApiField(description = "是否是vip专享")
    private Integer isVip;

    @NotNull
    @ApiField(description = "优惠券描述")
    private String description;

    @NotNull
    @ApiField(description = "总共发的数量")
    private Integer total;

    @NotNull
    @ApiField(description = "剩余的数量")
    private Integer surplus;

    @NotNull
    @ApiField(description = "每个用户限制领取数量")
    @TableField("`limit`")
    private Integer limit;

    @NotNull
    @ApiField(description = "折扣")
    private Integer discount;

    @NotNull
    @ApiField(description = "最低使用价格，单位分")
    @TableField("`min`")
    private Integer min;

    @NotNull
    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    @ApiField(description = "指定仅这个类目可用")
    private Long categoryId;

    @ApiField(description = "有效期多少天")
    private Integer days;

    @ApiField(description = "可领取开始时间")
    private LocalDateTime gmtStart;

    @ApiField(description = "可领取结束时间")
    private LocalDateTime gmtEnd;

}
