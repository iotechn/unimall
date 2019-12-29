package com.iotechn.unimall.app.api.order;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.binarywang.wxpay.bean.order.WxPayAppOrderResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.iotechn.unimall.app.api.category.CategoryService;
import com.iotechn.unimall.app.executor.GlobalExecutor;
import com.iotechn.unimall.biz.service.freight.FreightBizService;
import com.iotechn.unimall.biz.service.groupshop.GroupShopBizService;
import com.iotechn.unimall.biz.service.notify.AdminNotifyBizService;
import com.iotechn.unimall.biz.service.order.OrderBizService;
import com.iotechn.unimall.biz.service.user.UserBizService;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ThirdPartServiceException;
import com.iotechn.unimall.core.util.GeneratorUtil;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.goods.GroupShopDTO;
import com.iotechn.unimall.data.dto.goods.SkuDTO;
import com.iotechn.unimall.data.dto.UserCouponDTO;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestSkuDTO;
import com.iotechn.unimall.data.enums.*;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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

    @Autowired
    private UserBizService userBizService;

    @Autowired
    private GroupShopBizService groupShopBizService;

    @Autowired
    private AdminNotifyBizService adminNotifyBizService;

    @Value("${com.iotechn.unimall.machine-no}")
    private String MACHINE_NO;

    @Value("${com.iotechn.unimall.env}")
    private String ENV;

    @Value("${com.iotechn.unimall.wx.mini.app-id}")
    private String wxMiNiAppid;

    @Value("${com.iotechn.unimall.wx.app.app-id}")
    private String wxAppAppid;

    @Value("${com.iotechn.unimall.wx.h5.app-id}")
    private String wxH5Appid;


    @Override
    @Transactional(rollbackFor = Exception.class)
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
                Long groupShopId = orderRequest.getGroupShopId();
                Integer groupShopPrice = null;
                if (groupShopId != null) {
                    //校验团购参数
                    if (skuList.size() > 1) {
                        throw new AppServiceException(ExceptionDefinition.ORDER_GROUP_SPU_CAN_SINGLE_TAKE);
                    }
                    GroupShopDTO groupShopDTO = groupShopBizService.getGroupShopById(groupShopId);
                    if (groupShopDTO == null || groupShopDTO.getStatus() == StatusType.LOCK.getCode()) {
                        throw new AppServiceException(ExceptionDefinition.ORDER_GROUP_SHOP_NOT_EXIST_OR_EXPIRED);
                    }
                    List<GroupShopSkuDO> groupShopSkuList = groupShopDTO.getGroupShopSkuList();
                    for (GroupShopSkuDO groupShopSkuDO : groupShopSkuList) {
                        if (groupShopSkuDO.getSkuId().equals(groupShopSkuList.get(0).getSkuId())) {
                            //若找到交集
                            groupShopPrice = groupShopSkuDO.getSkuGroupShopPrice();
                        }
                    }
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
                        throw new AppServiceException(ExceptionDefinition.ORDER_SKU_STOCK_NOT_ENOUGH);
                    }
                    int p;
                    if (groupShopId != null && groupShopPrice != null) {
                        p = groupShopPrice;
                    } else if (userLevel == UserLevelType.VIP.getCode()) {
                        p = skuDTO.getVipPrice() * orderRequestSkuDTO.getNum();
                    } else {
                        p = skuDTO.getPrice() * orderRequestSkuDTO.getNum();
                    }
                    skuPrice += p;
                    skuOriginalPrice += skuDTO.getOriginalPrice() * orderRequestSkuDTO.getNum();
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

                Integer freightPrice = freightBizService.getFreightMoney(orderRequest);
                //参数强校验 END
                //???是否校验actualPrice??强迫校验？
                int actualPrice = skuPrice - couponPrice + freightPrice;
                Date now = new Date();
                OrderDO orderDO = new OrderDO();
                orderDO.setSkuTotalPrice(skuPrice);
                orderDO.setSkuOriginalTotalPrice(skuOriginalPrice);
                orderDO.setChannel(channel);
                orderDO.setActualPrice(actualPrice);
                orderDO.setGroupShopId(groupShopId);
                if (couponPrice != 0) {
                    orderDO.setCouponId(orderRequest.getCoupon().getCouponId());
                    orderDO.setCouponPrice(couponPrice);
                }
                orderDO.setMono(orderRequest.getMono());
                orderDO.setFreightPrice(freightPrice);
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
                    orderDO.setConsignee(addressDO.getConsignee());
                    orderDO.setPhone(addressDO.getPhone());
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
                    orderSkuDO.setUnit(skuDTO.getUnit());
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
                    //直接购买传值为 "buy"
                }

                return orderDO.getOrderNo();

            } catch (ServiceException e) {
                throw e;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object wxPrepay(String orderNo, String ip, Long userId) throws ServiceException {
        Date now = new Date();
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        // 检测订单状态
        Integer status = orderDO.getStatus();
        if (status != OrderStatusType.UNPAY.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_PAY);
        }

        Integer loginType = SessionUtil.getUser().getLoginType();
        String appId;
        String tradeType;
        if (UserLoginType.MP_WEIXIN.getCode() == loginType) {
            appId = wxMiNiAppid;
            tradeType = WxPayConstants.TradeType.JSAPI;
        } else if (UserLoginType.APP_WEIXIN.getCode() == loginType || UserLoginType.REGISTER.getCode() == loginType) {
            appId = wxAppAppid;
            tradeType = WxPayConstants.TradeType.APP;
        } else if (UserLoginType.H5_WEIXIN.getCode() == loginType) {
            appId = wxH5Appid;
            tradeType = WxPayConstants.TradeType.JSAPI;
        } else {
            throw new AppServiceException(ExceptionDefinition.ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY);
        }

        Object result = null;
        try {
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            orderRequest.setAppid(appId);
            orderRequest.setOutTradeNo(orderNo);
            orderRequest.setOpenid(SessionUtil.getUser().getOpenId());
            orderRequest.setBody("订单：" + orderNo);
            orderRequest.setTotalFee(orderDO.getActualPrice());
            orderRequest.setSpbillCreateIp(ip);
            orderRequest.setTradeType(tradeType);
            result = wxPayService.createOrder(orderRequest);
            if (result instanceof  WxPayMpOrderResult) {
                String prepayId = ((WxPayMpOrderResult)result).getPackageValue();
                prepayId = prepayId.replace("prepay_id=", "");
                UserFormIdDO userFormIdDO = new UserFormIdDO();
                userFormIdDO.setFormId(prepayId);
                userFormIdDO.setUserId(userId);
                userFormIdDO.setOpenid(SessionUtil.getUser().getOpenId());
                userFormIdDO.setGmtUpdate(now);
                userFormIdDO.setGmtCreate(now);
                userBizService.setValidFormId(userFormIdDO);
            }
        } catch (WxPayException e) {
            logger.error("[微信支付] 异常", e);
            throw new ThirdPartServiceException(e.getErrCodeDes(), ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
        } catch (Exception e) {
            logger.error("[预付款异常]", e);
            throw new AppServiceException(ExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object offlinePrepay(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        // 检测订单状态
        Integer status = orderDO.getStatus();
        if (status != OrderStatusType.UNPAY.getCode()) {
            throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_NOT_SUPPORT_PAY);
        }
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setPayChannel(PayChannelType.OFFLINE.getCode());
        updateOrderDO.setStatus(OrderStatusType.WAIT_STOCK.getCode());
        updateOrderDO.setGmtUpdate(new Date());
        boolean succ = orderBizService.changeOrderStatus(orderNo, OrderStatusType.UNPAY.getCode(), updateOrderDO);
        if (succ) {
            return "ok";
        }
        throw new AppServiceException(ExceptionDefinition.ORDER_STATUS_CHANGE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refund(String orderNo, Long userId) throws ServiceException {
        OrderDO orderDO = orderBizService.checkOrderExist(orderNo, userId);
        if (PayChannelType.OFFLINE.getCode().equals(orderDO.getPayChannel())) {
            throw new AppServiceException(ExceptionDefinition.ORDER_PAY_CHANNEL_NOT_SUPPORT_REFUND);
        }
        if (OrderStatusType.refundable(orderDO.getStatus())) {
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setStatus(OrderStatusType.REFUNDING.getCode());
            orderBizService.changeOrderStatus(orderNo, orderDO.getStatus() , updateOrderDO);
            GlobalExecutor.execute(() -> {
                OrderDTO orderDTO = new OrderDTO();
                BeanUtils.copyProperties(orderDO, orderDTO);
                List<OrderSkuDO> orderSkuList = orderSkuMapper.selectList(new EntityWrapper<OrderSkuDO>().eq("order_no", orderDO.getOrderNo()));
                orderDTO.setSkuList(orderSkuList);
                adminNotifyBizService.refundOrder(orderDTO);
            });
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
        if (StringUtils.isEmpty(orderDO.getShipCode()) || StringUtils.isEmpty(orderDO.getShipNo())) {
            throw new AppServiceException(ExceptionDefinition.ORDER_DID_NOT_SET_SHIP);
        }
        ShipTraceDTO shipTraceList = freightBizService.getShipTraceList(orderDO.getShipNo(), orderDO.getShipCode());
        if (CollectionUtils.isEmpty(shipTraceList.getTraces())) {
            throw new AppServiceException(ExceptionDefinition.ORDER_DO_NOT_EXIST_SHIP_TRACE);
        }
        return shipTraceList;
    }


}
