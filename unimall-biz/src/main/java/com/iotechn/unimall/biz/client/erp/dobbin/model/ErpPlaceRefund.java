package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ErpPlaceRefund {

    @NotNull(message = "渠道订单编号不能为空")
    @ApiField(description = "渠道编号")
    private String placeCode;

    @NotNull(message = "渠道退货商品不能为空")
    @ApiField(description = "退货商品")
    private List<ErpPlaceRefundSku> skuList;

}
