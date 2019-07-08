package com.iotechn.unimall.admin.api.advertisement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.admin.exception.AdminExceptionDefinition;
import com.iotechn.unimall.admin.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdvertisementDO;
import com.iotechn.unimall.data.enums.AdvertisementType;
import com.iotechn.unimall.data.mapper.AdvertisementMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Boolean addAdvertisement(Long adminId, Integer adType, String title, String url, String imgUrl, Integer status) throws ServiceException {

        Date now = new Date();
        AdvertisementDO advertisementDO = new AdvertisementDO(adType,title,url,imgUrl,status);
        advertisementDO.setGmtCreate(now);
        advertisementDO.setGmtUpdate(now);

        if(advertisementMapper.insert(advertisementDO) > 0){
            cacheComponent.del(AdvertisementType.ADVERTISEMENT_NAME+adType.toString());
            return true;
        }
        throw new AdminServiceException(AdminExceptionDefinition.ADVERTISEMENT_SQL_ADD_FAILED);
    }

    @Override
    public Boolean deleteAdvertisement(Long adminId,Integer adType, Long adId) throws ServiceException {

        if(advertisementMapper.delete(new EntityWrapper<AdvertisementDO>()
                .eq("id",adId)
                .eq("ad_type",adType)) > 0){
            cacheComponent.del(AdvertisementType.ADVERTISEMENT_NAME+adType.toString());
            return true;
        }
        throw new AdminServiceException(AdminExceptionDefinition.ADVERTISEMENT_SQL_DELETE_FAILED);
    }

    @Override
    public Boolean updateAdvertisement(Long adminId,Long adId, Integer adType, String title, String url, String imgUrl, Integer status) throws ServiceException {
        AdvertisementDO advertisementDO = new AdvertisementDO(adType,title,url,imgUrl,status);
        advertisementDO.setId(adId);
        advertisementDO.setGmtUpdate(new Date());
        if(advertisementMapper.updateById(advertisementDO)>0){
            cacheComponent.del(AdvertisementType.ADVERTISEMENT_NAME+adType.toString());
            return  true;
        }
        throw new AdminServiceException(AdminExceptionDefinition.ADVERTISEMENT_SQL_UPDATE_FAILED);
    }

    @Override
    public Page<AdvertisementDO> queryAdvertisement(Long adminId, Integer adType, Integer pageNo, Integer pageSize, Integer status) throws ServiceException {

        List<AdvertisementDO> advertisementDOList = advertisementMapper.getAdvertisementByTypeAndStatus(adType,status,pageSize*(pageNo - 1),pageSize);
        Integer count = advertisementMapper.selectCount(new EntityWrapper<AdvertisementDO>()
                .eq("status",status)
        .eq("ad_type",adType));

        Page<AdvertisementDO> page = new Page<>(advertisementDOList,pageNo,pageSize,count);

        return page;

    }

    @Override
    public Page<AdvertisementDO> queryAllAdvertisement(Long adminId,Integer pageNo,Integer pageSize) throws ServiceException {

        List<AdvertisementDO> advertisementDOList = advertisementMapper.getAllAdvertisement(pageSize*(pageNo-1),pageSize);
        Integer count = advertisementMapper.selectCount(null);
        Page<AdvertisementDO> page = new Page<>(advertisementDOList,pageNo,pageSize,count);
        return page;

    }
}
