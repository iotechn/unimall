package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/11.
 */
@Data
@ApiEntity(description = "商品树传输实体")
public class SpuTreeNodeDTO {

    @ApiField(description = "值")
    private String value;

    @ApiField(description = "标签")
    private String label;

    private Long id;

    @ApiField(description = "子节点")
    private List<SpuTreeNodeDTO> children;

}
