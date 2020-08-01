package com.iotechn.unimall.data.mapper;

import com.iotechn.unimall.data.domain.UserCouponDO;
import com.iotechn.unimall.data.dto.UserCouponDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/4.
 */
public interface UserCouponMapper extends IMapper<UserCouponDO> {

    public List<UserCouponDTO> getUserCoupons(Long userId);

    public UserCouponDTO getUserCouponById(@Param("userCouponId") Long userCouponId, @Param("userId") Long userId);

}
