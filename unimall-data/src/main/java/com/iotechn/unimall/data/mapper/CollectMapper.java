package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.CollectDO;
import com.iotechn.unimall.data.dto.CollectDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
@author kbq
@date  2019/7/5 - 10:00
*/

public interface CollectMapper extends BaseMapper<CollectDO> {

    public Integer countCollect(Long userId);//统计用户收藏数量

    public List<CollectDTO> getCollectAll(@Param("userId") Long userId, @Param("offset")Integer offset, @Param("size")Integer size);//获得用户所有收藏

    public CollectDTO getCollectById(@Param("userId") Long userId, @Param("collectId")Long collectId, @Param("spuId")Long spuId);//获得某一收藏

    public List<String> getUserCollectSpuIds(Long userId);

}
