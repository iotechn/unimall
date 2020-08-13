package com.iotechn.unimall.app.api.config;

import com.iotechn.unimall.biz.service.config.MerchantInfoBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.MerchantInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rize on 2019/7/21.
 */
@Service
public class ConfigServiceImpl implements ConfigService{

    @Autowired
    private MerchantInfoBizService merchantInfoBizService;

    @Override
    public MerchantInfoDTO getMerchantConfig() throws ServiceException {
        return merchantInfoBizService.getMerchantInfo();
    }
}
