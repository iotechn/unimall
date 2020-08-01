package com.iotechn.unimall.app.api.footprint;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.FootprintDO;
import com.iotechn.unimall.data.dto.FootprintDTO;
import com.iotechn.unimall.data.mapper.FootprintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午8:58
 */
@Service
public class FootprintServiceImpl implements  FootprintService {

    @Autowired
    private FootprintMapper footprintMapper;

    @Autowired
    private CacheComponent cacheComponent;

    //TODO 前端没有了单个删除足迹的功能
    @Override
    public boolean deleteFootprint(Long userId, Long footprintId) throws ServiceException {
        return deleteAllFootprint(userId);
    }

    @Override
    public boolean deleteAllFootprint(Long userId) throws ServiceException {
        cacheComponent.del(CacheConst.FOOTPRINT_USER + userId);
        return true;
    }

    @Override
    public List<FootprintDTO> getAllFootprint(Long userId) throws ServiceException {

        return null;
    }
}
