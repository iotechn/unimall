package com.iotechn.unimall.data.domain;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import lombok.Data;

@Data
@ApiEntity(description = "退款商品")
public class OrderRefundSkuDO extends SuperDO {

    @ApiField(description = "订单退款ID")
    private Long orderRefundId;

    @ApiField(description = "商品ID")
    private Long skuId;

    @ApiField(description = "数量")
    private Integer num;

    @ApiField(description = "退款单价")
    private Integer price;

    //  冗余的商品信息 防止商品删除 或 信息修改
    @ApiField(description = "商品标题")
    private String spuTitle;

    @ApiField(description = "SKU 标题， 即小规格名称")
    private String title;

    @ApiField(description = "商品条码")
    private String barCode;

    @ApiField(description = "主图 优先使用SKU图片")
    private String img;

    @ApiField(description = "商品单位")
    private String unit;

    @ApiField(description = "商品重量")
    private Integer weight;

}
