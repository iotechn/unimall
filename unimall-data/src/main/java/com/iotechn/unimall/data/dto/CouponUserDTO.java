package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/5.
 */
@Data
public class CouponUserDTO extends SuperDTO {

    @ApiField(description = "优惠券标题")
    private String title;

    @ApiField(description = "指定仅这个类目可用")
    private Long categoryId;

    @ApiField(description = "指定类目标题")
    private String categoryTitle;

    @ApiField(description = "折扣")
    private Integer discount;

    @ApiField(description = "最低使用价格，单位分")
    private Integer min;

    @ApiField(description = "所属用户ID")
    private Long userId;

    @ApiField(description = "优惠券ID")
    private Long couponId;

    @ApiField(description = "使用订单的ID 若未使用为空")
    private Long orderId;

    @ApiField(description = "使用的时间 未使用未空")
    private Date gmtUsed;

    @ApiField(description = "可使用开始时间")
    private Date gmtStart;

    @ApiField(description = "可使用结束时间")
    private Date gmtEnd;

}
