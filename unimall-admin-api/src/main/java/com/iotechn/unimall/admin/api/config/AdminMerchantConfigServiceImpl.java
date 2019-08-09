package com.iotechn.unimall.admin.api.config;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.biz.service.config.ConfigBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.ConfigDO;
import com.iotechn.unimall.data.dto.ConfigDTO;
import com.iotechn.unimall.data.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-20
 * Time: 上午10:47
 */
@Service
public class AdminMerchantConfigServiceImpl implements AdminMerchantConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private ConfigBizService configBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addMerchant(Long adminId, String title, String logoUrl, String description, String address, Integer showType) throws ServiceException {
        Date now = new Date();
        ConfigDO titleDO = new ConfigDO("title",title);
            titleDO.setGmtCreate(now);titleDO.setGmtUpdate(now);
        ConfigDO logoDO = new ConfigDO("logoUrl",logoUrl);
            logoDO.setGmtCreate(now);logoDO.setGmtUpdate(now);
        ConfigDO descDO = new ConfigDO("description",description);
            descDO.setGmtCreate(now);descDO.setGmtUpdate(now);
        ConfigDO addressDO = new ConfigDO("address",address);
            addressDO.setGmtCreate(now);addressDO.setGmtUpdate(now);
        ConfigDO showTypeDO = new ConfigDO("showType",String.valueOf(showType));
            showTypeDO.setGmtCreate(now);showTypeDO.setGmtUpdate(now);
        configMapper.insert(titleDO);
        configMapper.insert(logoDO);
        configMapper.insert(descDO);
        configMapper.insert(addressDO);
        configMapper.insert(showTypeDO);
        configBizService.clearMerchantConfigCache();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMerchant(Long adminId, String title, String logoUrl, String description, String address, Integer showType) throws ServiceException {
        configMapper.update(new ConfigDO("title",title), new EntityWrapper<ConfigDO>().eq("key_word","title"));

        configMapper.update(new ConfigDO("logoUrl",logoUrl), new EntityWrapper<ConfigDO>().eq("key_word","logoUrl"));

        configMapper.update(new ConfigDO("description",description), new EntityWrapper<ConfigDO>().eq("key_word","description"));

        configMapper.update(new ConfigDO("address",address), new EntityWrapper<ConfigDO>().eq("key_word","address"));

        configMapper.update(new ConfigDO("showType",String.valueOf(showType)), new EntityWrapper<ConfigDO>().eq("key_word","showType"));

        configBizService.clearMerchantConfigCache();
        return true;
    }

    @Override
    public ConfigDTO getMerchant(Long adminId) throws ServiceException {
        return configBizService.getMerchantConfig();
    }
}
