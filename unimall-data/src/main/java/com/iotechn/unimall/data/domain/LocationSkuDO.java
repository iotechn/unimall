package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

@Data
@TableName("unimall_location_sku")
@ApiEntity(description = "位置（仓库）商品规格关联表")
public class LocationSkuDO extends SuperDO {

    @ApiField(description = "位置ID")
    private Long locationId;

    @ApiField(description = "商品ID 提供查询效率")
    private Long spuId;

    @ApiField(description = "商品规格ID")
    private Long skuId;

    @ApiField(description = "剩余库存")
    private Integer stock;

}
