package com.iotechn.unimall.data.dto;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.model.KVModel;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/15.
 */
@Data
@ApiEntity(description = "后台聚合数据传输模型")
public class DashboardIntegralDTO {

    @ApiField(description = "待出库订单")
    private Integer waitStockCount;

    @ApiField(description = "商品数量")
    private Integer goodsCount;

    @ApiField(description = "最近订单数量")
    private List<Object[]> daysOrder;

    @ApiField(description = "也是统计")
    private List<Object[]> daysSum;

    @ApiField(description = "区域饼状图")
    private List<KVModel<String, Long>> area;

    @ApiField(description = "渠道饼状图")
    private List<KVModel<String, Long>> channel;

}
