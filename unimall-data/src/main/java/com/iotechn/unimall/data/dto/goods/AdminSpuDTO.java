package com.iotechn.unimall.data.dto.goods;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuSpecificationDO;
import com.iotechn.unimall.data.enums.StatusType;
import lombok.Data;

import java.util.List;

/**
 * Description: 用于和管理员后台相交互的
 * User: rize
 * Date: 2020/8/1
 * Time: 17:44
 */
@Data
@ApiEntity(description = "管理员添加商品传输实体")
public class AdminSpuDTO extends SuperDTO {

    @ApiField(description = "商品规格列表")
    private List<SkuDO> skuList;

    @ApiField(description = "商品原价（仅显示作用）")
    private Integer originalPrice;

    @ApiField(description = "价格（仅显示作用）")
    private Integer price;

    @ApiField(description = "VIP价格（仅显示作用）")
    private Integer vipPrice;

    @ApiField(description = "库存")
    private Integer stock;

    @ApiField(description = "销量")
    private Integer sales;

    @ApiField(description = "标题")
    private String title;

    /**
     * 主图
     */
    @ApiField(description = "主图")
    private String img;

    /**
     * 后面的图，仅在详情接口才出现
     */
    @ApiField(description = "画廊")
    private List<String> imgList;

    @ApiField(description = "富文本详情")
    private String detail;

    @ApiField(description = "简介")
    private String description;

    @ApiField(description = "类目ID")
    private Long categoryId;

    @ApiField(description = "类目全路径（包含1、2、3级）用于回显")
    private List<Long> categoryIds;

    @ApiField(description = "属性列表")
    private List<SpuAttributeDO> attributeList;

    /**
     * 商品规格枚举列表
     */
    @ApiField(description = "规格列表")
    private List<SpuSpecificationDO> specificationList;

    @ApiField(description = "计量单位")
    private String unit;

    @ApiField(description = "运费模板ID")
    private Long freightTemplateId;

    @ApiField(description = "状态", enums = StatusType.class)
    private Integer status;

}
