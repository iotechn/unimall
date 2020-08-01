package com.iotechn.unimall.biz.service.footpring;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.FootprintDO;
import com.iotechn.unimall.data.mapper.FootprintMapper;
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

    public boolean addFootPrint(Long userId,String spuId,String spuTitle,String spuPrice,String spuImg){
        if(userId != null && userId != 0l){
            cacheComponent.putSetRaw(CacheConst.FOOTPRINT_USER + userId,"spuId:" + spuId, Const.CACHE_ONE_YEAR);
            cacheComponent.putSetRaw(CacheConst.FOOTPRINT_USER + userId,"spuTitle:" + spuId + ":" + spuTitle, Const.CACHE_ONE_YEAR);
            cacheComponent.putSetRaw(CacheConst.FOOTPRINT_USER + userId,"spuPrice:" + spuId + ":" + spuPrice, Const.CACHE_ONE_YEAR);
            cacheComponent.putSetRaw(CacheConst.FOOTPRINT_USER + userId,"spuImg:" + spuId + ":" + spuImg, Const.CACHE_ONE_YEAR);
        }
        return true;
    }
}
