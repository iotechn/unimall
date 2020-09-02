package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/5.
 */
@Data
@TableName("unimall_order")
public class OrderDO extends SuperDO {

    /**
     * 用户下单渠道
     */
    private String channel;

    @TableField("parent_order_no")
    private String parentOrderNo;

    @TableField("order_no")
    private String orderNo;

    /**
     * 若是子单支付的，则此字段为1，若是父单合单支付的，此字段为0。若未支付，此字段为NULL
     */
    @TableField("sub_pay")
    private Integer subPay;

    @TableField("user_id")
    private Long userId;

    private Integer status;

    @TableField("sku_original_total_price")
    private Integer skuOriginalTotalPrice;

    /**
     * 商品总价
     */
    @TableField("sku_total_price")
    private Integer skuTotalPrice;

    @TableField("freight_price")
    private Integer freightPrice;

    @TableField("coupon_price")
    private Integer couponPrice;

    @TableField("coupon_id")
    private Long couponId;

    @TableField("group_shop_id")
    private Long groupShopId;

    /**
     * 计算优惠后，实际需要支付的价格
     */
    @TableField("actual_price")
    private Integer actualPrice;

    /**
     * 支付金额是单次支付的金额，其为父单的金额，可能超过子单的价格
     */
    @TableField("pay_price")
    private Integer payPrice;

    /**
     * 支付流水号 (第三方)
     */
    @TableField("pay_id")
    private String payId;

    /**
     * 第三方支付渠道
     */
    @TableField("pay_channel")
    private String payChannel;

    @TableField("gmt_pay")
    private Date gmtPay;

    @TableField("ship_no")
    private String shipNo;

    /**
     * 承运商
     */
    @TableField("ship_code")
    private String shipCode;

    private String province;

    private String city;

    private String county;

    private String address;

    private String phone;

    private String consignee;

    private String mono;

    @TableField("admin_mono_level")
    private Integer adminMonoLevel;

    @TableField("admin_mono")
    private String adminMono;

    @TableField("refund_reason")
    private String refundReason;

    @TableField("gmt_ship")
    private Date gmtShip;

    /**
     * 确实收货时间
     */
    @TableField("gmt_confirm")
    private Date gmtConfirm;


}
