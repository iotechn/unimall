package com.iotechn.unimall.app.api.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.service.WxPayService;
import com.iotechn.unimall.app.api.category.CategoryService;
import com.iotechn.unimall.biz.service.freight.FreightBizService;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.util.GeneratorUtil;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.goods.SkuDTO;
import com.iotechn.unimall.data.dto.UserCouponDTO;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestSkuDTO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.UserLevelType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/4.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static final String TAKE_ORDER_LOCK = "TAKE_ORDER_";

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSkuMapper orderSkuMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private LockComponent lockComponent;

    @Autowired
    private OrderBizService orderBizService;

    @Autowired
    private FreightBizService freightBizService;

    @Value("${com.iotechn.unimall.machine-no}")
    private String MACHINE_NO;

    @Value("${com.iotechn.unimall.env}")
    private String ENV;

    @Override
    public String takeOrder(OrderRequestDTO orderRequest, String channel, Long userId) throws ServiceException {
        if (lockComponent.tryLock(TAKE_ORDER_LOCK + userId, 20)) {
            //加上乐观锁，防止用户重复提交订单
            try {
                //用户会员等级
                Integer userLevel = SessionUtil.getUser().getLevel();
                //参数强校验 START
                List<OrderRequestSkuDTO> skuList = orderRequest.getSkuList();
                if (CollectionUtils.isEmpty(skuList) || orderRequest.getTotalPrice() == null) {
                    throw new AppServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
                }
                if (orderRequest.getTotalPrice() <= 0) {
                    throw new AppServiceException(ExceptionDefinition.ORDER_PRICE_MUST_GT_ZERO);
                }
                //商品价格
                int skuPrice = 0;
                int skuOriginalPrice = 0;
                //稍后用于优惠券作用范围校验
                Map<Long, Integer> categoryPriceMap = new HashMap<>();
                //稍后用于插入OrderSku
                Map<Long, SkuDTO> skuIdDTOMap = new HashMap<>();
                for (OrderRequestSkuDTO orderRequestSkuDTO : skuList) {
                    SkuDTO skuDTO = skuMapper.getSkuDTOById(orderRequestSkuDTO.getSkuId());
                    skuIdDTOMap.put(skuDTO.getId(), skuDTO);
                    if (skuDTO == null) {
                        throw new AppServiceException(ExceptionDefinition.ORDER_SKU_NOT_EXIST);
                    }
                    if (skuDTO.getStock() < orderRequestSkuDTO.getNum()) {
                        //TODO 这里存在并发问题
                        throw new AppServiceException(ExceptionDefinition.ORDER_SKU_STOCK_NOT_ENOUGH);
                    }
                    int p;
                    if (userLevel == UserLevelType.VIP.getCode()) {
                        p = skuDTO.getVipPrice() * orderRequestSkuDTO.getNum();
                    } else {
                        p = skuDTO.getPrice() * orderRequestSkuDTO.getNum();
                    }
                    skuPrice += p;
                    skuOriginalPrice += skuDTO.getOriginalPrice();
                    List<Long> categoryFamily = categoryService.getCategoryFamily(skuDTO.getCategoryId());
                    for (Long cid : categoryFamily) {
                        Integer price = categoryPriceMap.get(cid);
                        if (price == null) {
                            price = p;
                        } else {
                            price += p;
                        }
                        categoryPriceMap.put(cid, price);
                    }
                }

                if (skuPrice != orderRequest.getTotalPrice()) {
                    throw new AppServiceException(ExceptionDefinition.ORDER_PRICE_CHECK_FAILED);
                }

                //优惠券折扣价格
                int couponPrice = 0;
                //优惠券校验
                UserCouponDTO userCouponFromFront = orderRequest.getCoupon();
                if (userCouponFromFront != null) {
                    if (userCouponFromFront.getId() == null || userCouponFromFront.getDiscount() == null) {
                        throw new AppServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
                    }

                    UserCouponDTO userCouponFromDB = userCouponMapper.getUserCouponById(userCouponFromFront.getId(), userId);

                    if (userCouponFromDB == null) {
                        throw new AppServiceException(ExceptionDefinition.ORDER_COUPON_NOT_EXIST);
                    }

                    if (!userCouponFromDB.getDiscount().equals(userCouponFromFront.getDiscount())) {
                        throw new AppServiceException(ExceptionDefinition.ORDER_COUPON_DISCOUNT_CHECK_FAILED);
                    }

                    //校验优惠券策略是否满足
                    Long categoryId = userCouponFromDB.getCategoryId();
                    if (categoryId != null) {
                        Integer p = categoryPriceMap.get(categoryId);
                        if (p < userCouponFromDB.getMin()) {
                            throw new AppServiceException(ExceptionDefinition.ORDER_COUPON_PRICE_NOT_ENOUGH);
                        }
                    } else {
                        if (skuPrice < userCouponFromDB.getMin()) {
                            throw new AppServiceException(ExceptionDefinition.ORDER_COUPON_PRICE_NOT_ENOUGH);
                        }
                    }
                    couponPrice = userCouponFromDB.getDiscount();
                }

                //TODO 运费
                Integer freightPrice = freightBizService.getFreightMoney(orderRequest);
                //参数强校验 END
                //???是否校验actualPrice??强迫校验？
                int actualPrice = skuPrice - couponPrice - freightPrice;
                Date now = new Date();
                OrderDO orderDO = new OrderDO();
                orderDO.setSkuTotalPrice(skuPrice);
                orderDO.setSkuOriginalTotalPrice(skuOriginalPrice);
                orderDO.setChannel(channel);
                orderDO.setActualPrice(actualPrice);
                if (couponPrice != 0) {
                    orderDO.setCouponId(orderRequest.getCoupon().getCouponId());
                    orderDO.setCouponPrice(couponPrice);
                }
                //TODO 运费
                orderDO.setFreightPrice(0);
                orderDO.setOrderNo(GeneratorUtil.genOrderId(MACHINE_NO, ENV));
                orderDO.setUserId(userId);
                orderDO.setStatus(OrderStatusType.UNPAY.getCode());
                orderDO.setGmtUpdate(now);
                orderDO.setGmtCreate(now);

                if (orderRequest.getAddressId() != null) {
                    AddressDO addressDO = addressMapper.selectById(orderRequest.getAddressId());
                    if (!userId.equals(addressDO.getUserId())) {
                        throw new AppServiceException(ExceptionDefinition.ORDER_ADDRESS_NOT_BELONGS_TO_YOU);
                    }
                    orderDO.setProvince(addressDO.getProvince());
                    orderDO.setCity(addressDO.getCity());
                    orderDO.setCounty(addressDO.getCounty());
                    orderDO.setAddress(addressDO.getAddress());
                }
                orderMapper.insert(orderDO);

                //扣除用户优惠券
                if (orderDO.getCouponId() != null) {
                    UserCouponDO updateUserCouponDO = new UserCouponDO();
                    updateUserCouponDO.setId(orderDO.getCouponId());
                    updateUserCouponDO.setGmtUsed(now);
                    updateUserCouponDO.setOrderId(orderDO.getId());
                    userCouponMapper.updateById(updateUserCouponDO);
                }

                //插入OrderSku
                skuList.forEach(item -> {
                    SkuDTO skuDTO = skuIdDTOMap.get(item.getSkuId());
                    OrderSkuDO orderSkuDO = new OrderSkuDO();
                    orderSkuDO.setBarCode(skuDTO.getBarCode());
                    orderSkuDO.setTitle(skuDTO.getTitle());
                    orderSkuDO.setSpuTitle(skuDTO.getSpuTitle());
                    orderSkuDO.setImg(skuDTO.getImg() == null ? skuDTO.getSpuImg() : skuDTO.getImg());
                    orderSkuDO.setNum(item.getNum());
                    orderSkuDO.setOriginalPrice(skuDTO.getOriginalPrice());
                    orderSkuDO.setPrice(skuDTO.getPrice());
                    if (userLevel == UserLevelType.VIP.getCode()) {
                        orderSkuDO.setPrice(skuDTO.getVipPrice());
                    } else {
                        orderSkuDO.setPrice(skuDTO.getPrice());
                    }
                    orderSkuDO.setSkuId(skuDTO.getId());
                    orderSkuDO.setSpuId(skuDTO.getSpuId());
                    orderSkuDO.setOrderNo(orderDO.getOrderNo());
                    orderSkuDO.setOrderId(orderDO.getId());
                    orderSkuDO.setGmtCreate(now);
                    orderSkuDO.setGmtUpdate(now);
                    orderSkuMapper.insert(orderSkuDO);

                    //扣除库存
                    skuMapper.decSkuStock(item.getSkuId(), item.getNum());
                });

                if (!StringUtils.isEmpty(orderRequest.getTakeWay())) {
                    String takeWay = orderRequest.getTakeWay();
                    if ("cart".equals(takeWay)) {
                        //扣除购物车
                        List<Long> skuIds = skuList.stream().map(item -> item.getSkuId()).collect(Collectors.toList());
                        cartMapper.delete(new EntityWrapper<CartDO>().in("sku_id", skuIds).eq("user_id", userId));
                    }
                }

                return orderDO.getOrderNo();

            } catch (Exception e) {
                logger.error("[提交订单] 异常", e);
                throw new AppServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(TAKE_ORDER_LOCK + userId);
            }
        }
        throw new AppServiceException(ExceptionDefinition.ORDER_SYSTEM_BUSY);
    }

    @Override
    public Page<OrderDTO> getOrderPage(Integer pageNo, Integer pageSize, Integer status, Long userId) throws ServiceException {
        List<OrderDTO> orderDTOList = orderMapper.selectOrderPage(status, (pageNo - 1) * pageSize, pageSize, userId);
        Long count = orderMapper.countOrder(status, (pageNo - 1) * pageSize, pageSize, userId);
        //封装SKU
        orderDTOList.forEach(item -> {
            item.setSkuList(orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_id", item.getId())));
        });
        return new Page<>(orderDTOList, pageNo, pageSize, count);
    }

    @Override
    public OrderDTO getOrderDetail(Long orderId, Long userId) throws ServiceException {
        return orderBizService.getOrderDetail(orderId, userId);
    }


    public Object wxPrepay(String orderNo, String ip, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        // 检测订单状态
        Integer status = orderDO.getStatus();
        if (status != OrderStatusType.UNPAY.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_PAY);
        }

        String openid = SessionUtil.getUser().getMiniOpenId();
        WxPayMpOrderResult result = null;
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setOutTradeNo(orderNo);
            orderRequest.setOpenid(openid);
            orderRequest.setBody("订单：" + orderNo);
            orderRequest.setTotalFee(orderDO.getActualPrice());
            orderRequest.setSpbillCreateIp(ip);
            result = wxPayService.createOrder(orderRequest);
            //缓存prepayID用于后续模版通知
            String prepayId = result.getPackageValue();
            prepayId = prepayId.replace("prepay_id=", "");
//TODO 缓存支付Id            userBizService.setVaildFormIdFromSession(prepayId);
        } catch (Exception e) {
            logger.error("[预付款异常]", e);
            throw new AppServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (OrderStatusType.refundable(orderDO.getStatus())) {
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setStatus(OrderStatusType.REFUNDING.getCode());
            orderBizService.changeOrderStatus(orderNo, orderDO.getStatus() , updateOrderDO);
            return "ok";
        }
        throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_REFUND);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String cancel(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (orderDO.getStatus() != OrderStatusType.UNPAY.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CANCEL);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setStatus(OrderStatusType.CANCELED.getCode());
        updateOrderDO.setGmtUpdate(new Date());
        orderBizService.changeOrderStatus(orderNo, OrderStatusType.UNPAY.getCode(), updateOrderDO);
        return "ok";
    }

    @Override
    public String confirm(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (orderDO.getStatus() != OrderStatusType.WAIT_CONFIRM.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_CONFIRM);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setStatus(OrderStatusType.WAIT_APPRAISE.getCode());
        updateOrderDO.setGmtUpdate(new Date());
        orderBizService.changeOrderStatus(orderNo, OrderStatusType.WAIT_CONFIRM.getCode(), updateOrderDO);
        return "ok";
    }

    @Override
    public ShipTraceDTO queryShip(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (orderDO.getStatus() < OrderStatusType.WAIT_CONFIRM.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_HAS_NOT_SHIP);
        }
        if (!StringUtils.isEmpty(orderDO.getShipCode()) && !StringUtils.isEmpty(orderDO.getShipNo())) {
            throw new AppServiceException(ExceptionDefinition.ORDER_DID_NOT_SET_SHIP);
        }
        ShipTraceDTO shipTraceList = freightBizService.getShipTraceList(orderDO.getShipNo(), orderDO.getShipCode());
        return shipTraceList;
    }


}
