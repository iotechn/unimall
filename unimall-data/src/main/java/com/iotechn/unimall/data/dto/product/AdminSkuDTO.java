package com.iotechn.unimall.data.dto.product;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.domain.LocationSkuDO;
import lombok.Data;

import java.util.List;

@Data
public class AdminSkuDTO extends SuperDTO {

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "条形码")
    private String barCode;

    /**
     * SKU显示文字
     */
    @ApiField(description = "规格名称")
    private String title;

    @ApiField(description = "每个规格的具体值 格式: ")
    private String specification;

    @ApiField(description = "规格图片")
    private String img;

    @ApiField(description = "原始价格(仅显示作用)")
    private Integer originalPrice;

    @NotNull(message = "商品价格不能为空")
    @ApiField(description = "价格")
    private Integer price;

    @NotNull(message = "VIP价格不能为空")
    @ApiField(description = "VIP价格")
    private Integer vipPrice;

    @NotNull(message = "商品重量不能为空")
    @ApiField(description = "重量（G）")
    private Integer weight;

    @ApiField(description = "商品规格库存信息")
    private List<LocationSkuDTO> locationSkuList;
    
}
