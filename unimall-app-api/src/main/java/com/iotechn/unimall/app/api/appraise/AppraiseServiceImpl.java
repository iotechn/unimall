package com.iotechn.unimall.app.api.appraise;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.AppraiseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/*
@author kbq
@date  2019/7/6 - 11:08
*/
@Service
public class AppraiseServiceImpl implements AppraiseService {

    @Override
    public Boolean addAppraise(Long spuId, Long skuId, Long orderId, Long userId, String content, Integer score, String imgUrl) throws ServiceException {

        return null;
    }

    @Override
    public Boolean deleteAppraise(Long appraiseId, Long userId) throws ServiceException {
        return null;
    }

    @Override
    public List<AppraiseDTO> getUserAllAppraise(Long userId, Integer page, Integer size) throws ServiceException {
        return null;
    }

    @Override
    public List<AppraiseDTO> getSpuAllAppraise(Long userId, Long spuId, Integer page, Integer size) throws ServiceException {
        return null;
    }

    @Override
    public AppraiseDTO getOneById(Long userId, Long AppraiseId) throws ServiceException {
        return null;
    }
}
