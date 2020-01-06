package com.iotechn.unimall.data.dto.order;

import com.iotechn.unimall.data.dto.UserCouponDTO;
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

    private UserCouponDTO coupon;

    private Long addressId;

    private Long groupShopId;

    private String mono;

    /**
     * 购物车 ？ 直接点击购买商品
     */
    private String takeWay;

    private Integer freightPrice;

}
