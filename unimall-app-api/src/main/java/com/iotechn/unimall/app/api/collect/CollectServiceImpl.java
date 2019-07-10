package com.iotechn.unimall.app.api.collect;

/*
@author kbq
@date  2019/7/5 - 10:48
*/

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.app.api.goods.GoodsBizService;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CollectDO;
import com.iotechn.unimall.data.dto.CollectDTO;
import com.iotechn.unimall.data.mapper.CollectMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {


    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private GoodsBizService goodsBizService;

    private static final String CA_USER_COLLECT_HASH = "CA_USER_COLLECT_";

    @Override
    @Transactional
    public Boolean addCollect(Long userId, Long spuId) throws ServiceException {
        //校验SPU是否存在
        goodsBizService.getSpuById(spuId);
        List<CollectDO> collectDOS = collectMapper.selectList(new EntityWrapper<CollectDO>()
                .eq("user_id", userId)
                .eq("spu_id", spuId));
        if (!CollectionUtils.isEmpty(collectDOS)) {
            throw new AppServiceException(ExceptionDefinition.COLLECT_ALREADY_EXISTED);
        }
        CollectDO collectDO = new CollectDO(userId, spuId);
        Date now = new Date();
        collectDO.setGmtCreate(now);
        collectDO.setGmtUpdate(collectDO.getGmtCreate());
        cacheComponent.putSetRaw(CA_USER_COLLECT_HASH + userId, spuId + "", Const.CACHE_ONE_DAY);
        return collectMapper.insert(collectDO) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteCollect(Long userId, Long spuId) throws ServiceException {
        Integer num = collectMapper.delete(new EntityWrapper<CollectDO>()
                .eq("user_id", userId)
                .eq("spu_id", spuId)
        );
        if (num > 0) {
            cacheComponent.removeSetRaw(CA_USER_COLLECT_HASH + userId, spuId + "");
            return true;
        }

        throw new AppServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
    }

    @Override
    public Page<CollectDTO> getCollectAll(Long userId, Integer page, Integer size) throws ServiceException {
        Integer count = collectMapper.selectCount(new EntityWrapper<CollectDO>().eq("user_id", userId));
        Integer totalPage = 1;
        if (size <= 0 || page <= 0) {
            throw new AppServiceException(ExceptionDefinition.COLLECT_PARAM_CHECK_FAILED);
        }
        if (count % size == 0 && count != 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        if (page >= totalPage) {
            page = totalPage;
        }
        Integer offset = size * (page - 1);
        List<CollectDTO> collectAll = collectMapper.getCollectAll(userId, offset, size);
        Page<CollectDTO> pageination = new Page<CollectDTO>(collectAll, page, size, count);
        return pageination;
    }

    @Override
    public CollectDTO getCollectById(Long userId, Long collectId, Long spuId) throws ServiceException {
        return collectMapper.getCollectById(userId, collectId, spuId);
    }

    @Override
    public Boolean getCollectBySpuId(Long spuId, Long userId) throws ServiceException {
        boolean hasKey = cacheComponent.hasKey(CA_USER_COLLECT_HASH + userId);
        if (!hasKey) {
            //若没有Key，则添加缓存
            List<String> spuIds = collectMapper.getUserCollectSpuIds(userId);
            if (CollectionUtils.isEmpty(spuIds)) {
                //redis set不能为空
                spuIds.add("0");
            }
            cacheComponent.putSetRawAll(CA_USER_COLLECT_HASH + userId, spuIds.toArray(new String[0]), Const.CACHE_ONE_DAY);
        }
        return cacheComponent.isSetMember(CA_USER_COLLECT_HASH + userId, spuId + "");
    }
}
