package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/7/2.
 */
@Data
@TableName("unimall_category")
public class CategoryDO extends SuperDO {

    private String title;

    /**
     * 存储当前类目所属的一级类目，不存在为空
     */
    @TableField("first_level_id")
    private Long firstLevelId;

    /**
     * 存储当前类目所属的二级类目，不存在为空
     */
    @TableField("second_level_id")
    private Long secondLevelId;

    @TableField("parent_id")
    private Long parentId;

    /**
     * 图标
     */
    @TableField("icon_url")
    private String iconUrl;

    /**
     * 分类图片
     */
    @TableField("pic_url")
    private String picUrl;

    /**
     * 0,1,2三个等级
     */
    private Integer level;

}
