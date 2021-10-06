package com.iotechn.unimall.data.dto.order;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.iotechn.unimall.data.domain.OrderRefundSkuDO;
import lombok.Data;

import java.util.List;

/**
 * 用户申请退款请求
 */
@Data
public class OrderRefundRequestDTO {

    @NotNull(message = "请传入订单编号")
    @ApiField(description = "订单编号")
    private String orderNo;

    @ApiField(description = "退款原因")
    private String reason;

    @NotNull(message = "请选择商品")
    @ApiField(description = "退款商品列表")
    private List<OrderRefundSkuDO> skuList;

}
