package com.iotechn.unimall.biz.service.appriaise;

import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.annotation.AspectCommonCache;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.mapper.AppraiseMapper;
import com.iotechn.unimall.data.mapper.ImgMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rize on 2019/7/13.
 */
@Service
public class AppraiseBizService {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private ImgMapper imgMapper;

    @AspectCommonCache(value = CacheConst.PRT_APPRAISE_LIST, argIndex = {0, 1, 2}, second = Const.CACHE_ONE_DAY)
    public Page<AppraiseResponseDTO> getSpuAppraisePage(Long spuId, Integer pageNo, Integer pageSize) throws ServiceException {
        return appraiseMapper.selectSpuAppraisePage(Page.div(pageNo, pageSize, AppraiseResponseDTO.class), spuId).trans(item -> {
            item.setImgList(imgMapper.getImgs(BizType.APPRAISE.getCode(), item.getId()));
            return item;
        });
    }

    public Page<AppraiseResponseDTO> getUserAppraisePage(Long userId, Integer pageNo, Integer pageSize) {
        return appraiseMapper.selectUserAppraisePage(Page.div(pageNo, pageSize, AppraiseResponseDTO.class), userId).trans(item -> {
            item.setImgList(imgMapper.getImgs(BizType.APPRAISE.getCode(), item.getId()));
            return item;
        });
    }

}
