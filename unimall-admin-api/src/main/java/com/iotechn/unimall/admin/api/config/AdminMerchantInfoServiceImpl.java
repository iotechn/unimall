package com.iotechn.unimall.admin.api.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.service.config.MerchantInfoBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.MerchantInfoDO;
import com.iotechn.unimall.data.dto.MerchantInfoDTO;
import com.iotechn.unimall.data.mapper.MerchantInfoMapper;
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
public class AdminMerchantInfoServiceImpl implements AdminMerchantInfoService {

    @Autowired
    private MerchantInfoBizService merchantInfoBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createOrUpdate(Long adminId, String title, String logoUrl, String description, String address, Integer showType) throws ServiceException {
        merchantInfoBizService.createOrUpdate("title",title);
        merchantInfoBizService.createOrUpdate("logoUrl",logoUrl);
        merchantInfoBizService.createOrUpdate("description",description);
        merchantInfoBizService.createOrUpdate("address",address);
        merchantInfoBizService.createOrUpdate("showType",String.valueOf(showType));
        merchantInfoBizService.clearMerchantInfoCache();
        return true;
    }

    @Override
    public MerchantInfoDTO list(Long adminId) throws ServiceException {
        return merchantInfoBizService.getMerchantInfo();
    }
}
