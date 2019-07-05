package com.iotechn.unimall.app.api.coupon;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ServiceExceptionDefinition;
import com.iotechn.unimall.data.component.LockComponent;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.domain.UserCouponDO;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.CouponMapper;
import com.iotechn.unimall.data.mapper.UserCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by rize on 2019/7/4.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private UserCouponMapper userCouponMapper;

    @Autowired
    private LockComponent lockComponent;

    private static final String COUPON_LOCK = "COUPON_LOCK_";

    @Override
    @Transactional
    public String obtainCoupon(Long couponId, Long userId) throws ServiceException {
        CouponDO couponDO = couponMapper.selectById(couponId);
        if (couponDO.getStatus() == StatusType.LOCK.getCode()) {
            throw new AppServiceException(AppExceptionDefinition.COUPON_HAS_LOCKED);
        }
        long now = System.currentTimeMillis();
        if (couponDO.getGmtEnd() != null && couponDO.getGmtEnd().getTime() > now) {
            throw new AppServiceException(AppExceptionDefinition.COUPON_ACTIVITY_HAS_END);
        }
        if (couponDO.getGmtStart() != null && couponDO.getGmtStart().getTime() < now) {
            throw new AppServiceException(AppExceptionDefinition.COUPON_ACTIVITY_NOT_START);
        }
        if (couponDO.getTotal() != -1 && couponDO.getSurplus() <= 0) {
            throw new AppServiceException(AppExceptionDefinition.COUPON_ISSUE_OVER);
        }
        if (couponDO.getSurplus() == 1) {
            if (!lockComponent.tryLock(COUPON_LOCK + couponId, 10)) {
                throw new AppServiceException(AppExceptionDefinition.COUPON_ISSUE_OVER);
            }
        }

        if (couponDO.getLimit() != -1) {
            //校验用户是否已经领了
            Integer count = userCouponMapper.selectCount(
                    new EntityWrapper<UserCouponDO>()
                            .eq("user_id", userId)
                            .eq("coupon_id", couponId));

            if (count >= couponDO.getLimit()) {
                throw new AppServiceException(AppExceptionDefinition.COUPON_YOU_HAVE_OBTAINED);
            }
        }

        //领取优惠券
        UserCouponDO userCouponDO = new UserCouponDO();
        userCouponDO.setCouponId(couponId);






        return null;
    }

    @Override
    public List<CouponDTO> getObtainableCoupon(Long userId) throws ServiceException {
        List<CouponDO> couponDOS = couponMapper.selectList(
                new EntityWrapper<CouponDO>()
                        .isNull("gmt_end")
                        .or().gt("gmt_end", new Date()));
        List<Long> couponIds = couponDOS.stream().map(couponDO -> couponDO.getId()).collect(Collectors.toList());


        return null;
    }
}
