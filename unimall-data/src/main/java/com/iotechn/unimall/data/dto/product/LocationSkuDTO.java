package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.enums.SpuStatusType;
import lombok.Data;

@Data
public class LocationSkuDTO extends SuperDTO {

    @ApiField(description = "位置ID")
    private Long locationId;

    @ApiField(description = "位置标题")
    private String locationTitle;

    @ApiField(description = "商品ID 提供查询效率")
    private Long spuId;

    @ApiField(description = "商品销量")
    private Integer spuSales;

    @ApiField(description = "商品状态", enums = SpuStatusType.class)
    private Integer spuStatus;

    @ApiField(description = "商品规格ID")
    private Long skuId;

    @ApiField(description = "剩余库存")
    private Integer stock;

}
