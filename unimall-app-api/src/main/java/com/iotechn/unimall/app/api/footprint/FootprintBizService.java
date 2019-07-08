package com.iotechn.unimall.app.api.footprint;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.FootprintDO;
import com.iotechn.unimall.data.mapper.FootprintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午10:15
 */
public class FootprintBizService {

    @Autowired
    private FootprintMapper footprintMapper;

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

}
