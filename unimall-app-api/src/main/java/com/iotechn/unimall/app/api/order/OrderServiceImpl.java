package com.iotechn.unimall.app.api.order;

import com.iotechn.unimall.app.api.category.CategoryService;
import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.dto.SkuDTO;
import com.iotechn.unimall.data.dto.UserCouponDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestSkuDTO;
import com.iotechn.unimall.data.mapper.SkuMapper;
import com.iotechn.unimall.data.mapper.UserCouponMapper;
import com.iotechn.unimall.data.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
    private CategoryService categoryService;

    @Autowired
    private LockComponent lockComponent;

    @Override
    public String takeOrder(OrderRequestDTO orderRequest, String channel, Long userId) throws ServiceException {
        if (lockComponent.tryLock(TAKE_ORDER_LOCK + userId, 20)) {
            //加上乐观锁，防止用户重复提交订单
            try {
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
                for (OrderRequestSkuDTO orderRequestSkuDTO : skuList) {
                    SkuDTO skuDTO = skuMapper.getSkuDTOById(orderRequestSkuDTO.getSkuId());
                    if (skuDTO == null) {
                        throw new AppServiceException(AppExceptionDefinition.ORDER_SKU_NOT_EXIST);
                    }
                    if (skuDTO.getStock() < orderRequestSkuDTO.getNum()) {
                        //TODO 这里存在并发问题
                        throw new AppServiceException(AppExceptionDefinition.ORDER_SKU_STOCK_NOT_ENOUGH);
                    }
                    Integer level = SessionUtil.getUser().getLevel();
                    int p;
                    if (level == 1) {
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

                int actualPrice = skuPrice - couponPrice;
                OrderDO orderDO = new OrderDO();
                orderDO.setChannel(channel);


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
