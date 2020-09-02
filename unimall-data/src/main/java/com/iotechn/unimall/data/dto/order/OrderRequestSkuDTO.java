package com.iotechn.unimall.data.dto.order;

import lombok.Data;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class OrderRequestSkuDTO {

    private Long skuId;

    private Long spuId;

    private Integer num;

    /** 以下信息仅在预览时使用，是前端传过来的，是不可靠的，不可在结算中使用 **/

    private Integer price;

    private Integer vipPrice;

    private Integer weight;

    private Long freightTemplateId;

}
