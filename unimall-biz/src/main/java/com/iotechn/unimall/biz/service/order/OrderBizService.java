package com.iotechn.unimall.biz.service.order;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.iotechn.unimall.biz.mq.DelayedMessageQueue;
import com.iotechn.unimall.core.exception.*;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.constant.LockConst;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.enums.DMQHandlerType;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.PayChannelType;
import com.iotechn.unimall.data.enums.UserLoginType;
import com.iotechn.unimall.data.mapper.OrderMapper;
import com.iotechn.unimall.data.mapper.OrderSkuMapper;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.iotechn.unimall.data.properties.UnimallWxAppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by rize on 2019/7/10.
 */
@Service
public class OrderBizService {

    private static final Logger logger = LoggerFactory.getLogger(OrderBizService.class);

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private DelayedMessageQueue delayedMessageQueue;

    @Autowired
    private UnimallWxAppProperties unimallWxProperties;


    public List<OrderDO> checkOrderExistByParentNo(String parentOrderNo, Long userId) throws ServiceException {
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<OrderDO>().eq("parent_order_no", parentOrderNo);
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(orderDOS)) {
            throw new AppServiceException(ExceptionDefinition.ORDER_NOT_EXIST);
        }
        return orderDOS;
    }

    public List<OrderDO> checkOrderExistByNo(String orderNo, Long userId) throws ServiceException {
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<OrderDO>().eq("order_no", orderNo);
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(orderDOS)) {
            throw new AppServiceException(ExceptionDefinition.ORDER_NOT_EXIST);
        }
        return orderDOS;
    }

    public boolean changeOrderSubStatus(String orderNo, int nowStatus, OrderDO orderDO) throws ServiceException {
        String parentOrderNo = orderNo.substring(0, orderNo.indexOf('S') + 1);
        try {
            // 防止传入值为空,导致其余订单被改变
            if(orderNo == null || orderDO == null){
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            }
            // 同时拿到父单锁和子单锁，才能对子单进行操作
            if (lockComponent.tryLock(LockConst.ORDER_SUB_STATUS_LOCK + orderNo, 30)
                    && lockComponent.tryLock(LockConst.ORDER_PARENT_STATUS_LOCK + parentOrderNo, 30)) {
                if (orderMapper.update(orderDO,
                        new QueryWrapper<OrderDO>()
                                .eq("order_no", orderNo)
                                .eq("status", nowStatus)) > 0) {
                    return true;
                }
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            } else {
                throw new BizServiceException(ExceptionDefinition.ORDER_SYSTEM_BUSY);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("[订单状态扭转] 异常", e);
            throw new BizServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        } finally {
            lockComponent.release(LockConst.ORDER_SUB_STATUS_LOCK + orderNo);
            lockComponent.release(LockConst.ORDER_PARENT_STATUS_LOCK + parentOrderNo);
        }
    }

    public boolean changeOrderParentStatus(String parentOrderNo, int nowStatus, OrderDO orderDO, int length) throws ServiceException {
        try {
            // 防止传入值为空,导致其余订单被改变
            if (parentOrderNo == null || orderDO == null) {
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            }
            if (lockComponent.tryLock(LockConst.ORDER_PARENT_STATUS_LOCK + parentOrderNo, 30)) {
                int updateRes = orderMapper.update(orderDO,
                        new QueryWrapper<OrderDO>()
                                .eq("parent_order_no", parentOrderNo)
                                .eq("status", nowStatus));
                if (updateRes == length) {
                    return true;
                }
                throw new BizServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
            } else {
                throw new BizServiceException(ExceptionDefinition.ORDER_SYSTEM_BUSY);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("[订单状态扭转] 异常", parentOrderNo, e);
            throw new BizServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        } finally {
            lockComponent.release(LockConst.ORDER_PARENT_STATUS_LOCK + parentOrderNo);
        }
    }

    public OrderDTO getOrderDetail(Long orderId, Long userId) throws ServiceException {
        QueryWrapper<OrderDO> wrapper = new QueryWrapper<OrderDO>()
                .eq("id", orderId);
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(orderDOS)) {
            throw new AppServiceException(ExceptionDefinition.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderDOS.get(0), orderDTO);
        orderDTO.setSkuList(orderSkuMapper.selectList(new QueryWrapper<OrderSkuDO>().eq("order_id", orderId)));
        // 封装两个 自动取消 & 自动确认 剩余时间
        if (orderDTO.getStatus().intValue() == OrderStatusType.UNPAY.getCode()) {
            Long taskTime = delayedMessageQueue.getTaskTime(DMQHandlerType.ORDER_AUTO_CANCEL.getCode(), orderDTO.getOrderNo());
            if (taskTime != null && taskTime > 0) {
                orderDTO.setCancelSec(taskTime.intValue());
            }
        }
        if (orderDTO.getStatus().intValue() == OrderStatusType.WAIT_CONFIRM.getCode()) {
            Long taskTime = delayedMessageQueue.getTaskTime(DMQHandlerType.ORDER_AUTO_CONFIRM.getCode(), orderDTO.getOrderNo());
            if (taskTime != null && taskTime > 0) {
                orderDTO.setConfirmSec(taskTime.intValue());
            }
        }
        return orderDTO;
    }

//    @Transactional(rollbackFor = Exception.class) 外面加了事务
    public String groupShopStatusRefund(String orderNo) throws ServiceException {
        if (lockComponent.tryLock(LockConst.ORDER_REFUND_LOCK + orderNo, 30)) {
            try {
                //1.校验订单状态是否处于团购状态中
                OrderDO orderDO = checkOrderExistByNo(orderNo, null).get(0);
                if (orderDO.getStatus() != OrderStatusType.GROUP_SHOP_WAIT.getCode()) {
                    throw new AdminServiceException(ExceptionDefinition.ORDER_IS_NOT_GROUP_SHOP_STATUS);
                }
                //2.退款处理
                //2.1.1 先流转状态
                OrderDO updateOrderDO = new OrderDO();
                updateOrderDO.setStatus(OrderStatusType.REFUNDED.getCode());
                updateOrderDO.setGmtUpdate(new Date());
                changeOrderSubStatus(orderNo, OrderStatusType.GROUP_SHOP_WAIT.getCode(), updateOrderDO);
                Long userId = orderDO.getUserId();
                UserDO userDO = userMapper.selectById(userId);
                Integer loginType = userDO.getLoginType();
                // 根据不同的的支付方式，进行退款
                if (PayChannelType.WEPAY.getCode().equals(orderDO.getPayChannel())) {
                    //2.1.2 向微信支付平台发送退款请求
                    WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                    wxPayRefundRequest.setAppid(loginType == UserLoginType.MP_WEIXIN.getCode() ? unimallWxProperties.getMiniAppId() : unimallWxProperties.getAppId());
                    wxPayRefundRequest.setOutTradeNo(orderNo);
                    wxPayRefundRequest.setOutRefundNo("refund_" + orderNo);
                    wxPayRefundRequest.setRefundDesc("团购失败退款");
                    wxPayRefundRequest.setTotalFee(orderDO.getPayPrice() - orderDO.getFreightPrice());
                    wxPayRefundRequest.setRefundFee(orderDO.getPayPrice() - orderDO.getFreightPrice());
                    WxPayRefundResult wxPayRefundResult = wxPayService.refund(wxPayRefundRequest);
                    if (!wxPayRefundResult.getReturnCode().equals("SUCCESS")) {
                        logger.warn("[微信退款] 失败 : " + wxPayRefundResult.getReturnMsg());
                        throw new AdminServiceException(wxPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    if (!wxPayRefundResult.getResultCode().equals("SUCCESS")) {
                        logger.warn("[微信退款] 失败 : " + wxPayRefundResult.getReturnMsg());
                        throw new AdminServiceException(wxPayRefundResult.getReturnMsg(),
                                ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
                    }
                    return "ok";
                } else if (PayChannelType.OFFLINE.getCode().equals(orderDO.getChannel())) {
                    // 不需要退款
                    return "ok";
                } else {
                    throw new AppServiceException(ExceptionDefinition.ORDER_PAY_CHANNEL_NOT_SUPPORT_REFUND);
                }

            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[微信退款] 异常", e);
                throw new AppServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(LockConst.ORDER_REFUND_LOCK + orderNo);
            }
        } else {
            throw new AppServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }
    }

}
