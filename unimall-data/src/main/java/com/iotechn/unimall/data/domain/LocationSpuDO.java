package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.SpuStatusType;
import lombok.Data;

@Data
@TableName("unimall_location_spu")
@ApiEntity(description = "位置（仓库）商品关联表，用于记录位置销量等信息")
public class LocationSpuDO extends SuperDO {

    @ApiField(description = "位置ID")
    private Long locationId;

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "是否上架", enums = SpuStatusType.class)
    private Integer status;

    @ApiField(description = "销量")
    private Integer sales;

}
