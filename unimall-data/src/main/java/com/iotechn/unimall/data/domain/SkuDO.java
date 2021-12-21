package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
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

    @NotNull(message = "条形码不能为空")
    @ApiField(description = "条形码")
    private String barCode;

    /**
     * SKU显示文字
     */
    @ApiField(description = "规格名称")
    @NotNull(message = "SKU显示名不能为空")
    private String title;

    @ApiField(description = "每个规格的具体值 格式: 尺码_S,颜色_经典款短袖黑色A")
    private String specification;

    @ApiField(description = "规格图片")
    private String img;

    @ApiField(description = "原始价格(仅显示作用)")
    @NotNull(message = "原始价格不能为空")
    private Integer originalPrice;

    @ApiField(description = "价格")
    @NotNull(message = "价格不能为空")
    private Integer price;

    @ApiField(description = "VIP价格")
    @NotNull(message = "VIP价格不能为空")
    private Integer vipPrice;

    @ApiField(description = "库存")
    @NotNull(message = "库存不能为空")
    private Integer stock;

    @ApiField(description = "重量（G）")
    @NotNull(message = "重量不能为空")
    private Integer weight;

}
