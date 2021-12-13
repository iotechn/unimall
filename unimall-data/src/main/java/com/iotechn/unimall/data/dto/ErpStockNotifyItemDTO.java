package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ErpStockNotifyItemDTO {

    private Long skuId;

    private String barCode;

    private BigDecimal quantity;

}
