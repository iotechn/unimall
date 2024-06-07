package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Description:
 * User: rize
 * Date: 2020/8/1
 * Time: 17:24
 */
@Data
@TableName("unimall_spu_specification")
@ApiEntity(description = "商品规格维度表")
public class SpuSpecificationDO extends SuperDO {

    @NotNull
    @ApiField(description = "商品ID")
    private Long spuId;

    @NotNull
    @ApiField(description = "维度标题 eg: 颜色、尺寸")
    private String title;

}
