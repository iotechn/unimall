package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ErpPlaceRefundSku {

    @NotNull(message = "请选择销售商品")
    private String barCode;

    @Range(min = 0, message = "商品数量至少为0")
    @NotNull(message = "退货数量不能为空")
    private BigDecimal quantity;

    @NotNull(message = "退货单价不能为空")
    private Integer price;

    private String title;

    @NotNull(message = "退货单位不能为空")
    private String unit;

    private String mono;

}
