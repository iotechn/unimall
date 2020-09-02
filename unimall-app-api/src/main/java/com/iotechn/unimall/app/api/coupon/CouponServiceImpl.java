package com.iotechn.unimall.app.api.coupon;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.constant.LockConst;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.domain.CouponUserDO;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.dto.CouponUserDTO;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.CouponMapper;
import com.iotechn.unimall.data.mapper.CouponUserMapper;
import com.iotechn.unimall.data.model.KVModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/4.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponUserMapper couponUserMapper;

    @Autowired
    private LockComponent lockComponent;

    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String obtainCoupon(Long couponId, Long userId) throws ServiceException {
        //防止用户一瞬间提交两次表单，导致超领
        if (lockComponent.tryLock(LockConst.COUPON_USER_LOCK + userId + "_" + couponId, 10)) {
            try {
                CouponDO couponDO = couponMapper.selectById(couponId);
                if (couponDO.getStatus() == StatusType.LOCK.getCode()) {
                    throw new AppServiceException(ExceptionDefinition.COUPON_HAS_LOCKED);
                }
                Date now = new Date();
                if (couponDO.getGmtEnd() != null && couponDO.getGmtEnd().getTime() < now.getTime()) {
                    throw new AppServiceException(ExceptionDefinition.COUPON_ACTIVITY_HAS_END);
                }
                if (couponDO.getGmtStart() != null && couponDO.getGmtStart().getTime() > now.getTime()) {
                    throw new AppServiceException(ExceptionDefinition.COUPON_ACTIVITY_NOT_START);
                }
                if (couponDO.getTotal() != -1 && couponDO.getSurplus() <= 0) {
                    throw new AppServiceException(ExceptionDefinition.COUPON_ISSUE_OVER);
                } else {
                    if (couponDO.getTotal() >= 0) {
                        if (couponDO.getSurplus() == 1) {
                            if (!lockComponent.tryLock(LockConst.COUPON_LOCK + couponId, 10)) {
                                throw new AppServiceException(ExceptionDefinition.COUPON_ISSUE_OVER);
                            }
                        }
                        couponMapper.decCoupon(couponId);
                    }
                }


                if (couponDO.getLimit() != -1) {
                    //校验用户是否已经领了
                    Integer count = couponUserMapper.selectCount(
                            new QueryWrapper<CouponUserDO>()
                                    .eq("user_id", userId)
                                    .eq("coupon_id", couponId));

                    if (count >= couponDO.getLimit()) {
                        throw new AppServiceException(ExceptionDefinition.COUPON_YOU_HAVE_OBTAINED);
                    }
                }

                //领取优惠券
                CouponUserDO userCouponDO = new CouponUserDO();
                userCouponDO.setUserId(userId);
                userCouponDO.setCouponId(couponId);
                if (couponDO.getGmtStart() != null && couponDO.getGmtEnd() != null) {
                    //如果优惠券是按区间领取的
                    userCouponDO.setGmtStart(couponDO.getGmtStart());
                    userCouponDO.setGmtEnd(couponDO.getGmtEnd());
                } else if (couponDO.getDays() != null) {
                    //如果是任意领取的，则从当前时间 加上 可用天数
                    userCouponDO.setGmtStart(now);
                    userCouponDO.setGmtEnd(new Date(now.getTime() + 1000l * 60 * 60 * 24 * couponDO.getDays()));
                } else {
                    throw new AppServiceException(ExceptionDefinition.COUPON_STRATEGY_INCORRECT);
                }

                userCouponDO.setGmtUpdate(now);
                userCouponDO.setGmtCreate(now);

                couponUserMapper.insert(userCouponDO);
                return "ok";
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[领取优惠券] 异常", e);
                throw new AppServiceException(ExceptionDefinition.APP_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(LockConst.COUPON_USER_LOCK + userId + "_" + couponId);
            }
        } else {
            throw new AppServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }

    }

    @Override
    public List<CouponDTO> getObtainableCoupon(Long userId) throws ServiceException {
        List<CouponDTO> couponDOS = couponMapper.getActiveCoupons();
        //活动中的优惠券Id
        List<Long> activeCouponIds = couponDOS.stream().map(couponDO -> couponDO.getId()).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(activeCouponIds)) {
            return new ArrayList<>();
        }

        List<KVModel<Long, Long>> userCouponsCount = couponMapper.getUserCouponsCount(userId, activeCouponIds);

        List<CouponDTO> couponDTOList = couponDOS.stream().map(item -> {
            item.setNowCount(0);
            for (int i = 0; i < userCouponsCount.size(); i++) {
                KVModel<Long, Long> kv = userCouponsCount.get(i);
                if (kv != null && kv.getKey().equals(item.getId())) {
                    item.setNowCount(kv.getValue().intValue());
                }
            }
            return item;
        }).filter(item -> (item.getCategoryId() == null || (item.getCategoryId() != null && !StringUtils.isEmpty(item.getCategoryTitle())))).collect(Collectors.toList());
        return couponDTOList;
    }

    @Override
    public List<CouponUserDTO> getUserCoupons(Long userId) throws ServiceException {
        return couponUserMapper.getUserCoupons(userId);
    }
}
