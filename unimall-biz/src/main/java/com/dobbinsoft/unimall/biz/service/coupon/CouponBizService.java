package com.dobbinsoft.unimall.biz.service.coupon;

import com.dobbinsoft.unimall.data.domain.CouponUserDO;
import com.dobbinsoft.unimall.data.dto.coupon.CouponUserDTO;
import com.dobbinsoft.unimall.data.mapper.CouponUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Description:
 * User: rize
 * Date: 2020/8/2
 * Time: 15:45
 */
@Service("couponBizService")
public class CouponBizService {

    @Autowired
    private CouponUserMapper couponUserMapper;

    public CouponUserDTO getCouponUserById(Long couponUserId, Long userId) {
        return couponUserMapper.getCouponUserById(couponUserId, userId);
    }

    public Integer useCoupon(Long couponUserId, Long orderId) {
        CouponUserDO couponUserDO = new CouponUserDO();
        couponUserDO.setId(couponUserId);
        couponUserDO.setGmtUsed(LocalDateTime.now());
        couponUserDO.setOrderId(orderId);
        return couponUserMapper.updateById(couponUserDO);
    }

}
