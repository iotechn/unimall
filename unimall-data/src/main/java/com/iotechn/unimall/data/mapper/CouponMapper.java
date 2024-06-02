package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.dto.coupon.CouponAdminDTO;
import com.iotechn.unimall.data.dto.coupon.CouponCountDTO;
import com.iotechn.unimall.data.dto.coupon.CouponDTO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by rize on 2019/7/4.
 */
public interface CouponMapper extends IMapper<CouponDO> {

    public Integer decCoupon(Long couponId);

    //这样写MyBatis无法直接映射泛型，只能用Long了
    public List<CouponCountDTO> getUserCouponsCount(@Param("userId") Long userId, @Param("couponIds") List<Long> couponIds);

    public List<CouponDTO> getActiveCoupons();

    public Page<CouponAdminDTO> getAdminCouponList(IPage<CouponAdminDTO> page, @Param("title") String title, @Param("type") Integer type, @Param("status") Integer status, @Param("now") LocalDateTime now);

}
