package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class ErpSkuLocationStock extends SuperDTO implements Serializable {

    private Long skuId;

    private Long locationId;

    private String locationTitle;

    private Integer stock;

    private String unit;

}
