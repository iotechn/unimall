package com.iotechn.unimall.biz.service.footpring;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

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
