package com.iotechn.unimall.biz.client.erp.dobbin.model;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ErpCategory implements Serializable {

    private Long id;

    @ApiField(description = "类目标题")
    private String title;

    @ApiField(description = "父节点ID")
    private Long parentId;

    @ApiField(description = "类目图片")
    private String img;

    @ApiField(description = "类目颜色")
    private String color;

    @ApiField(description = "类目等级")
    private Integer level;

    @ApiField(description = "类目全名")
    private String fullName;

    @ApiField(description = "子类目集合")
    private List<ErpCategory> children;

    private Date gmtUpdate;

    private Date gmtCreate;

}
