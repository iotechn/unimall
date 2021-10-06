package com.iotechn.unimall.data.dto.order;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.domain.OrderRefundSkuDO;
import com.iotechn.unimall.data.enums.RefundStatusType;
import lombok.Data;

import java.util.List;

@Data
public class OrderRefundDTO extends SuperDTO {

    @ApiField(description = "订单ID")
    private Long orderId;

    @ApiField(description = "订单串号")
    private String orderNo;

    @ApiField(description = "应退金额（根据商品列表计算）")
    private Integer refundPrice;

    @ApiField(description = "状态", enums = RefundStatusType.class)
    private Integer status;

    @ApiField(description = "商品列表")
    private List<OrderRefundSkuDO> skuList;

}
