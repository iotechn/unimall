package com.iotechn.unimall.app.api.goods;

import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.mapper.SpuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rize on 2019/7/8.
 */
@Service
public class GoodsBizService {

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private SpuMapper spuMapper;

    private static final String CA_SPU_HASH = "CA_SPU_HASH";

    public SpuDO getSpuById(Long spuId) throws ServiceException {
        SpuDO objFromCache = cacheComponent.getHashObj(CA_SPU_HASH, "S" + spuId, SpuDO.class);
        if (objFromCache != null) {
            return objFromCache;
        }
        SpuDO spuDO = spuMapper.selectById(spuId);
        if (spuDO == null) {
            throw new AppServiceException(AppExceptionDefinition.GOODS_NOT_EXIST);
        }
        cacheComponent.putHashObj(CA_SPU_HASH, "S" + spuDO, spuDO, Const.CACHE_ONE_DAY);
        return spuDO;
    }

}
