package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/4.
 */
@Data
@ApiEntity(description = "用户领取的优惠券")
@TableName("unimall_coupon_user")
public class CouponUserDO extends SuperDO {

    @ApiField(description = "用户ID")
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
