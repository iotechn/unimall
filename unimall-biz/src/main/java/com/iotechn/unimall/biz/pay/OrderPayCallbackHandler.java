package com.iotechn.unimall.biz.pay;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.AppServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.exception.PayServiceException;
import com.dobbinsoft.fw.pay.handler.MatrixPayCallbackHandler;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayNotifyResponse;
import com.dobbinsoft.fw.pay.model.notify.MatrixPayOrderNotifyResult;
import com.dobbinsoft.fw.support.mq.DelayedMessageQueue;
import com.iotechn.unimall.biz.client.erp.ErpClient;
import com.iotechn.unimall.biz.executor.GlobalExecutor;
import com.iotechn.unimall.biz.service.groupshop.GroupShopBizService;
import com.iotechn.unimall.biz.service.notify.AdminNotifyBizService;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.enums.DMQHandlerType;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.OrderMapper;
import com.iotechn.unimall.data.mapper.OrderSkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderPayCallbackHandler implements MatrixPayCallbackHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderPayCallbackHandler.class);

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ErpClient erpClient;

    @Autowired
    private ProductBizService productBizService;

    @Autowired
    private GroupShopBizService groupShopBizService;

    @Autowired
    private DelayedMessageQueue delayedMessageQueue;

    @Autowired
    private AdminNotifyBizService adminNotifyBizService;

    @Override
    public void beforeCheckSign(HttpServletRequest httpServletRequest) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object handle(MatrixPayOrderNotifyResult result, HttpServletRequest request) {
        try {
            logger.info("处理{}支付平台的订单支付", result.getPayChannel().getMsg());
            logger.info(JSONObject.toJSONString(result));
            /* 之前传过去的我们系统的订单ID */
            // 现在是不知道是父订单还是普通订单
            String orderAbstractNo = result.getOutTradeNo();
            boolean isParent = !orderAbstractNo.contains("S");
            String payId = result.getTransactionId();

            List<OrderDO> orderDOList;
            if (isParent) {
                orderDOList = orderMapper.selectList(
                        new QueryWrapper<OrderDO>()
                                .eq("parent_order_no", orderAbstractNo));
            } else {
                orderDOList = orderMapper.selectList(
                        new QueryWrapper<OrderDO>()
                                .eq("order_no", orderAbstractNo));
            }

            if (CollectionUtils.isEmpty(orderDOList)) {
                return MatrixPayNotifyResponse.fail("订单不存在 orderNo=" + orderAbstractNo);
            }

            int status = orderDOList.get(0).getStatus().intValue();
            int actualPrice = 0;

            for (OrderDO orderDO : orderDOList) {
                actualPrice += orderDO.getActualPrice();
                if (orderDO.getStatus().intValue() != status) {
                    return MatrixPayNotifyResponse.fail("订单子单状态不一致");
                }
            }

            if (status != OrderStatusType.UNPAY.getCode()) {
                return MatrixPayNotifyResponse.success("订单已经处理过了");
            }

            Integer totalFee = result.getTotalFee();

            // 检查支付订单金额
            if (!totalFee.equals(actualPrice)) {
                return MatrixPayNotifyResponse.fail(orderAbstractNo + " : 支付金额不符合 totalFee=" + totalFee);
            }

            /**************** 在此之前都没有 数据库修改 操作 所以前面是直接返回错误的 **********************/

            //1. 更新订单状态
            Date now = new Date();
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setPayId(payId);
            updateOrderDO.setPayChannel(result.getPayChannel().getCode());
            updateOrderDO.setPayPrice(result.getTotalFee());
            updateOrderDO.setGmtPay(now);
            updateOrderDO.setGmtUpdate(now);
            updateOrderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
            updateOrderDO.setAppId(result.getAppid());
            List<OrderSkuDO> orderSkuDOList;

            if (isParent) {
                // 父单支付
                updateOrderDO.setSubPay(0);
                List<String> orderNos = orderDOList.stream().map(item -> item.getOrderNo()).collect(Collectors.toList());
                orderSkuDOList = orderSkuMapper.selectList(
                        new QueryWrapper<OrderSkuDO>()
                                .in("order_no", orderNos));
                if (orderSkuDOList.stream().filter(item -> (item.getActivityType() != null && item.getActivityType() == SpuActivityType.GROUP_SHOP.getCode())).count() > 0) {
                    // 走团购商品逻辑 更新状态只能按单独子单更新
                    List<OrderDO> subOrderList = orderBizService.checkOrderExistByParentNo(orderAbstractNo, null);
                    // 将orderSkuList转换为以子单为Key的Map
                    Map<String, List<OrderSkuDO>> orderSkuMap = orderSkuDOList.stream().collect(Collectors.groupingBy(OrderSkuDO::getOrderNo));
                    // 各个认领自己的skuList
                    for (OrderDO subOrder : subOrderList) {
                        List<OrderSkuDO> subOrderSkuList = orderSkuMap.get(subOrder.getOrderNo());
                        List<OrderSkuDO> groupShopSkuList = subOrderSkuList.stream().filter(item -> (item.getActivityType() != null && item.getActivityType() == SpuActivityType.GROUP_SHOP.getCode())).collect(Collectors.toList());
                        if (groupShopSkuList.size() > 0) {
                            // 若存在团购商品
                            OrderDO groupShopUpdateDO = new OrderDO();
                            groupShopUpdateDO.setPayId(payId);
                            groupShopUpdateDO.setPayChannel(PayChannelType.WX.getCode());
                            groupShopUpdateDO.setPayPrice(result.getTotalFee());
                            groupShopUpdateDO.setGmtPay(now);
                            groupShopUpdateDO.setGmtUpdate(now);
                            groupShopUpdateDO.setStatus(OrderStatusType.GROUP_SHOP_WAIT.getCode());
                            groupShopUpdateDO.setSubPay(1);
                            // 增加buyer count
                            for (OrderSkuDO orderSkuDO : groupShopSkuList) {
                                groupShopBizService.incGroupShopNum(orderSkuDO.getActivityId(), orderSkuDO.getNum());
                            }
                            orderBizService.changeOrderSubStatus(subOrder.getOrderNo(), OrderStatusType.UNPAY.getCode(), groupShopUpdateDO);
                        } else {
                            erpClient.takeSalesHeader(subOrder.getOrderNo());
                            orderBizService.changeOrderSubStatus(subOrder.getOrderNo(), OrderStatusType.UNPAY.getCode(), updateOrderDO);
                        }
                    }
                } else {
                    // 因为现在只有团购需要分单，所以这种情况并不会发生
                    // 走普通商品 并且更新状态 可以打包更新
                    orderBizService.changeOrderParentStatus(orderAbstractNo, OrderStatusType.UNPAY.getCode(), updateOrderDO, orderDOList.size());
                }
            } else {
                // 子单支付
                updateOrderDO.setSubPay(1);
                orderSkuDOList = orderSkuMapper.selectList(
                        new QueryWrapper<OrderSkuDO>()
                                .eq("order_no", orderAbstractNo));
                List<OrderSkuDO> groupShopSkuList = orderSkuDOList.stream().filter(item -> (item.getActivityType() != null && item.getActivityType() == SpuActivityType.GROUP_SHOP.getCode())).collect(Collectors.toList());
                if (groupShopSkuList.size() > 0) {
                    // 若存在团购商品
                    OrderDO groupShopUpdateDO = new OrderDO();
                    groupShopUpdateDO.setPayId(payId);
                    groupShopUpdateDO.setPayChannel(PayChannelType.WX.getCode());
                    groupShopUpdateDO.setPayPrice(result.getTotalFee());
                    groupShopUpdateDO.setGmtPay(now);
                    groupShopUpdateDO.setGmtUpdate(now);
                    groupShopUpdateDO.setStatus(OrderStatusType.GROUP_SHOP_WAIT.getCode());
                    groupShopUpdateDO.setSubPay(1);
                    // 增加buyer count
                    for (OrderSkuDO orderSkuDO : groupShopSkuList) {
                        groupShopBizService.incGroupShopNum(orderSkuDO.getActivityId(), orderSkuDO.getNum());
                    }
                    orderBizService.changeOrderSubStatus(orderAbstractNo, OrderStatusType.UNPAY.getCode(), groupShopUpdateDO);
                } else {
                    erpClient.takeSalesHeader(orderDOList.get(0).getOrderNo());
                    orderBizService.changeOrderSubStatus(orderAbstractNo, OrderStatusType.UNPAY.getCode(), updateOrderDO);
                }
            }

            //2. 增加商品销量
            // 可能存在两个相同的Spu，不同的Sku的情况
            Map<Long, Integer> salesMap = orderSkuDOList.stream().collect(Collectors.toMap(OrderSkuDO::getSpuId, OrderSkuDO::getNum, (k1, k2) -> k1.intValue() + k2.intValue()));
            productBizService.incSpuSales(salesMap);


            //3. 通知管理员发货 & 删除延迟消息队列取消订单消息
            Map<String, List<OrderSkuDO>> orderSkuMap = orderSkuDOList.stream().collect(Collectors.groupingBy(OrderSkuDO::getOrderNo));
            Map<String, List<OrderDO>> orderMap = orderDOList.stream().collect(Collectors.groupingBy(OrderDO::getOrderNo));
            for (String subOrderNo : orderSkuMap.keySet()) {
                OrderDTO finalOrderDTO = new OrderDTO();
                OrderDO orderDO = orderMap.get(subOrderNo).get(0);
                BeanUtils.copyProperties(orderDO, finalOrderDTO);
                finalOrderDTO.setPayChannel(PayChannelType.WX.getCode());
                finalOrderDTO.setSkuList(orderSkuMap.get(subOrderNo));
                GlobalExecutor.execute(() -> {
                    adminNotifyBizService.newOrder(finalOrderDTO);
                });
                delayedMessageQueue.deleteTask(DMQHandlerType.ORDER_AUTO_CANCEL.getCode(), subOrderNo);
                logger.info("[订单微信支付成功] orderNo:" + subOrderNo);
            }
            return MatrixPayNotifyResponse.success("支付成功");
        } catch (ServiceException e) {
            throw new PayServiceException(e.getMessage());
        }
    }
}
