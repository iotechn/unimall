package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Created by rize on 2019/7/2.
 */
@TableName("unimall_spu_attribute")
@Data
@ApiEntity(description = "商品属性 eg 材质: 纯金")
public class SpuAttributeDO extends SuperDO {

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "属性")
    private String attribute;

    @ApiField(description = "属性值")
    private String value;

}
