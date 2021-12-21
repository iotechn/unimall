package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class ErpStockNotifyDTO {

    private Long locationId;

    private List<ErpStockNotifyItemDTO> items;

}
