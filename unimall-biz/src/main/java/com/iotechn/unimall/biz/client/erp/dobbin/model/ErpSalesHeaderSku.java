package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description:
 * User: rize
 * Date: 2020/8/26
 * Time: 14:48
 */
@Data
public class ErpSalesHeaderSku implements Serializable {

    @NotNull(message = "请选择销售商品")
    private String barCode;

    @Range(min = 0, message = "商品数量至少为0")
    @NotNull(message = "销售数量不能为空")
    private BigDecimal quantity;

    @NotNull(message = "销售单价不能为空")
    private Integer price;

    private String title;

    @NotNull(message = "销售单位不能为空")
    private String unit;

    private String mono;

}
