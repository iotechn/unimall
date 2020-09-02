package com.iotechn.unimall.admin.api.freight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
import com.iotechn.unimall.data.domain.FreightTemplateDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import com.iotechn.unimall.data.mapper.FreightTemplateCarriageMapper;
import com.iotechn.unimall.data.mapper.FreightTemplateMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午4:25
 */
@Service
public class AdminFreightTemplateServiceImpl implements AdminFreightTemplateService {

    @Autowired
    private FreightTemplateMapper templateMapper;

    @Autowired
    private FreightTemplateCarriageMapper carriageMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private SpuMapper spuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(String title, String spuLocation, Integer deliveryDeadline, Integer defaultFreePrice, Integer defaultFirstWeight, Integer defaultFirstPrice, Integer defaultContinueWeight, Integer defaultContinuePrice, List carriageDOList, Long adminId) throws ServiceException {
        FreightTemplateDO freightTemplateDO = newFreightTemplateDO(null,title,spuLocation,deliveryDeadline,defaultFreePrice,defaultFirstWeight,defaultFirstPrice,defaultContinueWeight,defaultContinuePrice);

        if (templateMapper.insert(freightTemplateDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.FREIGHT_TEMPLATE_INSERT_FAILED);
        }
        insertCarriage(freightTemplateDO, carriageDOList);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id, Long adminId) throws ServiceException {
        if (spuMapper.selectCount(new QueryWrapper<SpuDO>().eq("freight_template_id", id)) > 0) {
            throw new AdminServiceException(ExceptionDefinition.FREIGHT_SPU_QUERY_HAS);
        }
        if (templateMapper.delete(new QueryWrapper<FreightTemplateDO>().eq("id", id)) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.FREIGHT_TEMPLATE_DELETE_FAILED);
        }
        if (carriageMapper.delete(
                new QueryWrapper<FreightTemplateCarriageDO>()
                        .eq("template_id", id)) >= 0) {

            clearCache(id);
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.FREIGHT_TEMPLATE_UPDATE_FAILED);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(Long id, String title, String spuLocation, Integer deliveryDeadline, Integer defaultFreePrice, Integer defaultFirstWeight, Integer defaultFirstPrice, Integer defaultContinueWeight, Integer defaultContinuePrice, List carriageDOList, Long adminId) throws ServiceException {

        FreightTemplateDO freightTemplateDO = newFreightTemplateDO(id,title,spuLocation,deliveryDeadline,defaultFreePrice,defaultFirstWeight,defaultFirstPrice,defaultContinueWeight,defaultContinuePrice);
        if (templateMapper.updateById(freightTemplateDO) <= 0) {    //如果主表修改失败
            throw new AdminServiceException(ExceptionDefinition.FREIGHT_TEMPLATE_UPDATE_FAILED);
        }
        carriageMapper.delete(new QueryWrapper<FreightTemplateCarriageDO>().eq("template_id", id));
        insertCarriage(freightTemplateDO, carriageDOList);
        clearCache(id);
        return "ok";
    }

    @Override
    public List<FreightTemplateDTO> list(Long adminId) throws ServiceException {
        List<FreightTemplateDO> freightTemplateDOList = templateMapper.selectList(null); //查出主表所有数据
        if(CollectionUtils.isEmpty(freightTemplateDOList)){
            return new ArrayList<>();
        }

        List<FreightTemplateDTO> template_id = freightTemplateDOList.stream().map(item -> {
            FreightTemplateDTO freightTemplateDTO = new FreightTemplateDTO();
            List<FreightTemplateCarriageDO> freightTemplateCarriageDOList = carriageMapper.selectList(new QueryWrapper<FreightTemplateCarriageDO>()
                    .eq("template_id", item.getId()));
            BeanUtils.copyProperties(item, freightTemplateDTO);
            freightTemplateDTO.setCarriageDOList(freightTemplateCarriageDOList);
            return freightTemplateDTO;
        }).collect(Collectors.toList());
        return template_id;
    }

    /**
     * 将传入的特殊地区邮费计算插入表中
     * @param freightTemplateDO
     * @param carriageDOList
     * @return
     * @throws ServiceException
     */
    private boolean insertCarriage(FreightTemplateDO freightTemplateDO, List carriageDOList) throws ServiceException {
        Date now = new Date();
        if (CollectionUtils.isEmpty(carriageDOList)) {
            return true;
        }
        List<FreightTemplateCarriageDO> collect = (List<FreightTemplateCarriageDO>) carriageDOList.stream().map(item -> {
            FreightTemplateCarriageDO t = JSONObject.toJavaObject((JSON) item, FreightTemplateCarriageDO.class);
            return t;
        }).collect(Collectors.toList());
        //表中设定可默认值，所以就不检查是否为空
        for (FreightTemplateCarriageDO carriageDO : collect) {
            carriageDO.setTemplateId(freightTemplateDO.getId());
            carriageDO.setGmtCreate(now);
            carriageDO.setGmtUpdate(now);
            if (carriageMapper.insert(carriageDO) <= 0) {
                throw new AdminServiceException(ExceptionDefinition.FREIGHT_CARRIAGE_INSERT_FAILED);
            }
        }
        return true;
    }

    private FreightTemplateDO newFreightTemplateDO(Long id, String title, String spuLocation, Integer deliveryDeadline, Integer defaultFreePrice, Integer defaultFirstWeight, Integer defaultFirstPrice, Integer defaultContinueWeight, Integer defaultContinuePrice){
        FreightTemplateDO freightTemplateDO = new FreightTemplateDO();
        Date now = new Date();
        freightTemplateDO.setTitle(title);
        freightTemplateDO.setSpuLocation(spuLocation);
        freightTemplateDO.setDeliveryDeadline(deliveryDeadline);
        freightTemplateDO.setDefaultFreePrice(defaultFreePrice);
        freightTemplateDO.setDefaultFirstWeight(defaultFirstWeight);
        freightTemplateDO.setDefaultFirstPrice(defaultFirstPrice);
        freightTemplateDO.setDefaultContinueWeight(defaultContinueWeight);
        freightTemplateDO.setDefaultContinuePrice(defaultContinuePrice);
        freightTemplateDO.setGmtUpdate(now);

        if(id != null){
            freightTemplateDO.setId(id);
        }else {
            freightTemplateDO.setGmtCreate(now);
        }
        return freightTemplateDO;
    }

    /**
     * 清楚缓存
     */
    private void clearCache(Long id){
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                cacheComponent.del(CacheConst.FREIGHT_TEMPLATE + id);
            }
        });
    }


}
