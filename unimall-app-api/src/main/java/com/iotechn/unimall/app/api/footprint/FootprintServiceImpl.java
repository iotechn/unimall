package com.iotechn.unimall.app.api.footprint;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.FootprintDO;
import com.iotechn.unimall.data.dto.FootprintDTO;
import com.iotechn.unimall.data.mapper.FootprintMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Transactional
    public boolean addOrUpdateFootprint(Long userId, Long spuId) throws ServiceException {

        Date now = new Date();
        List<FootprintDO> footprintDOList = footprintMapper.selectList(new EntityWrapper<FootprintDO>()
                .eq("user_id",userId)
                .eq("spu_id",spuId));
        if(footprintDOList == null || footprintDOList.size() == 0){
            FootprintDO footprintDO = new FootprintDO(userId,spuId);
            footprintDO.setGmtCreate(now);
            footprintDO.setGmtUpdate(footprintDO.getGmtCreate());
            return footprintMapper.insert(footprintDO)>0;
        }
        FootprintDO footprintDO = footprintDOList.get(0);
        footprintDO.setGmtUpdate(now);
        return footprintMapper.updateById(footprintDO) > 0;
    }

    @Override
    @Transactional
    public boolean deleteFootprint(Long userId, Long footprintId) throws ServiceException {
        Integer judgeSQL = footprintMapper.delete(new EntityWrapper<FootprintDO>()
                .eq("user_id",userId)
                .eq("id",footprintId));
        if(judgeSQL > 0){
            return true;
        }
        throw new AppServiceException(AppExceptionDefinition.FOOTPRINT_DELETE_FAILED);
    }

    @Override
    public List<FootprintDTO> getAllFootprint(Long userId) throws ServiceException {

        List<FootprintDTO> footprintDTOList = footprintMapper.getAllFootprint(userId,0,30);
        return footprintDTOList;
    }
}
