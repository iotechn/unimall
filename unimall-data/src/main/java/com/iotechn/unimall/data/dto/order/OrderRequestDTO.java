package com.iotechn.unimall.data.dto.order;

import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class OrderRequestDTO {

    private List<OrderRequestSkuDTO> skuList;

    /**
     * 商品支付总价
     */
    private Integer totalPrice;

    private Integer totalOriginalPrice;

    private Long addressId;

    private Long groupShopId;

    /**
     * 用户优惠券id
     */
    private Long couponId;

    private String mono;

    /**
     * 购物车 ？ 直接点击购买商品 cart,buy,combine
     */
    private String takeWay;

    private Integer freightPrice;

    private Integer exceptPrice;

}
