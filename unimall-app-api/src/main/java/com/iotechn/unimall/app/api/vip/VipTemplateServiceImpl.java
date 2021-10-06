package com.iotechn.unimall.app.api.vip;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.annotation.AspectCommonCache;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.VipTemplateDO;
import com.iotechn.unimall.data.mapper.VipTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VipTemplateServiceImpl implements VipTemplateService {

    @Autowired
    private VipTemplateMapper mapper;

    @Override
    @AspectCommonCache(value = CacheConst.VIP_TEMPLATE_LIST, arrayClass = VipTemplateDO.class)
    public List<VipTemplateDO> list() throws ServiceException {
        QueryWrapper<VipTemplateDO> wrapper = new QueryWrapper<>();
        wrapper.eq("display", 1);
        return mapper.selectList(wrapper);
    }
}
