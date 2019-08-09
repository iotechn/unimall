package com.iotechn.unimall.data.dto.goods;

import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/11.
 */
@Data
public class SpuTreeNodeDTO {

    private String value;

    private String label;

    private Long id;

    private List<SpuTreeNodeDTO> children;

}
