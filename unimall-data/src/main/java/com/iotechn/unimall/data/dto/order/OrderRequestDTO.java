package com.iotechn.unimall.data.dto.order;

import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.dto.UserCouponDTO;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class OrderRequestDTO {

    private List<OrderRequestSkuDTO> skuList;

    private Integer totalPrice;

    private Integer totalOriginalPrice;

    private UserCouponDTO coupon;

}
