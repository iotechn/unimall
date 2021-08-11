package com.iotechn.unimall.data.dto.order;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

/**
 * Created by rize on 2019/7/6.
 */
@Data
@ApiEntity(description = "订单请求商品传输实体")
public class OrderRequestSkuDTO {

    @ApiField(description = "商品规格ID")
    private Long skuId;

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "商品数量")
    private Integer num;

    /** 以下信息仅在预览时使用，是前端传过来的，是不可靠的，不可在结算中使用 **/
    @ApiField(description = "商品价格")
    private Integer price;

    @ApiField(description = "Vip价格")
    private Integer vipPrice;

    @ApiField(description = "商品重量（G）")
    private Integer weight;

    @ApiField(description = "运费模板ID")
    private Long freightTemplateId;

}
