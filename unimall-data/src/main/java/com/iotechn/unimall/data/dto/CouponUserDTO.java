package com.iotechn.unimall.data.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/5.
 */
@Data
public class CouponUserDTO extends SuperDTO {

    private String title;

    private String categoryTitle;

    private Long categoryId;

    private Integer min;

    /**
     * 优惠券价格
     */
    private Integer discount;

    private Long userId;

    private Long couponId;

    private Long orderId;

    private Date gmtUsed;

    private Date gmtStart;

    private Date gmtEnd;

}
