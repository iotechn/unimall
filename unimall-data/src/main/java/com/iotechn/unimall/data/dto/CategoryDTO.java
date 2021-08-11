package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.enums.CategoryLevelType;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@Data
@ApiEntity(description = "类目传输实体")
public class CategoryDTO extends SuperDTO {

    @ApiField(description = "一级类目ID")
    private Long firstLevelId;

    @ApiField(description = "二级类目ID")
    private Long secondLevelId;

    @ApiField(description = "父类目ID")
    private Long parentId;

    @ApiField(description = "类目标题")
    private String title;

    /**
     * 全类目名称：一级类目/二级类目/三级类目
     * 主要用于管理员查看
     */
    @ApiField(description = "类目全名")
    private String fullName;

    @ApiField(description = "等级", enums = CategoryLevelType.class)
    private Integer level;

    @ApiField(description = "图标URL")
    private String iconUrl;

    @ApiField(description = "图片URL")
    private String picUrl;

    @ApiField(description = "子类目")
    private List<CategoryDTO> childrenList;

}
