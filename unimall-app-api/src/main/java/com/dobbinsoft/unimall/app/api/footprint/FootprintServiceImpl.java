package com.dobbinsoft.unimall.app.api.footprint;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.domain.SpuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午8:58
 */
@Service
public class FootprintServiceImpl implements FootprintService {

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    public boolean delete(Long userId, Long spuId) throws ServiceException {
        cacheComponent.delZSet(CacheConst.FOOTPRINT_LRU + userId, "P" + spuId);
        return deleteAll(userId);
    }

    @Override
    public boolean deleteAll(Long userId) throws ServiceException {
        cacheComponent.del(CacheConst.FOOTPRINT_LRU + userId);
        return true;
    }

    @Override
    public List<SpuDO> list(Long userId) throws ServiceException {
        Set<String> spuIdsSet = cacheComponent.getZSetLruTopN(CacheConst.FOOTPRINT_LRU + userId, 30);
        return spuIdsSet.stream().map(item ->
                cacheComponent.getHashObj(CacheConst.PRT_SPU_HASH_BUCKET, item, SpuDO.class)).filter(item -> item != null).collect(Collectors.toList());
    }
}
