package com.iotechn.unimall.data.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.dobbinsoft.fw.core.annotation.doc.ApiEntity;
import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.support.domain.SuperDO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import lombok.Data;

import java.util.Date;

/**
 * Created by rize on 2019/7/5.
 */
@Data
@ApiEntity(description = "订单领域模型")
@TableName("unimall_order")
public class OrderDO extends SuperDO {

    /**
     * 用户下单渠道
     */
    @ApiField(description = "下单渠道")
    private String channel;

    @ApiField(description = "订单父单串号")
    private String parentOrderNo;

    @ApiField(description = "订单串号")
    private String orderNo;

    /**
     * 若是子单支付的，则此字段为1，若是父单合单支付的，此字段为0。若未支付，此字段为NULL
     */
    @ApiField(description = "若是子单支付的，则此字段为1，若是父单合单支付的，此字段为0。若未支付，此字段为NULL")
    private Integer subPay;

    @ApiField(description = "所属用户ID")
    private Long userId;

    @ApiField(description = "状态", enums = OrderStatusType.class)
    private Integer status;

    @ApiField(description = "商品原始价格总价(仅显示作用)")
    private Integer skuOriginalTotalPrice;

    /**
     * 商品总价
     */
    @ApiField(description = "商品总价")
    private Integer skuTotalPrice;

    @ApiField(description = "商品配送费")
    private Integer freightPrice;

    @ApiField(description = "使用优惠券抵扣的价格")
    private Integer couponPrice;

    @ApiField(description = "优惠券ID")
    private Long couponId;

    @ApiField(description = "若是团购商品 参加团购的ID")
    private Long groupShopId;

    /**
     * 计算优惠后，实际需要支付的价格
     */
    @ApiField(description = "计算优惠后，实际需要支付的价格")
    private Integer actualPrice;

    /**
     * 支付金额是单次支付的金额，其为父单的金额，可能超过子单的价格
     */
    @ApiField(description = "支付金额是单次支付的金额，其为父单的金额，可能超过子单的价格")
    private Integer payPrice;

    /**
     * 支付流水号 (第三方)
     */
    @ApiField(description = "支付流水号 (第三方)")
    private String payId;

    /**
     * 第三方支付渠道
     */
    @ApiField(description = "第三方支付渠道")
    private String payChannel;

    @ApiField(description = "选择支付的AppId")
    private String appId;

    @ApiField(description = "支付时间")
    private Date gmtPay;

    @ApiField(description = "运单号")
    private String shipNo;

    @ApiField(description = "承运商")
    private String shipCode;

    @ApiField(description = "省份")
    private String province;

    @ApiField(description = "城市")
    private String city;

    @ApiField(description = "区/县")
    private String county;

    @ApiField(description = "详细地址")
    private String address;

    @ApiField(description = "签收人联系电话")
    private String phone;

    @ApiField(description = "签收人姓名")
    private String consignee;

    @ApiField(description = "用户订单备注")
    private String mono;

    @ApiField(description = "客服订单备注等级")
    private Integer adminMonoLevel;

    @ApiField(description = "客服订单备注")
    private String adminMono;

    @ApiField(description = "用户申请退款原因")
    private String refundReason;

    @ApiField(description = "发货时间")
    private Date gmtShip;

    @ApiField(description = "确实收货时间")
    private Date gmtConfirm;

}
