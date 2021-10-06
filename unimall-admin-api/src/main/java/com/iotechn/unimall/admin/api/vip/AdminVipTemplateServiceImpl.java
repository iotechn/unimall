package com.iotechn.unimall.admin.api.vip;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.VipTemplateDO;
import com.iotechn.unimall.data.mapper.VipTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class AdminVipTemplateServiceImpl implements AdminVipTemplateService {

    @Autowired
    private VipTemplateMapper vipTemplateMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VipTemplateDO create(String title, String description, Integer dayNum, Integer originalPrice, Integer price, Integer display, Long adminId) throws ServiceException {
        VipTemplateDO vipTemplateDO = new VipTemplateDO();
        vipTemplateDO.setTitle(title);
        vipTemplateDO.setDescription(description);
        vipTemplateDO.setDayNum(dayNum);
        vipTemplateDO.setOriginalPrice(originalPrice);
        vipTemplateDO.setPrice(price);
        vipTemplateDO.setDisplay(display);
        vipTemplateMapper.insert(vipTemplateDO);
        cleanCache();
        return vipTemplateDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id, Long adminId) throws ServiceException {
        vipTemplateMapper.deleteById(id);
        cleanCache();
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public VipTemplateDO edit(Long id, String title, String description, Integer dayNum, Integer originalPrice, Integer price, Integer display, Long adminId) throws ServiceException {
        VipTemplateDO vipTemplateDO = vipTemplateMapper.selectById(id);
        vipTemplateDO.setTitle(title);
        vipTemplateDO.setDescription(description);
        vipTemplateDO.setDayNum(dayNum);
        vipTemplateDO.setOriginalPrice(originalPrice);
        vipTemplateDO.setPrice(price);
        vipTemplateDO.setDisplay(display);
        vipTemplateMapper.updateById(vipTemplateDO);
        cleanCache();
        return vipTemplateDO;
    }

    @Override
    public Page<VipTemplateDO> list(String title, Integer display, Integer pageNo, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<VipTemplateDO> wrapper = new QueryWrapper<>();
        if (display != null) {
            wrapper.eq("display", display);
        }
        if (title != null) {
            wrapper.like("title", title);
        }
        return vipTemplateMapper.selectPage(Page.div(pageNo, limit, VipTemplateDO.class), wrapper);
    }

    private void cleanCache() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                cacheComponent.del(CacheConst.VIP_TEMPLATE_LIST);
            }
        });
    }

}
