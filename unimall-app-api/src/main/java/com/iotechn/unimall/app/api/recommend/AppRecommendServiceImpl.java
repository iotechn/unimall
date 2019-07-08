package com.iotechn.unimall.app.api.recommend;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.dto.RecommendDTO;
import com.iotechn.unimall.data.enums.RecommendType;
import com.iotechn.unimall.data.mapper.RecommendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午3:40
 */
@Service
public class AppRecommendServiceImpl implements AppRecommendService {

    @Autowired
    private CacheComponent cacheComponent;
    @Autowired
    private RecommendMapper recommendMapper;

    @Override
    public List<RecommendDTO> getRecommendByType(Integer recommendType,Integer pageNo,Integer pageSize) throws ServiceException {
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
