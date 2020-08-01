package com.iotechn.unimall.app.api.advertisement;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.annotaion.AspectCommonCache;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdvertisementDO;
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
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementMapper advertisementMapper;

    @Override
    @AspectCommonCache(value = CacheConst.ADVERTISEMENT_TYPE, argIndex = {0}, second = 100)
    public List<AdvertisementDO> getActiveAd(Integer adType) throws ServiceException {
        QueryWrapper<AdvertisementDO> wrapper = new QueryWrapper<AdvertisementDO>()
                .eq("status", StatusType.ACTIVE.getCode());
        if (adType != null) {
            wrapper.eq("ad_type", adType);
        }
        List<AdvertisementDO> advertisementDOList = advertisementMapper.selectList(wrapper);
        return advertisementDOList;
    }

}
