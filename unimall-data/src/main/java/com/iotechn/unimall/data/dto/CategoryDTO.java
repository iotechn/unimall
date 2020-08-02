package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@Data
public class CategoryDTO extends SuperDTO {

    private Long firstLevelId;

    private Long secondLevelId;

    private Long parentId;

    private String title;

    /**
     * 全类目名称：一级类目/二级类目/三级类目
     * 主要用于管理员查看
     */
    private String fullName;

    private Integer level;

    private String iconUrl;

    private String picUrl;

    private List<CategoryDTO> childrenList;

}
