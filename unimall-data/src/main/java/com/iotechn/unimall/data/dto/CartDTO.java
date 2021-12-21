package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/3.
 */
@Data
@ApiEntity(description = "购物车传输实体")
public class CartDTO extends SuperDTO {

    @ApiField(description = "商品规格ID")
    private Long skuId;

    @ApiField(description = "数量")
    private Integer num;

    @ApiField(description = "商品标题")
    private String title;

    @ApiField(description = "商品原价（仅显示作用）")
    private Integer originalPrice;

    @ApiField(description = "价格")
    private Integer price;

    @ApiField(description = "vip价格")
    private Integer vipPrice;

    @ApiField(description = "商品规格标题")
    private String skuTitle;

    @ApiField(description = "商品图片")
    private String spuImg;

    @ApiField(description = "商品规格图片")
    private String skuImg;

    @ApiField(description = "剩余库存")
    private Integer stock;

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "所属类目ID")
    private Long categoryId;

    @ApiField(description = "商品ID列表")
    private List<Long> categoryIdList;

    @ApiField(description = "商品重量G")
    private Integer weight;

    @ApiField(description = "运费模板ID")
    private Long freightTemplateId;

}
