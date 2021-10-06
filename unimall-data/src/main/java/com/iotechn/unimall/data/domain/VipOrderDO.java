package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
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

    private Integer source;

    private Integer price;

    private Integer commission;

    private Long templateId;

    private Integer status;

    private String phone;

    private Date gmtPay;

    private String payChannel;

    private String payId;

    private Long userId;

    private Long shareId;

    private String title;

    private String description;

}
