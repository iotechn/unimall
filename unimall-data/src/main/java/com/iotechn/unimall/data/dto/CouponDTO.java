package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.enums.CouponType;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/4.
 */
@Data
public class CouponDTO extends SuperDTO {

    @ApiField(description = "优惠券标题")
    private String title;

    @ApiField(description = "类型", enums = CouponType.class)
    private Integer type;

    @ApiField(description = "是否是vip专享")
    private Integer isVip;

    @ApiField(description = "优惠券描述")
    private String description;

    @ApiField(description = "总共发的数量")
    private Integer total;

    @ApiField(description = "剩余的数量")
    private Integer surplus;

    @ApiField(description = "每个用户限制领取数量")
    private Integer limit;

    @ApiField(description = "折扣")
    private Integer discount;

    @ApiField(description = "最低使用价格，单位分")
    private Integer min;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

    @ApiField(description = "指定仅这个类目可用")
    private Long categoryId;

    @ApiField(description = "指定类目标题")
    private String categoryTitle;

    @ApiField(description = "有效期多少天")
    private Integer days;

    @ApiField(description = "用户现在有的数量")
    private Integer nowCount;

    @ApiField(description = "可领取开始时间")
    private Date gmtStart;

    @ApiField(description = "可领取结束时间")
    private Date gmtEnd;

}
