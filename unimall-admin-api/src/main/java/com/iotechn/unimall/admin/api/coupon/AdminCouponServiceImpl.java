package com.iotechn.unimall.admin.api.coupon;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.dto.CouponAdminDTO;
import com.iotechn.unimall.data.dto.CouponDTO;
import com.iotechn.unimall.data.mapper.CouponMapper;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
public class AdminCouponServiceImpl implements  AdminCouponService {

    @Autowired
    private CouponMapper couponMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCoupon(Long adminId, String title, Integer type, String description, Integer total, Integer surplus, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, Date gmtStart, Date gmtEnd) throws ServiceException {

        CouponDO couponDO = new CouponDO(title,type,description,total,surplus,limit,discount,min,status,categoryId,days,gmtStart,gmtEnd);

        Date now = new Date();
        couponDO.setGmtCreate(now);
        couponDO.setGmtUpdate(now);
        return couponMapper.insert(couponDO) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCoupon(Long adminId, Long id) throws ServiceException {

        return couponMapper.delete(new EntityWrapper<CouponDO>().eq("id", id)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCoupon(Long adminId, Long id, String title, Integer type, String description, Integer total, Integer surplus, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, Date gmtStart, Date gmtEnd) throws ServiceException {
        CouponDO couponDO = new CouponDO(title,type,description,total,surplus,limit,discount,min,status,categoryId,days,gmtStart,gmtEnd);
        couponDO.setId(id);
        List<CouponDO> couponDOList = couponMapper.selectList(new EntityWrapper<CouponDO>().eq("id",id ));
        if(CollectionUtils.isEmpty(couponDOList)){
            throw new AppServiceException(ExceptionDefinition.COUPON_NOT_EXIST);
        }
        Date now = new Date();
        couponDO.setGmtCreate(couponDOList.get(0).getGmtCreate());
        couponDO.setGmtUpdate(now);
        return couponMapper.updateAllColumnById(couponDO) > 0;
    }

    @Override
    public Page<CouponAdminDTO> queryCouponByTitle(Long adminId, String title,Integer type,Integer status,Integer pageNo, Integer limit) throws ServiceException {
        EntityWrapper wrapper = new EntityWrapper();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }
        if(type != null){
            wrapper.eq("type",type );
        }
        if(status != null){
            if(status >= 0 && status < 2){
                wrapper.eq("status", status).gt("gmt_end", new Date());
                wrapper.addFilter("and status ="+status + "",)

            } else if(status < 0){
                wrapper.lt("gmt_end", new Date());
            } else{
                throw new AppServiceException(ExceptionDefinition.COUPON_CHECK_DATA_FAILED);
            }
        }
        Integer count = couponMapper.selectCount(wrapper);
        List<CouponAdminDTO> couponDTOList = couponMapper.getAdminCouponList(title,type,status,new Date(),(pageNo-1)*limit,limit);
        Page<CouponAdminDTO> page = new Page<CouponAdminDTO>(couponDTOList,pageNo,limit,count);
        return page;
    }
}
