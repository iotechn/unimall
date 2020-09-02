package com.iotechn.unimall.data.dto.goods;

import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuSpecificationDO;
import com.iotechn.unimall.data.dto.SuperDTO;
import lombok.Data;

import java.util.List;

/**
 * Description: 用于和管理员后台相交互的
 * User: rize
 * Date: 2020/8/1
 * Time: 17:44
 */
@Data
public class AdminSpuDTO extends SuperDTO {

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

    private List<Long> categoryIds;

    private List<SpuAttributeDO> attributeList;

    /**
     * 商品规格枚举列表
     */
    private List<SpuSpecificationDO> specificationList;

    private String unit;

    private Long freightTemplateId;

    private Integer status;

}
