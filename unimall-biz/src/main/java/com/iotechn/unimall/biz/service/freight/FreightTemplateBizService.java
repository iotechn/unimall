package com.iotechn.unimall.biz.service.freight;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.core.exception.BizServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
import com.iotechn.unimall.data.domain.FreightTemplateDO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import com.iotechn.unimall.data.dto.freight.ShipTraceDTO;
import com.iotechn.unimall.data.mapper.FreightTemplateCarriageMapper;
import com.iotechn.unimall.data.mapper.FreightTemplateMapper;
import com.iotechn.unimall.data.model.FreightCalcModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午7:48
 */
@Service
public class FreightTemplateBizService {

    @Autowired
    private FreightTemplateMapper templateMapper;

    @Autowired
    private FreightTemplateCarriageMapper carriageMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private ShipTraceQuery shipTraceQuery;

    public int computePostage(FreightCalcModel freightCalcModel) throws ServiceException {
        List<FreightCalcModel.FreightAndWeight> freightAndWeights = freightCalcModel.getFreightAndWeights();
        if(CollectionUtils.isEmpty(freightAndWeights)){
            throw new BizServiceException(ExceptionDefinition.FREIGHT_PARAM_CHECK_FAILED);
        }
        HashSet<Long> templateIds = freightAndWeights.stream().map(item ->{
            return item.getId();
        }).collect(Collectors.toCollection(HashSet::new));

         // 统计每个模板下的商品重量和，key为模板ID
        Map<Long, Integer> weightMap = templateIds.stream().collect(Collectors.toMap(key -> key, value -> 0));
        // 统计每个模板下的商品价格和，key为模板ID
        Map<Long, Integer> priceMap = templateIds.stream().collect(Collectors.toMap(key -> key, value -> 0));

        // 计算每个模板的商品重量和购买商品价格
        for (FreightCalcModel.FreightAndWeight freightAndWeight : freightAndWeights) {
            weightMap.put(freightAndWeight.getId(),freightAndWeight.getWeight() + weightMap.get(freightAndWeight.getId()));
            priceMap.put(freightAndWeight.getId(),freightAndWeight.getPrice() + priceMap.get(freightAndWeight.getId()));
        }

        int postage = 0;
        for (Long templateId : templateIds) {
            FreightTemplateDTO freightTemplateById = getFreightTemplateById(templateId);
            Integer weight = weightMap.get(templateId);
            Integer price = priceMap.get(templateId);

            boolean judge = true;
            List<FreightTemplateCarriageDO> carriageDOList = freightTemplateById.getCarriageDOList();
            if(!CollectionUtils.isEmpty(carriageDOList)){
                for (FreightTemplateCarriageDO carriageDO : carriageDOList) {
                    if(carriageDO.getDesignatedArea().contains(freightCalcModel.getProvince())){
                        postage += calcPostage(carriageDO.getFreePrice(),carriageDO.getFirstWeight(),carriageDO.getFirstPrice(),carriageDO.getContinueWeight(),carriageDO.getContinuePrice(),weight,price);
                        judge = false;
                        break;
                    }
                }
            }
            if(judge){
                postage += calcPostage(freightTemplateById.getDefaultFreePrice(),freightTemplateById.getDefaultFirstWeight(),freightTemplateById.getDefaultFirstPrice(),freightTemplateById.getDefaultContinueWeight(),freightTemplateById.getDefaultContinuePrice(),weight,price);
            }
        }
        return postage;
    }

    public ShipTraceDTO getShipTraceList(String shipNo, String shipCode) throws ServiceException {
        return shipTraceQuery.query(shipNo, shipCode);
    }

    // 内部有调用，不用切面
    public FreightTemplateDTO getFreightTemplateById(Long id) throws ServiceException {
        FreightTemplateDTO obj = cacheComponent.getObj(CacheConst.FREIGHT_TEMPLATE + id, FreightTemplateDTO.class);
        if(obj != null){
            return obj;
        }

        FreightTemplateDO freightTemplateDO = templateMapper.selectById(id);
        if(freightTemplateDO == null){
            throw new BizServiceException(ExceptionDefinition.FREIGHT_TEMPLATE_NOT_EXIST);
        }
        List<FreightTemplateCarriageDO> template_id = carriageMapper.selectList(
                new QueryWrapper<FreightTemplateCarriageDO>()
                .eq("template_id", freightTemplateDO.getId()));
        FreightTemplateDTO resultDTO = new FreightTemplateDTO();
        BeanUtils.copyProperties(freightTemplateDO,resultDTO);
        resultDTO.setCarriageDOList(template_id);

        cacheComponent.putObj(CacheConst.FREIGHT_TEMPLATE + id , resultDTO);
        return resultDTO;
    }

    private int calcPostage(Integer freePrice,Integer firstWeight, Integer firstPrice, Integer continueWeight, Integer continuePrice, Integer weight, Integer price){
        if(freePrice.intValue() == 0 || (freePrice.intValue() > 0 && freePrice.intValue() <= price)){
            return 0;
        }

        int count = firstPrice;
        weight -= firstWeight;
        while(weight > 0){
            count += continuePrice;
            weight -= continueWeight;
        }
        return count;
    }
}
