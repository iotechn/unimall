package com.dobbinsoft.unimall.app.api.favorite;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.domain.FavoriteDO;
import com.dobbinsoft.unimall.data.dto.FavoriteDTO;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.FavoriteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/*
@author kbq
@date  2019/7/5 - 10:48
*/
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(Long spuId, Long userId) throws ServiceException {
        //校验SPU是否存在
        Long count = favoriteMapper.selectCount(new QueryWrapper<FavoriteDO>()
                .eq("user_id", userId)
                .eq("spu_id", spuId));
        if (count > 0) {
            // 若收藏已存在，这可能是缓存与数据库不一致导致的，在正常的系统操作下不可能发生
            cacheComponent.putHashRaw(CacheConst.PRT_USER_FAVORITE_HASH_BUCKET + spuId, "U" + userId, "1");
            return "ok";
        }
        LocalDateTime now = LocalDateTime.now();
        FavoriteDO favoriteDO = new FavoriteDO();
        favoriteDO.setSpuId(spuId);
        favoriteDO.setUserId(userId);
        favoriteDO.setGmtCreate(now);
        favoriteDO.setGmtUpdate(now);
        cacheComponent.putHashRaw(CacheConst.PRT_USER_FAVORITE_HASH_BUCKET + spuId, "U" + userId, "1");
        if (favoriteMapper.insert(favoriteDO) > 0) {
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.APP_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long spuId, Long userId) throws ServiceException {
        int num = favoriteMapper.delete(
                new QueryWrapper<FavoriteDO>()
                        .eq("spu_id", spuId)
                        .eq("user_id", userId));
        if (num > 0) {
            cacheComponent.delHashKey(CacheConst.PRT_USER_FAVORITE_HASH_BUCKET + spuId, "U" + userId);
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
    }

    @Override
    public Page<FavoriteDTO> list(Long userId, Integer pageNo, Integer pageSize) throws ServiceException {
        return favoriteMapper.getFavoritePage(Page.div(pageNo, pageSize, FavoriteDTO.class), userId);
    }

    @Override
    public FavoriteDTO getFavoriteById(Long userId, Long collectId, Long spuId) throws ServiceException {
        return favoriteMapper.getFavoriteById(userId, collectId, spuId);
    }

    @Override
    public Boolean getFavoriteBySpuId(Long spuId, Long userId) throws ServiceException {
        String hashRaw = cacheComponent.getHashRaw(CacheConst.PRT_USER_FAVORITE_HASH_BUCKET + spuId, "U" + userId);
        return "1".equals(hashRaw);
    }
}
