package com.iotechn.unimall.admin.api.advertisement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdvertisementDO;
import com.iotechn.unimall.data.mapper.AdvertisementMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午9:24
 */
@Service
public class AdminAdvertisementServiceImpl implements AdminAdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;
    @Autowired
    private CacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAdvertisement(Long adminId, Integer adType, String title, String url, String imgUrl, Integer status,String color) throws ServiceException {

        Date now = new Date();
        AdvertisementDO advertisementDO = new AdvertisementDO(adType,title,url,imgUrl,status,color);
        advertisementDO.setGmtCreate(now);
        advertisementDO.setGmtUpdate(now);

        if(advertisementMapper.insert(advertisementDO) > 0){
            cacheComponent.delPrefixKey(CacheConst.ADVERTISEMENT_TYPE + adType);
            return true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_ADD_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAdvertisement(Long adminId,Integer adType, Long adId) throws ServiceException {

        if(advertisementMapper.delete(new QueryWrapper<AdvertisementDO>()
                .eq("id",adId)
                .eq("ad_type",adType)) > 0){
            cacheComponent.delPrefixKey(CacheConst.ADVERTISEMENT_TYPE + adType);
            return true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_DELETE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateAdvertisement(Long adminId,Long adId, Integer adType, String title, String url, String imgUrl, Integer status,String color) throws ServiceException {
        AdvertisementDO advertisementDO = new AdvertisementDO(adType,title,url,imgUrl,status,color);
        advertisementDO.setId(adId);
        advertisementDO.setGmtUpdate(new Date());
        if(advertisementMapper.updateById(advertisementDO)>0){
            cacheComponent.delPrefixKey(CacheConst.ADVERTISEMENT_TYPE + adType);
            return  true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_UPDATE_FAILED);
    }

    @Override
    public Page<AdvertisementDO> queryAdvertisement(Long adminId, Integer adType, Integer pageNo, Integer limit, Integer status) throws ServiceException {
        QueryWrapper<AdvertisementDO> wrapper = new QueryWrapper<AdvertisementDO>();
        if (adType != null) {
            wrapper.eq("ad_type", adType);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }

        List<AdvertisementDO> advertisementDOList = null;// TODO advertisementMapper.selectPage(new RowBounds(limit *(pageNo - 1), limit),wrapper);
        Integer count = advertisementMapper.selectCount(wrapper);

        Page<AdvertisementDO> page = new Page<>(advertisementDOList,pageNo, limit,count);

        return page;

    }
}
