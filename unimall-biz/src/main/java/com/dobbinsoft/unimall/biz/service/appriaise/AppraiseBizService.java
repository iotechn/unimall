package com.dobbinsoft.unimall.biz.service.appriaise;

import com.dobbinsoft.fw.core.Const;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.annotation.AspectCommonCache;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.dobbinsoft.unimall.data.enums.BizType;
import com.dobbinsoft.unimall.data.mapper.AppraiseMapper;
import com.dobbinsoft.unimall.data.mapper.ImgMapper;
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
