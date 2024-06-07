package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.VipOrderStatusType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("unimall_vip_order")
public class VipOrderDO extends SuperDO {

    @NotNull
    @ApiField(description = "VIP订单号")
    private String orderNo;

    @NotNull
    @ApiField(description = "续费天数")
    private Integer dayNum;

    @NotNull
    @ApiField(description = "价格")
    private Integer price;

    @NotNull
    @ApiField(description = "关联VIP模板ID")
    private Long templateId;

    @NotNull
    @ApiField(description = "状态", enums = VipOrderStatusType.class)
    private Integer status;

    @ApiField(description = "支付时间")
    private LocalDateTime gmtPay;

    @ApiField(description = "支付渠道")
    private String payChannel;

    @ApiField(description = "支付时用的AppId")
    private String appId;

    @ApiField(description = "支付流水号")
    private String payId;

    @NotNull
    @ApiField(description = "关联用户ID")
    private Long userId;

    @NotNull
    @ApiField(description = "标题")
    private String title;

    @ApiField(description = "描述")
    private String description;

}
