package com.iotechn.unimall.biz.service.footprint;

import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午10:15
 */
@Service
public class FootprintBizService {

    @Autowired
    private CacheComponent cacheComponent;

    public void newFootprint(Long spuId, Long userId) throws ServiceException {
        cacheComponent.putZSetLru(CacheConst.FOOTPRINT_LRU + userId, "P" + spuId, 30, 10);
    }
}
