package com.iotechn.unimall.biz.service.config;

import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.ConfigDO;
import com.iotechn.unimall.data.dto.ConfigDTO;
import com.iotechn.unimall.data.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rize on 2019/7/21.
 */
@Service
public class ConfigBizService {

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private CacheComponent cacheComponent;

    private static final String CA_CONFIG_DTO_KEY = "CA_CONFIG_DTO_KEY";

    public ConfigDTO getMerchantConfig() {
        ConfigDTO obj = cacheComponent.getObj(CA_CONFIG_DTO_KEY, ConfigDTO.class);
        if (obj != null) {
            return obj;
        }
        List<ConfigDO> list = configMapper.selectList(null);
        ConfigDTO configDTO = new ConfigDTO();
        for(ConfigDO configDO : list){
            switch (configDO.getKeyWord()){
                case "title": configDTO.setTitle(configDO.getValueWorth());break;
                case "logoUrl":configDTO.setLogoUrl(configDO.getValueWorth());break;
                case "description":configDTO.setDescription(configDO.getValueWorth());break;
                case "address":configDTO.setAddress(configDO.getValueWorth());break;
                case "showType":configDTO.setShowType(Integer.parseInt(configDO.getValueWorth()));break;
                case "status":configDTO.setStatus(Integer.parseInt(configDO.getValueWorth()));break;
            }
        }
        cacheComponent.putObj(CA_CONFIG_DTO_KEY, configDTO, Const.CACHE_ONE_DAY);
        return configDTO;
    }

    public void clearMerchantConfigCache() {
        cacheComponent.del(CA_CONFIG_DTO_KEY);
    }
}
