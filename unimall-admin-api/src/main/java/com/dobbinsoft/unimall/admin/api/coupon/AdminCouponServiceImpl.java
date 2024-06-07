package com.dobbinsoft.unimall.admin.api.coupon;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.domain.CouponDO;
import com.dobbinsoft.unimall.data.domain.CouponUserDO;
import com.dobbinsoft.unimall.data.dto.coupon.CouponAdminDTO;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.CouponMapper;
import com.dobbinsoft.unimall.data.mapper.CouponUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-12
 * Time: 下午11:26
 */
@Service
public class AdminCouponServiceImpl implements AdminCouponService {

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponUserMapper couponUserMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CouponDO create(String title, Integer type, Integer isVip, String description, Integer total, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, LocalDateTime gmtStart, LocalDateTime gmtEnd, Long adminId) throws ServiceException {
        CouponDO couponDO = new CouponDO();
        couponDO.setTitle(title);
        couponDO.setType(type);
        couponDO.setIsVip(isVip);
        couponDO.setDescription(description);
        couponDO.setTotal(total);
        couponDO.setSurplus(total);
        couponDO.setLimit(limit);
        couponDO.setDiscount(discount);
        couponDO.setMin(min);
        couponDO.setStatus(status);
        couponDO.setCategoryId(categoryId);
        couponDO.setDays(days);
        couponDO.setGmtStart(gmtStart);
        couponDO.setGmtEnd(gmtEnd);
        LocalDateTime now = LocalDateTime.now();
        couponDO.setGmtCreate(now);
        couponDO.setGmtUpdate(now);
        if (couponMapper.insert(couponDO) > 0) {
            return couponDO;
        }
        throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id, Long adminId) throws ServiceException {
        if (couponMapper.deleteById(id) <= 0) {
            throw new ServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        QueryWrapper<CouponUserDO> wrapperUC = new QueryWrapper<>();
        wrapperUC.eq("coupon_id", id);
        couponUserMapper.delete(wrapperUC);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(Long id, String title, Integer type, Integer isVip, String description, Integer total, Integer surplus, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, LocalDateTime gmtStart, LocalDateTime gmtEnd, Long adminId) throws ServiceException {
        CouponDO couponDO = new CouponDO();
        couponDO.setId(id);
        couponDO.setTitle(title);
        couponDO.setType(type);
        couponDO.setIsVip(isVip);
        couponDO.setDescription(description);
        couponDO.setTotal(total);
        couponDO.setSurplus(total);
        couponDO.setLimit(limit);
        couponDO.setDiscount(discount);
        couponDO.setMin(min);
        couponDO.setStatus(status);
        couponDO.setCategoryId(categoryId);
        couponDO.setDays(days);
        couponDO.setGmtStart(gmtStart);
        couponDO.setGmtEnd(gmtEnd);
        List<CouponDO> couponDOList = couponMapper.selectList(new QueryWrapper<CouponDO>().eq("id", id));
        if (CollectionUtils.isEmpty(couponDOList)) {
            throw new ServiceException(ExceptionDefinition.COUPON_NOT_EXIST);
        }
        LocalDateTime now = LocalDateTime.now();
        couponDO.setGmtCreate(couponDOList.get(0).getGmtCreate());
        couponDO.setGmtUpdate(now);
        return couponMapper.updateById(couponDO) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCouponStatus(Long id, Integer status, Long adminId) throws ServiceException {
        CouponDO couponDO = new CouponDO();
        couponDO.setId(id);
        couponDO.setStatus(status);
        couponDO.setGmtUpdate(LocalDateTime.now());
        return couponMapper.updateById(couponDO) > 0;
    }

    @Override
    public Page<CouponAdminDTO> list(String title, Integer type, Integer status, Integer pageNo, Integer limit, Long adminId) throws ServiceException {
        Page<CouponAdminDTO> page = couponMapper.getAdminCouponList(Page.div(pageNo, limit, CouponAdminDTO.class), title, type, status, LocalDateTime.now());
        return page;
    }
}
