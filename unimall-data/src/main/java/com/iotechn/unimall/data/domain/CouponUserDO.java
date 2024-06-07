package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by rize on 2019/7/4.
 */
@Data
@ApiEntity(description = "用户领取的优惠券")
@TableName("unimall_coupon_user")
public class CouponUserDO extends SuperDO {

    @NotNull
    @ApiField(description = "用户ID")
    private Long userId;

    @NotNull
    @ApiField(description = "优惠券ID")
    private Long couponId;

    @ApiField(description = "使用订单的ID 若未使用为空")
    private Long orderId;

    @ApiField(description = "使用的时间 未使用未空")
    private LocalDateTime gmtUsed;

    @ApiField(description = "可使用开始时间")
    private LocalDateTime gmtStart;

    @ApiField(description = "可使用结束时间")
    private LocalDateTime gmtEnd;

}
