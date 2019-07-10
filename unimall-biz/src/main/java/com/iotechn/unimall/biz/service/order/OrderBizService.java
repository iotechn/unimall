package com.iotechn.unimall.biz.service.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.BizServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.mapper.OrderMapper;
import com.iotechn.unimall.data.mapper.OrderSkuMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by rize on 2019/7/10.
 */
@Service
public class OrderBizService {

    private static final String ORDER_STATUS_LOCK = "ORDER_STATUS_LOCK_";

    //订单退款乐观锁
    public static final String ORDER_REFUND_LOCK = "ORDER_REFUND_LOCK_";

    private static final Logger logger = LoggerFactory.getLogger(OrderBizService.class);

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    public boolean changeOrderStatus(String orderNo, int nowStatus, OrderDO orderDO) throws ServiceException {
        try {
            if (lockComponent.tryLock(ORDER_STATUS_LOCK + orderNo,30)) {
                if (orderMapper.update(orderDO,
                        new EntityWrapper<OrderDO>()
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
            lockComponent.release(ORDER_STATUS_LOCK + orderNo);
        }
    }

    public OrderDO checkOrderExist(String orderNo, Long userId) throws ServiceException {
        Wrapper<OrderDO> wrapper = new EntityWrapper<OrderDO>().eq("order_no", orderNo);
        if (userId != null) {
            wrapper.eq("user_id", userId);
        }
        List<OrderDO> orderDOS = orderMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(orderDOS)) {
            throw new AppServiceException(ExceptionDefinition.ORDER_NOT_EXIST);
        }
        return orderDOS.get(0);
    }

    public OrderDTO getOrderDetail(Long orderId, Long userId) throws ServiceException {
        Wrapper<OrderDO> wrapper = new EntityWrapper<OrderDO>()
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
        orderDTO.setSkuList(orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_id", orderId)));
        return orderDTO;
    }


}
