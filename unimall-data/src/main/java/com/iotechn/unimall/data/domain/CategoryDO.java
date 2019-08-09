package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by rize on 2019/7/2.
 */
@Data
@TableName("unimall_category")
public class CategoryDO extends SuperDO {

    private String title;

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

    private Integer level;

}
