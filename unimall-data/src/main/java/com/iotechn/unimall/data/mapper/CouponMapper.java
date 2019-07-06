package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.dto.UserCouponDTO;
import com.iotechn.unimall.data.model.KVModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/4.
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    public Integer decCoupon(Long couponId);

    public List<KVModel<Long,Integer>> getUserCouponsCount(@Param("userId") Long userId, @Param("couponIds") List<Long> couponIds);

}
