package com.iotechn.unimall.data.dto.order;

import lombok.Data;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class OrderRequestSkuDTO {

    private Long skuId;

    private Integer price;

    private Integer num;

}
