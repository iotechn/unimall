package com.iotechn.unimall.biz.service.location;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.domain.LocationDO;
import com.iotechn.unimall.data.domain.LocationSkuDO;
import com.iotechn.unimall.data.domain.LocationSpuDO;
import com.iotechn.unimall.data.enums.SpuStatusType;
import com.iotechn.unimall.data.mapper.LocationMapper;
import com.iotechn.unimall.data.mapper.LocationSkuMapper;
import com.iotechn.unimall.data.mapper.LocationSpuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationBizService {

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private LocationSkuMapper locationSkuMapper;

    @Autowired
    private LocationSpuMapper locationSpuMapper;

    /**
     * 为新建的SKU，初始化库存信息
     * @param spuId
     * @param skuId
     */
    public void initSku(Long spuId, Long skuId) {
        List<LocationDO> locationOfAll = locationMapper.selectList(new QueryWrapper<>());
        for (LocationDO locationDO : locationOfAll) {
            LocationSkuDO locationSkuDO = new LocationSkuDO();
            locationSkuDO.setLocationId(locationDO.getId());
            locationSkuDO.setSpuId(spuId);
            locationSkuDO.setSkuId(skuId);
            locationSkuDO.setStock(0);
            locationSkuMapper.insert(locationSkuDO);
        }
    }

    /**
     * 为新建的SPU，初始化必要数据
     * @param spuId
     */
    public void initSpu(Long spuId) {
        List<LocationDO> locationOfAll = locationMapper.selectList(new QueryWrapper<>());
        for (LocationDO locationDO : locationOfAll) {
            LocationSpuDO locationSpuDO = new LocationSpuDO();
            locationSpuDO.setLocationId(locationDO.getId());
            locationSpuDO.setSpuId(spuId);
            locationSpuDO.setSales(0);
            locationSpuDO.setStatus(SpuStatusType.SELLING.getCode());
            locationSpuMapper.insert(locationSpuDO);
        }
    }

}
