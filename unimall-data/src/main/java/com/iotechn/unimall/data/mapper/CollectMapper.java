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

    /**
     * 获得用户所有收藏
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    public List<CollectDTO> getCollectAll(@Param("userId") Long userId, @Param("offset")Integer offset, @Param("size")Integer size);

    /**
     * 获得某一收藏
     * @param userId
     * @param collectId
     * @param spuId
     * @return
     */
    public CollectDTO getCollectById(@Param("userId") Long userId, @Param("collectId")Long collectId, @Param("spuId")Long spuId);

    public List<String> getUserCollectSpuIds(Long userId);

}
