package com.iotechn.unimall.biz.service.footpring;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.FootprintDO;
import com.iotechn.unimall.data.mapper.FootprintMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午10:15
 */
@Service
public class FootprintBizService {

    @Autowired
    private FootprintMapper footprintMapper;

    @Transactional(rollbackFor = Exception.class)
    public boolean addOrUpdateFootprint(Long userId, Long spuId) throws ServiceException {
        Date now = new Date();
        List<FootprintDO> footprintDOList = footprintMapper.selectList(
                new EntityWrapper<FootprintDO>()
                        .eq("user_id", userId)
                        .eq("spu_id", spuId)
                        .orderBy("gmt_update", false));
        if (CollectionUtils.isEmpty(footprintDOList)) {
            FootprintDO footprintDO = new FootprintDO(userId, spuId);
            footprintDO.setGmtCreate(now);
            footprintDO.setGmtUpdate(now);
            return footprintMapper.insert(footprintDO) > 0;
        }
        FootprintDO footprintDO = footprintDOList.get(0);
        footprintDO.setGmtUpdate(now);
        return footprintMapper.updateById(footprintDO) > 0;
    }

}
