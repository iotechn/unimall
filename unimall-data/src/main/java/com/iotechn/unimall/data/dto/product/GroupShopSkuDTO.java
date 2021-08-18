package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

/**
@PackageName:com.iotechn.unimall.data.dto.goods
@ClassName: GroupShopSkuDTO
@Description:
@author kbq
@date 19-11-13下午3:16
*/
@Data
@ApiEntity(description = "团购商品传输实体")
public class GroupShopSkuDTO extends SpuDTO {

    @ApiField(description = "团购商品价格")
    private Integer skuGroupShopPrice;

    @ApiField(description = "团购ID")
    private Long groupShopId;

    @ApiField(description = "商品规格ID")
    private Long skuId;

    /**
     * sku属性
     */
    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "商品条码")
    private String barCode;

    @ApiField(description = "商品标题")
    private String title;

    @ApiField(description = "SKU 图片")
    private String img;

    @ApiField(description = "商品原价（仅显示）")
    private Integer originalPrice;

    @ApiField(description = "VIP 价格")
    private Integer vipPrice;

    @ApiField(description = "商品库存")
    private Integer stock;

    @ApiField(description = "冻结商品库存")
    private Integer freezeStock;

}
