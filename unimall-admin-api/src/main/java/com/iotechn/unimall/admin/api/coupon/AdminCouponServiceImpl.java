package com.iotechn.unimall.admin.api.coupon;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.AdminServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.dto.CouponAdminDTO;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.CouponMapper;
import com.iotechn.unimall.data.mapper.CouponUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
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
    public CouponDO create(String title, Integer type, Integer isVip, String description, Integer total, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, Long gmtStart, Long gmtEnd, Long adminId) throws ServiceException {
        Date start = null;
        Date end = null;
        if (gmtEnd != null && gmtStart != null) {
            start = new Date(gmtStart);
            end = new Date(gmtEnd);
        }

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
        couponDO.setGmtStart(start);
        couponDO.setGmtEnd(end);
        Date now = new Date();
        couponDO.setGmtCreate(now);
        couponDO.setGmtUpdate(now);
        if (couponMapper.insert(couponDO) > 0) {
            return couponDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id, Long adminId) throws ServiceException {
        QueryWrapper wrapperC = new QueryWrapper();
        wrapperC.eq("id", id);
        if (couponMapper.delete(wrapperC) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        QueryWrapper wrapperUC = new QueryWrapper();
        wrapperUC.eq("coupon_id", id);
        couponUserMapper.delete(wrapperUC);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(Long id, String title, Integer type, Integer isVip, String description, Integer total, Integer surplus, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, Date gmtStart, Date gmtEnd, Long adminId) throws ServiceException {
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
            throw new AdminServiceException(ExceptionDefinition.COUPON_NOT_EXIST);
        }
        Date now = new Date();
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
        couponDO.setGmtUpdate(new Date());
        return couponMapper.updateById(couponDO) > 0;
    }

    @Override
    public Page<CouponAdminDTO> list(String title, Integer type, Integer status, Integer pageNo, Integer limit, Long adminId) throws ServiceException {
        Page<CouponAdminDTO> page = couponMapper.getAdminCouponList(Page.div(pageNo, limit, CouponAdminDTO.class), title, type, status, new Date());
        return page;
    }
}
