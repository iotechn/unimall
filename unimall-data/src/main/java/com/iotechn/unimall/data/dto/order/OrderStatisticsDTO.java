package com.iotechn.unimall.data.dto.order;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description: 订单统计传输模型
 * User: rize
 * Date: 2020/2/15
 * Time: 17:59
 */
@Data
@ApiEntity(description = "订单统计传输模型")
public class OrderStatisticsDTO {

    @ApiField(description = "商品ID")
    private Long spuId;

    @ApiField(description = "商品标题")
    private String spuTitle;

    @ApiField(description = "商品规格ID")
    private Long skuId;

    @ApiField(description = "商品规格标题")
    private String skuTitle;

    @ApiField(description = "商品数量")
    private Integer num;

}
