package com.iotechn.unimall.app.api.footprint;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.SpuDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //TODO 前端没有了单个删除足迹的功能,需要修改
    @Override
    public boolean deleteFootprint(Long userId, Long spuId) throws ServiceException {
        cacheComponent.delZSet(CacheConst.FOOTPRINT_LRU + userId, "P" + spuId);
        return deleteAllFootprint(userId);
    }

    @Override
    public boolean deleteAllFootprint(Long userId) throws ServiceException {
        cacheComponent.del(CacheConst.FOOTPRINT_LRU + userId);
        return true;
    }

    @Override
    public List<SpuDO> getAllFootprint(Long userId) throws ServiceException {
        Set<String> spuIdsSet = cacheComponent.getZSetLruTopN(CacheConst.FOOTPRINT_LRU + userId, 30);
        return spuIdsSet.stream().map(item ->
                cacheComponent.getHashObj(CacheConst.PRT_SPU_HASH_BUCKET, item, SpuDO.class)).filter(item -> item != null).collect(Collectors.toList());
    }
}
