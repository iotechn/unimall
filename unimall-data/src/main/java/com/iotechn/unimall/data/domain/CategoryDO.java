package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.CategoryLevelType;
import lombok.Data;

/**
 * Created by rize on 2019/7/2.
 */
@Data
@ApiEntity(description = "类目领域模型")
@TableName("unimall_category")
public class CategoryDO extends SuperDO {

    @ApiField(description = "类目名称")
    private String title;

    @ApiField(description = "第三方ERP ID")
    private String thirdId;

    /**
     * 存储当前类目所属的一级类目，不存在为空
     */
    @ApiField(description = "一级类目ID")
    @TableField("first_level_id")
    private Long firstLevelId;

    @ApiField(description = "父节点")
    @TableField("parent_id")
    private Long parentId;

    /**
     * 分类图片
     */
    @ApiField(description = "图片URL")
    @TableField("pic_url")
    private String picUrl;

    @ApiField(description = "等级", enums = CategoryLevelType.class)
    private Integer level;

}
