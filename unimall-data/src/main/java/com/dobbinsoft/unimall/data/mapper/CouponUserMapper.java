package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.CouponUserDO;
import com.dobbinsoft.unimall.data.dto.coupon.CouponUserDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/4.
 */
public interface CouponUserMapper extends IMapper<CouponUserDO> {

    public List<CouponUserDTO> getUserCoupons(Long userId);

    public CouponUserDTO getCouponUserById(@Param("userCouponId") Long userCouponId, @Param("userId") Long userId);

}
