package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.dobbinsoft.fw.support.annotation.cache.CacheAssemble;
import com.dobbinsoft.fw.support.annotation.cache.CacheHashPut;
import com.dobbinsoft.fw.support.annotation.cache.CacheKeyPut;
import com.dobbinsoft.fw.support.mapper.IMapper;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.AdvertDO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:38
 */
public interface AdvertMapper extends IMapper<AdvertDO> {

    @Override
    @CacheAssemble(key = "'" + CacheConst.ADVERT_TYPE + "' + #queryWrapper.type", arrayClass = AdvertDO.class)
    List<AdvertDO> selectList(Wrapper<AdvertDO> queryWrapper);

}
