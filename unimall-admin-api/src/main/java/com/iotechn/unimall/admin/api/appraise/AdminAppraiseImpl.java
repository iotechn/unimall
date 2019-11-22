package com.iotechn.unimall.admin.api.appraise;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.mapper.AppraiseMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAppraise(Long adminId, Long id) throws ServiceException {
        return appraiseMapper.deleteById(id) > 0;
    }

    @Override
    public Page<AppraiseResponseDTO> getAppraiseList(Long adminId, Long id, String userName, String spuName, Long orderId, Integer score, String content,Integer pageNo,Integer limit) throws ServiceException {

        Integer count = appraiseMapper.countAppraiseCondition(id,userName ,spuName , orderId, score, content);

        List<AppraiseResponseDTO> appraiseResponseDTOList = appraiseMapper.selectAppraiseCondition(id,userName ,spuName , orderId, score, content,(pageNo-1)*limit,limit);

        Page<AppraiseResponseDTO> page = new Page<AppraiseResponseDTO>(appraiseResponseDTOList,pageNo,limit,count);

        return page;
    }
}
