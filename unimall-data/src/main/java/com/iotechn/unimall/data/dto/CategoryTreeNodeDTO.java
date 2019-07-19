package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.util.List;

/**
 * 为ElementUi适配的树结构
 * Created by rize on 2019/7/12.
 */
@Data
public class CategoryTreeNodeDTO {

    private String label;

    private Long value;

    private String fullName;

    private Long parent;

    private Integer level;

    private String iconUrl;

    private String picUrl;

    private List<CategoryTreeNodeDTO> children;

}
