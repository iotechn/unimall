package com.dobbinsoft.unimall.app.api.coupon;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.entiy.SuperDTO;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.LockComponent;
import com.dobbinsoft.unimall.data.model.KVModel;
import com.dobbinsoft.fw.support.service.BaseService;
import com.dobbinsoft.unimall.data.constant.LockConst;
import com.dobbinsoft.unimall.data.domain.CouponDO;
import com.dobbinsoft.unimall.data.domain.CouponUserDO;
import com.dobbinsoft.unimall.data.dto.admin.AdminDTO;
import com.dobbinsoft.unimall.data.dto.coupon.CouponCountDTO;
import com.dobbinsoft.unimall.data.dto.coupon.CouponDTO;
import com.dobbinsoft.unimall.data.dto.coupon.CouponUserDTO;
import com.dobbinsoft.unimall.data.dto.UserDTO;
import com.dobbinsoft.unimall.data.enums.StatusType;
import com.dobbinsoft.unimall.data.enums.UserLevelType;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.CouponMapper;
import com.dobbinsoft.unimall.data.mapper.CouponUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.dobbinsoft.fw.support.utils.StringUtils;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/4.
 */
@Service
public class CouponServiceImpl extends BaseService<UserDTO, AdminDTO> implements CouponService {

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
                if (couponDO.getStatus().intValue() == StatusType.LOCK.getCode()) {
                    throw new ServiceException(ExceptionDefinition.COUPON_HAS_LOCKED);
                }
                if (couponDO.getIsVip().intValue() == 1) {
                    if (sessionUtil.getUser().getLevel().intValue() != UserLevelType.VIP.getCode()) {
                        throw new ServiceException(ExceptionDefinition.COUPON_IS_VIP_ONLY);
                    }
                }
                LocalDateTime now = LocalDateTime.now();
                if (couponDO.getGmtEnd() != null && couponDO.getGmtEnd().isBefore(now)) {
                    throw new ServiceException(ExceptionDefinition.COUPON_ACTIVITY_HAS_END);
                }
                if (couponDO.getGmtStart() != null && couponDO.getGmtStart().isAfter(now)) {
                    throw new ServiceException(ExceptionDefinition.COUPON_ACTIVITY_NOT_START);
                }
                if (couponDO.getTotal() != -1 && couponDO.getSurplus() <= 0) {
                    throw new ServiceException(ExceptionDefinition.COUPON_ISSUE_OVER);
                } else {
                    if (couponDO.getTotal() >= 0) {
                        if (couponDO.getSurplus() == 1) {
                            if (!lockComponent.tryLock(LockConst.COUPON_LOCK + couponId, 10)) {
                                throw new ServiceException(ExceptionDefinition.COUPON_ISSUE_OVER);
                            }
                        }
                        couponMapper.decCoupon(couponId);
                    }
                }


                if (couponDO.getLimit() != -1) {
                    //校验用户是否已经领了
                    Long count = couponUserMapper.selectCount(
                            new QueryWrapper<CouponUserDO>()
                                    .eq("user_id", userId)
                                    .eq("coupon_id", couponId));

                    if (count >= couponDO.getLimit()) {
                        throw new ServiceException(ExceptionDefinition.COUPON_YOU_HAVE_OBTAINED);
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
                    userCouponDO.setGmtEnd(LocalDateTime.now().plusDays(couponDO.getDays()));
                } else {
                    throw new ServiceException(ExceptionDefinition.COUPON_STRATEGY_INCORRECT);
                }

                userCouponDO.setGmtUpdate(now);
                userCouponDO.setGmtCreate(now);

                couponUserMapper.insert(userCouponDO);
                return "ok";
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                logger.error("[领取优惠券] 异常", e);
                throw new ServiceException(ExceptionDefinition.APP_UNKNOWN_EXCEPTION);
            } finally {
                lockComponent.release(LockConst.COUPON_USER_LOCK + userId + "_" + couponId);
            }
        } else {
            throw new ServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }

    }

    @Override
    public List<CouponDTO> getObtainableCoupon(Long userId) throws ServiceException {
        List<CouponDTO> couponDOS = couponMapper.getActiveCoupons();
        //活动中的优惠券Id
        List<Long> activeCouponIds = couponDOS.stream().map(SuperDTO::getId).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(activeCouponIds)) {
            return new ArrayList<>();
        }

        List<CouponCountDTO> userCouponsCount = couponMapper.getUserCouponsCount(userId, activeCouponIds);

        return couponDOS.stream().peek(item -> {
            item.setNowCount(0);
            for (CouponCountDTO kv : userCouponsCount) {
                if (kv != null && kv.getCouponId().equals(item.getId())) {
                    item.setNowCount(kv.getCouponCount());
                }
            }
        }).filter(item -> item.getCategoryId() == null || !StringUtils.isEmpty(item.getCategoryTitle())).collect(Collectors.toList());
    }

    @Override
    public List<CouponUserDTO> getUserCoupons(Long userId) throws ServiceException {
        return couponUserMapper.getUserCoupons(userId);
    }

    @Override
    public List<CouponDTO> getVipCoupons() throws ServiceException {
        return couponMapper.getActiveCoupons().stream().filter(item -> item.getIsVip().intValue() == 1).collect(Collectors.toList());
    }
}
