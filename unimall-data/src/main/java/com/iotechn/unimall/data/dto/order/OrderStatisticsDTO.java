package com.iotechn.unimall.data.dto.order;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description: 订单统计传输模型
 * User: rize
 * Date: 2020/2/15
 * Time: 17:59
 */
@Data
public class OrderStatisticsDTO {

    private Long spuId;

    private String spuTitle;

    private Long skuId;

    private String skuTitle;

    private Integer num;

}
