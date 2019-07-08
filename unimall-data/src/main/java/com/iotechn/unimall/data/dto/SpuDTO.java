package com.iotechn.unimall.data.dto;

import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@Data
public class SpuDTO extends SuperDTO {

    private List<SkuDO> skuList;

    private Integer originalPrice;

    private Integer price;

    private Integer vipPrice;

    private Integer stock;

    private Integer sales;

    private String title;

    /**
     * 主图
     */
    private String img;

    /**
     * 后面的图，仅在详情接口才出现
     */
    private List<String> imgList;

    private String detail;

    private String description;

    private Long categoryId;

    private List<CategoryDTO> categoryList;

    private List<SpuAttributeDO> attributeList;

    private String unit;

    private FreightTemplateDTO freightTemplate;

    private Boolean collect;

    private Integer status;

}
