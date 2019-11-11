package com.iotechn.unimall.admin.api.advertisement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdvertisementDO;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.enums.AdvertisementType;
import com.iotechn.unimall.data.mapper.AdvertisementMapper;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    private  final  static String  ADVERTISEMENT_NAME = "ADVERTISEMENT_TYPE_";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addAdvertisement(Long adminId, Integer adType, String title, String url, String imgUrl, Integer status,String color) throws ServiceException {

        Date now = new Date();
        AdvertisementDO advertisementDO = new AdvertisementDO(adType,title,url,imgUrl,status,color);
        advertisementDO.setGmtCreate(now);
        advertisementDO.setGmtUpdate(now);

        if(advertisementMapper.insert(advertisementDO) > 0){
            cacheComponent.delPrefixKey(ADVERTISEMENT_NAME);
            return true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_ADD_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteAdvertisement(Long adminId,Integer adType, Long adId) throws ServiceException {

        if(advertisementMapper.delete(new EntityWrapper<AdvertisementDO>()
                .eq("id",adId)
                .eq("ad_type",adType)) > 0){
            cacheComponent.delPrefixKey(ADVERTISEMENT_NAME);
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
            cacheComponent.delPrefixKey(ADVERTISEMENT_NAME);
            return  true;
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_UPDATE_FAILED);
    }

    @Override
    public Page<AdvertisementDO> queryAdvertisement(Long adminId, Integer adType, Integer pageNo, Integer limit, Integer status) throws ServiceException {
        Wrapper<AdvertisementDO> wrapper = new EntityWrapper<AdvertisementDO>();
        if (adType != null) {
            wrapper.eq("ad_type", adType);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }

        List<AdvertisementDO> advertisementDOList = advertisementMapper.selectPage(new RowBounds(limit *(pageNo - 1), limit),wrapper);
        Integer count = advertisementMapper.selectCount(wrapper);

        Page<AdvertisementDO> page = new Page<>(advertisementDOList,pageNo, limit,count);

        return page;

    }

    //冗余，未使用
    @Override
    public Page<AdvertisementDO> queryAllAdvertisement(Long adminId,Integer pageNo,Integer pageSize) throws ServiceException {

        List<AdvertisementDO> advertisementDOList = advertisementMapper.getAllAdvertisement(pageSize*(pageNo-1),pageSize);
        Integer count = advertisementMapper.selectCount(null);
        Page<AdvertisementDO> page = new Page<>(advertisementDOList,pageNo,pageSize,count);
        return page;

    }
}
