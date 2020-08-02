package com.iotechn.unimall.admin.api.advert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdvertDO;
import com.iotechn.unimall.data.mapper.AdvertMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午9:24
 */
@Service
public class AdminAdvertServiceImpl implements AdminAdvertService {

    @Autowired
    private AdvertMapper advertMapper;
    @Autowired
    private CacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(Long adminId, Integer adType, String title, String url, String imgUrl, Integer status, String color) throws ServiceException {

        Date now = new Date();
        AdvertDO advertDO = new AdvertDO(adType,title,url,imgUrl,status,color);
        advertDO.setGmtCreate(now);
        advertDO.setGmtUpdate(now);

        if(advertMapper.insert(advertDO) > 0){
            cacheComponent.delPrefixKey(CacheConst.ADVERT_TYPE + adType);
            return true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_ADD_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long adminId, Integer adType, Long adId) throws ServiceException {

        if(advertMapper.delete(new QueryWrapper<AdvertDO>()
                .eq("id",adId)
                .eq("ad_type",adType)) > 0){
            cacheComponent.delPrefixKey(CacheConst.ADVERT_TYPE + adType);
            return true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_DELETE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(Long adminId, Long adId, Integer adType, String title, String url, String imgUrl, Integer status, String color) throws ServiceException {
        AdvertDO advertDO = new AdvertDO(adType,title,url,imgUrl,status,color);
        advertDO.setId(adId);
        advertDO.setGmtUpdate(new Date());
        if(advertMapper.updateById(advertDO)>0){
            cacheComponent.delPrefixKey(CacheConst.ADVERT_TYPE + adType);
            return  true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_UPDATE_FAILED);
    }

    @Override
    public Page<AdvertDO> list(Long adminId, Integer adType, Integer page, Integer limit, Integer status) throws ServiceException {
        QueryWrapper<AdvertDO> wrapper = new QueryWrapper<AdvertDO>();
        if (adType != null) {
            wrapper.eq("ad_type", adType);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        return advertMapper.selectPage(Page.div(page,limit, AdvertDO.class),wrapper);
    }
}
