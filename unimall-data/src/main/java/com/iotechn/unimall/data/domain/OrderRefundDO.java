package com.iotechn.unimall.data.domain;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.RefundStatusType;
import lombok.Data;

/**
 * 订单退款申请表
 */
@Data
public class OrderRefundDO extends SuperDO {

    @ApiField(description = "订单ID")
    private Long orderId;

    @ApiField(description = "订单串号")
    private String orderNo;

    @ApiField(description = "应退金额（根据商品列表计算）")
    private Integer refundPrice;

    @ApiField(description = "状态", enums = RefundStatusType.class)
    private Integer status;

}
