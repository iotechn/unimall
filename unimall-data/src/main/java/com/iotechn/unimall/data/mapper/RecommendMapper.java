package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.RecommendDO;
import com.iotechn.unimall.data.dto.RecommendDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午3:32
 */
public interface RecommendMapper extends BaseMapper<RecommendDO> {

    //根据推荐类型，查找商品信息
    public List<RecommendDTO> getRecommendByType(@Param("recommendType") Integer recommendType,@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

    public List<RecommendDTO> getAllRecommend(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

}
