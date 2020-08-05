package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iotechn.unimall.data.domain.FavoriteDO;
import com.iotechn.unimall.data.dto.FavoriteDTO;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
@author kbq
@date  2019/7/5 - 10:00
*/
public interface FavoriteMapper extends IMapper<FavoriteDO> {

    /**
     * 获得用户所有收藏
     * @param page 分页对象
     * @param userId
     * @return
     */
    public Page<FavoriteDTO> getFavoritePage(IPage page, @Param("userId") Long userId);

    /**
     * 获得某一收藏
     * @param userId
     * @param collectId
     * @param spuId
     * @return
     */
    public FavoriteDTO getCollectById(@Param("userId") Long userId, @Param("collectId")Long collectId, @Param("spuId")Long spuId);

    public List<String> getUserCollectSpuIds(Long userId);

}
