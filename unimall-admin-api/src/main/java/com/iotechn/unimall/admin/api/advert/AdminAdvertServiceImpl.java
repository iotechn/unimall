package com.iotechn.unimall.admin.api.advert;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AdvertDO;
import com.iotechn.unimall.data.mapper.AdvertMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午9:24
 */
@Service("adminAdvertService")
public class AdminAdvertServiceImpl implements AdminAdvertService {

    @Autowired
    private AdvertMapper advertMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(Integer type, Integer unionType, String title, String unionValue, String imgUrl, Integer status, String color, Long adminId) throws ServiceException {
        Date now = new Date();
        AdvertDO advertDO = new AdvertDO();
        advertDO.setType(type);
        advertDO.setTitle(title);
        advertDO.setUnionType(unionType);
        advertDO.setUnionValue(unionValue);
        advertDO.setImgUrl(imgUrl);
        advertDO.setStatus(status);
        advertDO.setColor(color);
        advertDO.setGmtCreate(now);
        advertDO.setGmtUpdate(now);
        if (advertMapper.insert(advertDO) > 0) {
            this.clearCache(type);
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_ADD_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Integer adType, Long adId, Long adminId) throws ServiceException {
        if (advertMapper.delete(new QueryWrapper<AdvertDO>()
                .eq("id", adId)
                .eq("type", adType)) > 0) {
            this.clearCache(adType);
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_DELETE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(Long adId, Integer type, Integer unionType, String title, String unionValue, String imgUrl, Integer status, String color, Long adminId) throws ServiceException {
        AdvertDO advertDO = new AdvertDO();
        advertDO.setId(adId);
        advertDO.setType(type);
        advertDO.setUnionType(unionType);
        advertDO.setTitle(title);
        advertDO.setUnionValue(unionValue);
        advertDO.setImgUrl(imgUrl);
        advertDO.setStatus(status);
        advertDO.setColor(color);
        advertDO.setGmtUpdate(new Date());
        if (advertMapper.updateById(advertDO) > 0) {
            this.clearCache(type);
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADVERTISEMENT_SQL_UPDATE_FAILED);
    }

    @Override
    public Page<AdvertDO> list(Long adminId, Integer adType, Integer page, Integer limit, Integer status) throws ServiceException {
        QueryWrapper<AdvertDO> wrapper = new QueryWrapper<AdvertDO>();
        if (adType != null) {
            wrapper.eq("type", adType);
        }
        if (status != null) {
            wrapper.eq("status", status);
        }
        return advertMapper.selectPage(Page.div(page, limit, AdvertDO.class), wrapper);
    }

    public void clearCache(Integer adType) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                cacheComponent.del(CacheConst.ADVERT_TYPE + null);
                cacheComponent.del(CacheConst.ADVERT_TYPE + adType);
                cacheComponent.del(CacheConst.INTEGRAL_INDEX);
            }
        });
    }
}
