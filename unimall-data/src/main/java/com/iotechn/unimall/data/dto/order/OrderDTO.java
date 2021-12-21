package com.iotechn.unimall.data.dto.order;

import com.dobbinsoft.fw.core.annotation.doc.ApiField;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.SpuActivityType;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
@Data
public class OrderDTO extends SuperDTO {

    /**
     * 用户下单渠道
     */
    @ApiField(description = "下单渠道")
    private String channel;

    @ApiField(description = "订单父单串号")
    private String parentOrderNo;

    @ApiField(description = "订单串号")
    private String orderNo;

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

    @ApiField(description = "自动取消订单还有多少秒")
    private Integer cancelSec;

    @ApiField(description = "自动确认收货还有多少秒")
    private Integer confirmSec;

    @ApiField(description = "商品列表")
    private List<OrderSkuDO> skuList;

    @ApiField(description = "物流轨迹实体")
    private ShipTraceDTO shipTraceDTO;

    @ApiField(description = "活动类型", enums = SpuActivityType.class)
    private Integer activityType;

    @ApiField(description = "活动ID")
    private Long activityId;

}
