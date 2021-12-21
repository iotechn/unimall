package com.iotechn.unimall.data.dto.order;

import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import lombok.Data;

import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
@Data
@ApiEntity(description = "下单传输实体")
public class OrderRequestDTO {

    @ApiField(description = "商品列表")
    private List<OrderRequestSkuDTO> skuList;

    /**
     * 商品支付总价
     */
    @ApiField(description = "商品支付总价")
    private Integer totalPrice;

    @ApiField(description = "商品原始总价（仅显示作用）")
    private Integer totalOriginalPrice;

    @ApiField(description = "用户地址ID")
    private Long addressId;

    @ApiField(description = "商品团购ID")
    private Long groupShopId;

    /**
     * 用户优惠券id
     */
    @ApiField(description = "用户优惠券ID")
    private Long couponId;

    @ApiField(description = "用户订单备注")
    private String mono;

    /**
     * 购物车 ？ 直接点击购买商品 cart,buy
     */
    @ApiField(description = "购物车 ？ 直接点击购买商品 cart,buy")
    private String takeWay;

    @ApiField(description = "运费")
    private Integer freightPrice;

    @ApiField(description = "前端计算后期望的订单价格")
    private Integer exceptPrice;

}
