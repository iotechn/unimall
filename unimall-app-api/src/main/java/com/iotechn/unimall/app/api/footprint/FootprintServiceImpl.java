package com.iotechn.unimall.app.api.footprint;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFootprint(Long userId, Long footprintId) throws ServiceException {
        Integer judgeSQL = footprintMapper.delete(new EntityWrapper<FootprintDO>()
                .eq("user_id",userId)
                .eq("id",footprintId));
        if(judgeSQL > 0){
            return true;
        }
        throw new AppServiceException(ExceptionDefinition.FOOTPRINT_DELETE_FAILED);
    }

    @Override
    public List<FootprintDTO> getAllFootprint(Long userId) throws ServiceException {
        List<FootprintDTO> footprintDTOList = footprintMapper.getAllFootprint(userId,0,30);
        return footprintDTOList;
    }
}
