package com.iotechn.unimall.app.api.advertisement;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdvertisementDO;
import com.iotechn.unimall.data.enums.AdvertisementType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.AdvertisementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:36
 */
@Service
public class AppAdvertisementServiceImpl implements AppAdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;
    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public List<AdvertisementDO> getActiveAd(Integer adType) throws ServiceException {

        List<AdvertisementDO> advertisementDOList
                = cacheComponent.getObjList(AdvertisementType.ADVERTISEMENT_NAME + Integer.toString(adType), AdvertisementDO.class);

        if(CollectionUtils.isEmpty(advertisementDOList)){

            advertisementDOList = advertisementMapper.selectList(new EntityWrapper<AdvertisementDO>()
                    .eq("ad_type",adType)
                    .eq("status", StatusType.ACTIVE.getCode()));
            cacheComponent.putObj(AdvertisementType.ADVERTISEMENT_NAME + Integer.toString(adType),advertisementDOList,100);
        }
        return advertisementDOList;
    }

}
