package com.iotechn.unimall.admin.api.coupon;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.dto.CouponAdminDTO;
import com.iotechn.unimall.data.mapper.CouponMapper;
import com.iotechn.unimall.data.mapper.UserCouponMapper;
import com.iotechn.unimall.data.model.Page;
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
    private UserCouponMapper userCouponMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CouponDO addCoupon(Long adminId, String title, Integer type, String description, Integer total, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, Long gmtStart, Long gmtEnd) throws ServiceException {

        Date start = null;
        Date end = null;
        if (gmtEnd != null && gmtStart != null) {
            start = new Date(gmtStart);
            end = new Date(gmtEnd);
        }

        CouponDO couponDO = new CouponDO(title, type, description, total, total, limit, discount, min, status, categoryId, days, start, end);

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
    public Boolean deleteCoupon(Long adminId, Long id) throws ServiceException {
        EntityWrapper wrapperC = new EntityWrapper();
        wrapperC.eq("id", id);
        if (couponMapper.delete(wrapperC) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        EntityWrapper wrapperUC = new EntityWrapper();
        wrapperUC.eq("coupon_id", id);
        userCouponMapper.delete(wrapperUC);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCoupon(Long adminId, Long id, String title, Integer type, String description, Integer total, Integer surplus, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, Date gmtStart, Date gmtEnd) throws ServiceException {
        CouponDO couponDO = new CouponDO(title, type, description, total, surplus, limit, discount, min, status, categoryId, days, gmtStart, gmtEnd);
        couponDO.setId(id);
        List<CouponDO> couponDOList = couponMapper.selectList(new EntityWrapper<CouponDO>().eq("id", id));
        if (CollectionUtils.isEmpty(couponDOList)) {
            throw new AdminServiceException(ExceptionDefinition.COUPON_NOT_EXIST);
        }
        Date now = new Date();
        couponDO.setGmtCreate(couponDOList.get(0).getGmtCreate());
        couponDO.setGmtUpdate(now);
        return couponMapper.updateAllColumnById(couponDO) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCouponStatus(Long adminId, Long id, Integer status) throws ServiceException {
        CouponDO couponDO = new CouponDO();
        couponDO.setId(id);
        couponDO.setStatus(status);
        couponDO.setGmtUpdate(new Date());
        return couponMapper.updateById(couponDO) > 0;
    }

    @Override
    public Page<CouponAdminDTO> queryCouponByTitle(Long adminId, String title, Integer type, Integer status, Integer pageNo, Integer limit) throws ServiceException {
        EntityWrapper wrapper = new EntityWrapper();
        Date now = new Date();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (type != null) {
            wrapper.eq("type", type);
        }
        if (status != null) {
            if (status >= 0 && status < 2) {
                wrapper.eq("status", status);
                wrapper.andNew().gt("gmt_end", now).or().isNotNull("days"); //coupon -> conpon.gt("gmt_end", now).or().isNotNull("days")
            } else if (status < 0) {
                wrapper.lt("gmt_end", now);
            } else {
                throw new AdminServiceException(ExceptionDefinition.COUPON_CHECK_DATA_FAILED);
            }
        }
        Integer count = couponMapper.selectCount(wrapper);
        List<CouponAdminDTO> couponDTOList = couponMapper.getAdminCouponList(title, type, status, now, (pageNo - 1) * limit, limit);
        Page<CouponAdminDTO> page = new Page<CouponAdminDTO>(couponDTOList, pageNo, limit, count);
        return page;
    }
}
