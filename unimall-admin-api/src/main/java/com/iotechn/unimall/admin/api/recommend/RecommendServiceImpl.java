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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.xml.ws.Service;
import java.rmi.ServerException;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午5:47
 */
public class RecommendServiceImpl implements RecommendService {

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

        if (!(recommendMapper.selectCount(new EntityWrapper<RecommendDO>()
                .eq("spu_id", spuId)
                .eq("recommend_type", recommendType)) > 0)) {
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
    public List<RecommendDTO> queryRecommend(Long adminId, Integer recommendType, Integer pageNo, Integer pageSize) throws ServiceException {
        //先从缓存中找
        List<RecommendDTO> recommendDTOList = cacheComponent.getObjList(RecommendType.RECOMMEND_NAME+Integer.toString(recommendType),RecommendDTO.class);
        if(CollectionUtils.isEmpty(recommendDTOList)){
            recommendDTOList = recommendMapper.getRecommendByType(recommendType,pageNo,pageSize);
            if(!CollectionUtils.isEmpty(recommendDTOList)){
                cacheComponent.putObj(RecommendType.RECOMMEND_NAME+Integer.toString(recommendType),recommendDTOList,1000);
            }
        }
        return recommendDTOList;
    }
}
