package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.domain.LocationSkuDO;
import com.iotechn.unimall.data.enums.SpuStatusType;
import lombok.Data;

import java.util.List;

@Data
public class LocationSpuDTO extends SuperDTO {

    @ApiField(description = "位置ID")
    private Long locationId;

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "是否上架", enums = SpuStatusType.class)
    private Integer status;

    @ApiField(description = "销量")
    private Integer sales;

    @ApiField(description = "位置商品")
    private List<LocationSkuDO> skuList;

}
