package com.iotechn.unimall.admin.api.appraise;

import com.iotechn.unimall.biz.service.appriaise.AppraiseBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AppraiseDO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.mapper.AppraiseMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-15
 * Time: 下午3:56
 */
@Service
public class AdminAppraiseImpl implements  AdminAppraise {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAppraise(Long adminId, Long id) throws ServiceException {
        AppraiseDO appraiseDO = appraiseMapper.selectById(id);
        boolean succ = appraiseMapper.deleteById(id) > 0;
        if (succ) {
            cacheComponent.delPrefixKey(AppraiseBizService.CA_APPRAISE_KEY + appraiseDO.getSpuId());
        }
        return succ;
    }

    @Override
    public Page<AppraiseResponseDTO> getAppraiseList(Long adminId, Long id, String userName, String spuName, Long orderId, Integer score, String content,Integer pageNo,Integer limit) throws ServiceException {

        Integer count = appraiseMapper.countAppraiseCondition(id,userName ,spuName , orderId, score, content);

        List<AppraiseResponseDTO> appraiseResponseDTOList = appraiseMapper.selectAppraiseCondition(id,userName ,spuName , orderId, score, content,(pageNo-1)*limit,limit);

        Page<AppraiseResponseDTO> page = new Page<AppraiseResponseDTO>(appraiseResponseDTOList,pageNo,limit,count);

        return page;
    }
}
