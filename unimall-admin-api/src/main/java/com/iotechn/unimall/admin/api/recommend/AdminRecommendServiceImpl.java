package com.iotechn.unimall.admin.api.recommend;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.admin.exception.AdminExceptionDefinition;
import com.iotechn.unimall.admin.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.RecommendDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.RecommendDTO;
import com.iotechn.unimall.data.enums.RecommendType;
import com.iotechn.unimall.data.mapper.RecommendMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.Page;
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
 * Time: 下午5:47
 */
@Service
public class AdminRecommendServiceImpl implements AdminRecommendService {

    @Autowired
    private CacheComponent cacheComponent;
    @Autowired
    private RecommendMapper recommendMapper;
    @Autowired
    private SpuMapper spuMapper;

    @Override
    @Transactional
    public Boolean addRecomend(Long adminId, Long spuId, Integer recommendType) throws ServiceException {

        if (!(spuMapper.selectCount(new EntityWrapper<SpuDO>().eq("id", spuId)) > 0)) {
            throw new AdminServiceException(AdminExceptionDefinition.RECOMMEND_SPU_NO_HAS);
        }

        if (recommendMapper.selectCount(new EntityWrapper<RecommendDO>()
                .eq("spu_id", spuId)
                .eq("recommend_type", recommendType)) > 0) {
            throw new AdminServiceException(AdminExceptionDefinition.RECOMMEND_ALREADY_HAS);
        }
        RecommendDO recommendDO = new RecommendDO(spuId, recommendType);
        Date now = new Date();
        recommendDO.setGmtCreate(now);
        recommendDO.setGmtUpdate(now);
        if (!(recommendMapper.insert(recommendDO) > 0)) {
            throw new AdminServiceException(AdminExceptionDefinition.RECOMMEND_SQL_ADD_FAILED);
        }
        cacheComponent.del(RecommendType.RECOMMEND_NAME + Integer.toString(recommendType));
        return true;
    }

    @Override
    @Transactional
    public Boolean deleteRecomend(Long adminId, Long spuId, Integer recommendType) throws ServiceException {

        Integer judgeSQL = recommendMapper.delete(new EntityWrapper<RecommendDO>()
                .eq("spu_id", spuId)
                .eq("recommend_type",recommendType));

        if(judgeSQL > 0){
            return true;
        }

        cacheComponent.del(RecommendType.RECOMMEND_NAME + Integer.toString(recommendType));
        throw new AdminServiceException(AdminExceptionDefinition.RECOMMEND_SQL_DELETE_FAILED);
    }

    @Override
    public Page<RecommendDTO> queryRecommendByType(Long adminId, Integer recommendType, Integer pageNo, Integer pageSize) throws ServiceException {

        Integer count = recommendMapper.selectCount(new EntityWrapper<RecommendDO>().eq("recommend_type",recommendType));
        List<RecommendDTO> recommendDTOList = recommendMapper.getRecommendByType(recommendType,pageNo,pageSize);
        Page<RecommendDTO> page = new Page<>(recommendDTOList,pageNo,pageSize,count);
        return page;
    }

    @Override
    public Page<RecommendDTO> queryAllRecommend(Long adminId, Integer pageNo, Integer pageSize) throws ServiceException {
        Integer count = recommendMapper.selectCount(null);
        List<RecommendDTO> recommendDTOList = recommendMapper.getAllRecommend(pageNo,pageSize);
        Page<RecommendDTO> page = new Page<>(recommendDTOList,pageNo,pageSize,count);
        return page;
    }
}
