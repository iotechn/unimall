package com.dobbinsoft.unimall.app.api.advert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.annotation.AspectCommonCache;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.domain.AdvertDO;
import com.dobbinsoft.unimall.data.enums.StatusType;
import com.dobbinsoft.unimall.data.mapper.AdvertMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:36
 */
@Service
public class AdvertServiceImpl implements AdvertService {

    @Autowired
    private AdvertMapper advertMapper;

    @Override
    @AspectCommonCache(value = CacheConst.ADVERT_TYPE, argIndex = {0}, second = 100, arrayClass = AdvertDO.class)
    public List<AdvertDO> getActiveAd(Integer adType) throws ServiceException {
        QueryWrapper<AdvertDO> wrapper = new QueryWrapper<AdvertDO>()
                .eq("status", StatusType.ACTIVE.getCode());
        if (adType != null) {
            wrapper.eq("type", adType);
        }
        return advertMapper.selectList(wrapper);
    }

}
