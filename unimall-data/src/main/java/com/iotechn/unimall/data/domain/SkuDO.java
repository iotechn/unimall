package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

/**
 * Created by rize on 2019/7/2.
 */
@Data
@ApiEntity(description = "商品规格")
@TableName("unimall_sku")
public class SkuDO extends SuperDO {

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "条形码")
    private String barCode;

    /**
     * SKU显示文字
     */
    @ApiField(description = "规格名称")
    private String title;

    /**
     * TODO 注释格式
     */
    @ApiField(description = "每个规格的具体值 格式: ")
    private String specification;

    @ApiField(description = "规格图片")
    private String img;

    @ApiField(description = "原始价格(仅显示作用)")
    private Integer originalPrice;

    @ApiField(description = "价格")
    private Integer price;

    @ApiField(description = "VIP价格")
    private Integer vipPrice;

    @ApiField(description = "库存")
    private Integer stock;

    @ApiField(description = "重量（G）")
    private Integer weight;

}
