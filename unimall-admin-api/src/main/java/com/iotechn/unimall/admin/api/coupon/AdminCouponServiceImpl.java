package com.iotechn.unimall.admin.api.coupon;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CouponDO;
import com.iotechn.unimall.data.mapper.CouponMapper;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
public class AdminCouponServiceImpl implements  AdminCouponService {

    @Autowired
    private CouponMapper couponMapper;
    @Override
    public Boolean addCoupon(Long adminId, String title, Integer type, String description, Integer total, Integer surplus, Integer limit, Integer discount, Integer min, Integer status, Long categoryId, Integer days, Date gmtStart, Date gmtEnd) throws ServiceException {

        CouponDO couponDO = new CouponDO(title,type,description,total,surplus,limit,discount,min,status,categoryId,days,gmtStart,gmtEnd);

        Date now = new Date();
        couponDO.setGmtCreate(now);
        couponDO.setGmtUpdate(now);
        return couponMapper.insert(couponDO) > 0;
    }

    @Override
    public Boolean deleteCoupon(Long adminId, Long id) throws ServiceException {

        return couponMapper.delete(new EntityWrapper<CouponDO>().eq("id", id)) > 0;
    }

    @Override
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
    public Page<CouponDO> queryCouponByTitle(Long adminId, String title,Integer type,Integer status,Integer pageNo, Integer limit) throws ServiceException {
        EntityWrapper wrapper = new EntityWrapper();
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }
        if(type != null){
            wrapper.eq("type",type );
        }
        if(status != null){
            if(status >= 0 && status < 2){
                wrapper.eq("status", status);
            }else if(status == 3){
                wrapper.lt("gmt_end", new Date());
            }else {
                throw new AppServiceException(ExceptionDefinition.COUPON_CHECK_DATA_FAILED);
            }
        }
        Integer count = couponMapper.selectCount(wrapper);
        List<CouponDO> couponDOList = couponMapper.selectPage(new RowBounds((pageNo-1)*limit,limit), wrapper);
        Page<CouponDO> page = new Page<>(couponDOList,pageNo,limit,count);
        return page;
    }
}
