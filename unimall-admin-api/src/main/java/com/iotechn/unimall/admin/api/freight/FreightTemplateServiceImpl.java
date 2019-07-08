package com.iotechn.unimall.admin.api.freight;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.admin.exception.AdminExceptionDefinition;
import com.iotechn.unimall.admin.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.FreightTemplateCarriageDO;
import com.iotechn.unimall.data.domain.FreightTemplateDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import com.iotechn.unimall.data.mapper.FreightTemplateCarriageMapper;
import com.iotechn.unimall.data.mapper.FreightTemplateMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午4:25
 */
@Service
public class FreightTemplateServiceImpl implements FreightTemplateService{

    @Autowired
    private FreightTemplateMapper freightTemplateMapper;

    @Autowired
    private FreightTemplateCarriageMapper freightTemplateCarriageMapper;

    @Autowired
    private SpuMapper spuMapper;


    @Override
    @Transactional
    public boolean addFreightTemplate(String templateName, String spuLocation, Integer deliveryDeadline, Integer  defaultFreePrice, Integer  defaultFirstNum, Integer  defaultFirstPrice, Integer  defaultContinueNum, Integer  defaultContinuePrice, List<FreightTemplateCarriageDO> freightTemplateCarriageDOList, Long adminId) throws ServiceException {
        Date now = new Date();
        FreightTemplateDO freightTemplateDO = new FreightTemplateDO(templateName,spuLocation,deliveryDeadline,defaultFreePrice,defaultFirstNum,defaultFirstPrice,defaultContinueNum,defaultContinuePrice);
        freightTemplateDO.setGmtCreate(now);
        freightTemplateDO.setGmtUpdate(freightTemplateDO.getGmtCreate());
        Integer judgeSQL = 1;   //用于判断sql是否执行成功
        judgeSQL = freightTemplateMapper.insert(freightTemplateDO); //插入模板主表
        if(!(judgeSQL > 0)){
            throw  new AdminServiceException(AdminExceptionDefinition.FREIGHT_TEMPLATE_INSERT_FAILED);
        }
        if(freightTemplateCarriageDOList == null || freightTemplateCarriageDOList.size() == 0){
            return true;
        }

        //表中设定可默认值，所以就不检查是否为空
        for(FreightTemplateCarriageDO freightTemplateCarriageDO : freightTemplateCarriageDOList){
            freightTemplateCarriageDO.setTemplateId(freightTemplateDO.getId());
            freightTemplateCarriageDO.setGmtCreate(now);
            freightTemplateCarriageDO.setGmtUpdate(freightTemplateCarriageDO.getGmtCreate());
            judgeSQL = freightTemplateCarriageMapper.insert(freightTemplateCarriageDO);
            if(!(judgeSQL > 0)){
                throw  new AdminServiceException(AdminExceptionDefinition.FREIGHT_CARRIAGE_INSERT_FAILED);
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean deleteFreightTemplate(Long templateId, Long adminId) throws ServiceException {
        Integer judgeSQL = 1;
        if(spuMapper.selectCount(new EntityWrapper<SpuDO>().eq("freight_template_id",templateId))>0){
            throw new AdminServiceException(AdminExceptionDefinition.FREIGHT_SPU_QUERY_HAS);
        }
        judgeSQL = freightTemplateMapper.delete(new EntityWrapper<FreightTemplateDO>()
                .eq("id",templateId));
        if(!(judgeSQL > 0)){
            throw  new AdminServiceException(AdminExceptionDefinition.FREIGHT_TEMPLATE_DELETE_FAILED);
        }
        judgeSQL = freightTemplateCarriageMapper.selectCount(new EntityWrapper<FreightTemplateCarriageDO>().eq("templateId",templateId));
        if(judgeSQL == 0){
            return true;
        }
        judgeSQL = freightTemplateCarriageMapper.delete(new EntityWrapper<FreightTemplateCarriageDO>()
                .eq("templateId",templateId));
        if(judgeSQL > 0)
        {return true;}
        else
        {return false;}
    }

    @Override
    @Transactional
    public boolean updateFreightTemplate(Long templateId,String templateName, String spuLocation, Integer deliveryDeadline, Integer defaultFreePrice, Integer defaultFirstNum, Integer defaultFirstPrice, Integer defaultContinueNum, Integer defaultContinuePrice, List<FreightTemplateCarriageDO> freightTemplateCarriageDOList, Long adminId) throws ServiceException {
        Date now = new Date();
        FreightTemplateDO freightTemplateDO = new FreightTemplateDO(templateName,spuLocation,deliveryDeadline,defaultFreePrice,defaultFirstNum,defaultFirstPrice,defaultContinueNum,defaultContinuePrice);
        freightTemplateDO.setId(templateId);
        freightTemplateDO.setGmtUpdate(now);
        Integer judgeSQL = freightTemplateMapper.updateById(freightTemplateDO);
        if(!(judgeSQL > 0)){    //如果主表修改失败
            throw new AdminServiceException(AdminExceptionDefinition.FREIGHT_TEMPLATE_UPDATE_FAILED);
        }

        freightTemplateCarriageMapper.delete(new EntityWrapper<FreightTemplateCarriageDO>().eq("templateId",templateId));

        if(freightTemplateCarriageDOList == null || freightTemplateCarriageDOList.size() == 0){
            return true;
        }
        //表中设定可默认值，所以就不检查是否为空
        for(FreightTemplateCarriageDO freightTemplateCarriageDO : freightTemplateCarriageDOList){
            freightTemplateCarriageDO.setTemplateId(freightTemplateDO.getId());
            freightTemplateCarriageDO.setGmtCreate(now);
            freightTemplateCarriageDO.setGmtUpdate(freightTemplateCarriageDO.getGmtCreate());
            judgeSQL = freightTemplateCarriageMapper.insert(freightTemplateCarriageDO);
            if(!(judgeSQL > 0)){
                throw  new AdminServiceException(AdminExceptionDefinition.FREIGHT_CARRIAGE_INSERT_FAILED);
            }
        }
        return true;
    }

    @Override
    public List<FreightTemplateDTO> getAllFreightTemplate(Long adminId) throws ServiceException {
        List<FreightTemplateDO> freightTemplateDOList = freightTemplateMapper.selectList(null); //查出主表所有数据
        List<FreightTemplateDTO> freightTemplateDTOList = new ArrayList<>();
        if(freightTemplateDOList == null || freightTemplateDOList.size() == 0){ //如果主表没有记录
            return  freightTemplateDTOList;
        }
        for (FreightTemplateDO freightTemplateDO : freightTemplateDOList){  //查出副表中，主表每条数据对应的数据
            FreightTemplateDTO freightTemplateDTO = new FreightTemplateDTO();
            List<FreightTemplateCarriageDO> freightTemplateCarriageDOList = freightTemplateCarriageMapper.selectList(new EntityWrapper<FreightTemplateCarriageDO>()
                    .eq("templateId", freightTemplateDO.getId()));
            freightTemplateDTO.setFreightTemplateDO(freightTemplateDO);
            freightTemplateDTO.setFreightTemplateCarriageDOList(freightTemplateCarriageDOList);
            freightTemplateDTOList.add(freightTemplateDTO);
        }
        return freightTemplateDTOList;
    }


}
