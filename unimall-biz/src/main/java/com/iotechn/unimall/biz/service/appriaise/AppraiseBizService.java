package com.iotechn.unimall.biz.service.appriaise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.annotaion.AspectCommonCache;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.AppraiseDO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.mapper.AppraiseMapper;
import com.iotechn.unimall.data.mapper.ImgMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rize on 2019/7/13.
 */
@Service
public class AppraiseBizService {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private ImgMapper imgMapper;

    @AspectCommonCache(value = CacheConst.APPRAISE_KEY,argIndex = {0,1,2},second = Const.CACHE_ONE_DAY)
    public Page<AppraiseResponseDTO> getSpuAllAppraise(Long spuId, Integer pageNo, Integer pageSize) throws ServiceException {
        Integer count = appraiseMapper.selectCount(new QueryWrapper<AppraiseDO>().eq("spu_id", spuId));
        Integer offset = pageSize * (pageNo - 1);
        List<AppraiseResponseDTO> appraiseResponseDTOS = appraiseMapper.selectSpuAllAppraise(spuId, offset, pageSize);
        for (AppraiseResponseDTO appraiseResponseDTO : appraiseResponseDTOS) {
            appraiseResponseDTO.setImgList(imgMapper.getImgs(BizType.COMMENT.getCode(), appraiseResponseDTO.getId()));
        }
        Page<AppraiseResponseDTO> page = new Page<>(appraiseResponseDTOS, pageNo, pageSize, count);
        return page;
    }

}
