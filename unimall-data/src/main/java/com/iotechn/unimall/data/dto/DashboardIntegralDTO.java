package com.iotechn.unimall.data.dto;

import com.iotechn.unimall.data.model.KVModel;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/15.
 */
@Data
public class DashboardIntegralDTO {

    private Integer waitStockCount;

    private Integer goodsCount;

    private List<Object[]> daysOrder;

    private List<Object[]> daysSum;

    private List<KVModel<String, Long>> area;

    private List<KVModel<String, Long>> channel;

}
