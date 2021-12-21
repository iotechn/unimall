package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

import java.util.Date;

@Data
@TableName("unimall_vip_order")
public class VipOrderDO extends SuperDO {

    @ApiField(description = "VIP订单号")
    private String orderNo;

    @ApiField(description = "续费天数")
    private Integer dayNum;

    private Integer price;

    private Long templateId;

    private Integer status;

    private Date gmtPay;

    @ApiField(description = "支付渠道")
    private String payChannel;

    @ApiField(description = "支付时用的AppId")
    private String appId;

    private String payId;

    private Long userId;

    private String title;

    private String description;

}
