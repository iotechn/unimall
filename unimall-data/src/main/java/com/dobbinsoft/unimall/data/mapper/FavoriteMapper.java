package com.dobbinsoft.unimall.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.domain.FavoriteDO;
import com.dobbinsoft.unimall.data.dto.FavoriteDTO;
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
     * @param favoriteId
     * @param spuId
     * @return
     */
    public FavoriteDTO getFavoriteById(@Param("userId") Long userId, @Param("favoriteId") Long favoriteId, @Param("spuId") Long spuId);

    public List<String> getUserFavoriteSpuIds(Long userId);

}
