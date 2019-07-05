package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.UserCouponDO;
import com.iotechn.unimall.data.dto.UserCouponDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by rize on 2019/7/4.
 */
public interface UserCouponMapper extends BaseMapper<UserCouponDO> {

    public List<UserCouponDTO> getUserCoupons(Long userId);

}
