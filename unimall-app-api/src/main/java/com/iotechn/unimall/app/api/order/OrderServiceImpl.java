package com.iotechn.unimall.app.api.order;

import com.iotechn.unimall.app.api.category.CategoryService;
import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.util.GeneratorUtil;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.AddressDO;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.dto.SkuDTO;
import com.iotechn.unimall.data.dto.UserCouponDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestSkuDTO;
import com.iotechn.unimall.data.enums.OrderStatusType;
import com.iotechn.unimall.data.enums.UserLevelType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CategoryService categoryService;

    @Autowired
    private LockComponent lockComponent;

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
                    throw new AppServiceException(AppExceptionDefinition.APP_PARAM_CHECK_FAILED);
                }
                if (orderRequest.getTotalPrice() <= 0) {
                    throw new AppServiceException(AppExceptionDefinition.ORDER_PRICE_MUST_GT_ZERO);
                }
                //商品价格
                int skuPrice = 0;
                //稍后用于优惠券作用范围校验
                Map<Long, Integer> categoryPriceMap = new HashMap<>();
                //稍后用于插入OrderSku
                Map<Long, SkuDTO> skuIdDTOMap = new HashMap<>();
                for (OrderRequestSkuDTO orderRequestSkuDTO : skuList) {
                    SkuDTO skuDTO = skuMapper.getSkuDTOById(orderRequestSkuDTO.getSkuId());
                    skuIdDTOMap.put(skuDTO.getId(), skuDTO);
                    if (skuDTO == null) {
                        throw new AppServiceException(AppExceptionDefinition.ORDER_SKU_NOT_EXIST);
                    }
                    if (skuDTO.getStock() < orderRequestSkuDTO.getNum()) {
                        //TODO 这里存在并发问题
                        throw new AppServiceException(AppExceptionDefinition.ORDER_SKU_STOCK_NOT_ENOUGH);
                    }
                    int p;
                    if (userLevel == UserLevelType.VIP.getCode()) {
                        p = skuDTO.getVipPrice() * orderRequestSkuDTO.getNum();
                    } else {
                        p = skuDTO.getPrice() * orderRequestSkuDTO.getNum();
                    }
                    skuPrice += p;
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
                    throw new AppServiceException(AppExceptionDefinition.ORDER_PRICE_CHECK_FAILED);
                }

                //优惠券折扣价格
                int couponPrice = 0;
                //优惠券校验
                UserCouponDTO userCouponFromFront = orderRequest.getCoupon();
                if (userCouponFromFront != null) {
                    if (userCouponFromFront.getId() == null || userCouponFromFront.getDiscount() == null) {
                        throw new AppServiceException(AppExceptionDefinition.APP_PARAM_CHECK_FAILED);
                    }

                    UserCouponDTO userCouponFromDB = userCouponMapper.getUserCouponById(userCouponFromFront.getId(), userId);

                    if (userCouponFromDB == null) {
                        throw new AppServiceException(AppExceptionDefinition.ORDER_COUPON_NOT_EXIST);
                    }

                    if (!userCouponFromDB.getDiscount().equals(userCouponFromFront.getDiscount())) {
                        throw new AppServiceException(AppExceptionDefinition.ORDER_COUPON_DISCOUNT_CHECK_FAILED);
                    }

                    //校验优惠券策略是否满足
                    Long categoryId = userCouponFromDB.getCategoryId();
                    if (categoryId != null) {
                        Integer p = categoryPriceMap.get(categoryId);
                        if (p < userCouponFromDB.getMin()) {
                            throw new AppServiceException(AppExceptionDefinition.ORDER_COUPON_PRICE_NOT_ENOUGH);
                        }
                    } else {
                        if (skuPrice < userCouponFromDB.getMin()) {
                            throw new AppServiceException(AppExceptionDefinition.ORDER_COUPON_PRICE_NOT_ENOUGH);
                        }
                    }
                    couponPrice = userCouponFromDB.getDiscount();
                }

                //TODO 运费
                //参数强校验 END
                //???是否校验actualPrice??强迫校验？
                int actualPrice = skuPrice - couponPrice;
                Date now = new Date();
                OrderDO orderDO = new OrderDO();
                orderDO.setSkuTotalPrice(skuPrice);
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
                        throw new AppServiceException(AppExceptionDefinition.ORDER_ADDRESS_NOT_BELONGS_TO_YOU);
                    }
                    orderDO.setProvince(addressDO.getProvince());
                    orderDO.setCity(addressDO.getCity());
                    orderDO.setCounty(addressDO.getCounty());
                    orderDO.setAddress(addressDO.getAddress());
                }
                orderMapper.insert(orderDO);

                //插入OrderSku
                skuList.forEach(item -> {
                    SkuDTO skuDTO = skuIdDTOMap.get(item.getSkuId());
                    OrderSkuDO orderSkuDO = new OrderSkuDO();
                    orderSkuDO.setBarCode(skuDTO.getBarCode());
                    orderSkuDO.setSkuTitle(skuDTO.getTitle());
                    orderSkuDO.setTitle(skuDTO.getSpuTitle());
                    orderSkuDO.setImg(skuDTO.getImg() == null ? skuDTO.getSpuImg() : skuDTO.getImg());
                    orderSkuDO.setNum(item.getNum());
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
                });

                return "ok";

            } catch (Exception e) {
                logger.error("[提交订单] 异常", e);
                throw new AppServiceException(AppExceptionDefinition.ORDER_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(TAKE_ORDER_LOCK + userId);
            }
        }
        throw new AppServiceException(AppExceptionDefinition.ORDER_SYSTEM_BUSY);
    }
}
