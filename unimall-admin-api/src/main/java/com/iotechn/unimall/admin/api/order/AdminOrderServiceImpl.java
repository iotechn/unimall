package com.iotechn.unimall.admin.api.order;

import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.domain.UserDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.dto.order.OrderStatisticsDTO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.UserLoginType;
import com.iotechn.unimall.data.mapper.OrderMapper;
import com.iotechn.unimall.data.mapper.OrderSkuMapper;
import com.iotechn.unimall.data.mapper.SkuMapper;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/10.
 */
@Service
public class AdminOrderServiceImpl implements AdminOrderService {

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderServiceImpl.class);

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private UserMapper userMapper;

    @Value("${com.iotechn.unimall.wx.mini.app-id}")
    private String wxMiNiAppid;

    @Value("${com.iotechn.unimall.wx.app.app-id}")
    private String wxAppAppid;

    @Override
    public Page<OrderDO> list(Integer pageNo, Integer pageSize, Integer status, String orderNo, Long adminId) throws ServiceException {
        Wrapper<OrderDO> wrapper = new EntityWrapper<OrderDO>();
        wrapper.orderBy("id", false);
        if (!StringUtils.isEmpty(orderNo)) {
            wrapper.eq("order_no", orderNo);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        List<OrderDO> orderDOS = orderMapper.selectPage(new RowBounds((pageNo - 1) * pageSize, pageSize), wrapper);
        Integer count = orderMapper.selectCount(wrapper);
        return new Page<OrderDO>(orderDOS, pageNo, pageSize, count);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(String orderNo, Integer type, Integer sum, Long adminId) throws ServiceException {
        if (lockComponent.tryLock(OrderBizService.ORDER_REFUND_LOCK + orderNo, 30)) {
            try {
                //1.校验订单状态是否处于退款中
                OrderDO orderDO = orderBizService.checkOrderExist(orderNo, null);
                if (orderDO.getStatus() != OrderStatusType.REFUNDING.getCode()) {
                    throw new AdminServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
                }
                //2.退款处理
                if (type == 0) {
                    //2.1 店主拒绝退款
                    OrderDO updateOrderDO = new OrderDO();
                    updateOrderDO.setStatus(OrderStatusType.WAIT_CONFIRM.getCode());
                    updateOrderDO.setGmtUpdate(new Date());
                    orderBizService.changeOrderStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    return "ok";
                } else if (type == 1) {
                    //2.2 店主同意退款
                    //2.2.1 先流转状态
                    OrderDO updateOrderDO = new OrderDO();
                    updateOrderDO.setStatus(OrderStatusType.REFUNDED.getCode());
                    updateOrderDO.setGmtUpdate(new Date());
                    //订单还库存
                    List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_id", orderDO.getId()));
                    orderSkuList.forEach(item -> {
                        skuMapper.returnSkuStock(item.getSkuId(), item.getNum());
                    });
                    orderBizService.changeOrderStatus(orderNo, OrderStatusType.REFUNDING.getCode(), updateOrderDO);
                    Long userId = orderDO.getUserId();
                    UserDO userDO = userMapper.selectById(userId);
                    Integer loginType = userDO.getLoginType();
                    //2.2.2 向微信支付平台发送退款请求
                    Integer refundPrice = null;
                    if (sum != null) {
                        if (sum.intValue() > orderDO.getPayPrice()) {
                            throw new AdminServiceException(ExceptionDefinition.ORDER_REFUND_SUM_MOST_LOWER_THAN_PAY_PRICE);
                        }
                        refundPrice = sum;
                    } else {
                        refundPrice = orderDO.getPayPrice() - orderDO.getFreightPrice();
                    }
                    WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
                    wxPayRefundRequest.setAppid(loginType == UserLoginType.MP_WEIXIN.getCode() ? wxMiNiAppid : wxAppAppid);
                    wxPayRefundRequest.setOutTradeNo(orderNo);
                    wxPayRefundRequest.setOutRefundNo("refund_" + orderNo);
                    wxPayRefundRequest.setTotalFee(orderDO.getPayPrice());
                    wxPayRefundRequest.setRefundFee(refundPrice);
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
                } else {
                    throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
                }
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[微信退款] 异常", e);
                throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(OrderBizService.ORDER_REFUND_LOCK + orderNo);
            }
        } else {
            throw new AdminServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String ship(String orderNo, String shipCode, String shipNo, Long adminId) throws ServiceException {
        orderBizService.checkOrderExist(orderNo, null);
        OrderDO updateOrderDO = new OrderDO();
        Date now = new Date();
        updateOrderDO.setGmtUpdate(now);
        updateOrderDO.setGmtShip(now);
        updateOrderDO.setStatus(OrderStatusType.WAIT_CONFIRM.getCode());
        if (!"NONE".equals(shipCode)) {
            updateOrderDO.setShipCode(shipCode);
            updateOrderDO.setShipNo(shipNo);
        }
        //流转订单状态
        orderBizService.changeOrderStatus(orderNo, OrderStatusType.WAIT_STOCK.getCode(), updateOrderDO);
        return "ok";
    }

    @Override
    public OrderDTO detail(Long orderId, Long adminId) throws ServiceException {
        return orderBizService.getOrderDetail(orderId, null);
    }

    @Override
    public List<OrderDTO> queryToExcel(Long gmtStart, Long gmtEnd, Integer status, Long adminId) throws ServiceException {
        EntityWrapper wrapper = new EntityWrapper();
        if(gmtStart != null && gmtEnd != null){
            if(gmtStart > gmtStart){
                throw new AdminServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
            }
            wrapper.between("gmt_create",new Date(gmtStart) ,new Date(gmtEnd));
        }
        if(status != null){
            wrapper.eq("status", status);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if(orderDOS == null || orderDOS.size() == 0){
            return null;
        }
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderDO temp: orderDOS ) {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(temp, orderDTO);
            orderDTOList.add(orderDTO);
        }
        for (OrderDTO orderDTO:orderDTOList) {
                List<OrderSkuDO> orderSkuDOList =  orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_no",orderDTO.getOrderNo() ));
                orderDTO.setSkuList(orderSkuDOList);
        }
        return orderDTOList;
    }

    @Override
    public String editAdminMono(Long orderId, Integer level, String mono, Long adminId) throws ServiceException {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(orderId);
        orderDO.setAdminMonoLevel(level);
        orderDO.setAdminMono(mono);
        orderDO.setGmtUpdate(new Date());
        if (orderMapper.updateById(orderDO) > 0) {
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public List<OrderStatisticsDTO> statistics(Long gmtStart, Long gmtEnd, Long adminId) throws ServiceException {
        if (gmtStart == null) {
            gmtStart = System.currentTimeMillis() - 1000l * 60 * 60 * 24;
        }
        if (gmtEnd == null) {
            gmtEnd = System.currentTimeMillis();
        }
        if(gmtStart > gmtStart){
            throw new AdminServiceException(ExceptionDefinition.ORDER_EXCEL_PARAM_ERROR);
        }
        Column[] idColumn = {
                Column.create().column("id")};
        List<OrderDO> orderDOS = orderMapper.selectList(
                new EntityWrapper<OrderDO>()
                        .between("gmt_create", new Date(gmtStart), new Date(gmtEnd))
                        .setSqlSelect(idColumn));
        List<Long> ids = orderDOS.stream().map(item -> item.getId()).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(ids)){
            return null;
        }

        List<OrderStatisticsDTO> statistics = orderSkuMapper.statistics(ids);
        return statistics;
    }

}
