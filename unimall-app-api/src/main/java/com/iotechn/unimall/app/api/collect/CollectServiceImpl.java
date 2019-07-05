package com.iotechn.unimall.app.api.collect;

/*
@author kbq
@date  2019/7/5 - 10:48
*/

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CollectDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.CollectDTO;
import com.iotechn.unimall.data.mapper.CollectMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.mapper.UserMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Override
    @Transactional
    public Boolean addCollect(Long userId, Long spuId) throws ServiceException {
        SpuDO spuDO = spuMapper.selectById(spuId);
        List<CollectDO> collectDOS = collectMapper.selectList(new EntityWrapper<CollectDO>()
                .eq("user_id", userId)
                .eq("spu_id", spuId));
        if(spuDO == null){
            throw new AppServiceException(AppExceptionDefinition.APP_PARAM_CHECK_FAILED);
        }
        if(collectDOS != null){
            throw new AppServiceException(AppExceptionDefinition.COLLECT_ALREADY_EXISTED);
        }

        CollectDO collectDO = new CollectDO(userId,spuId);
        Date now = new Date();
        collectDO.setGmtCreate(now);
        collectDO.setGmtUpdate(collectDO.getGmtCreate());
        return collectMapper.insert(collectDO) > 0;
    }

    @Override
    @Transactional
    public Boolean deleteCollect(Long userId, Long spuId,Long collectId) throws ServiceException {
        Integer num = collectMapper.delete(new EntityWrapper<CollectDO>()
                .eq("user_id",userId)
                .eq("spu_id",spuId)
                .eq("id",collectId)
        );
        if(num > 0){
            return true;
        }

        throw new AppServiceException(AppExceptionDefinition.APP_PARAM_CHECK_FAILED);
    }

    @Override
    public Page<CollectDTO> getCollectAll(Long userId, Integer page, Integer size) throws ServiceException {
        Integer count = collectMapper.countCollect(userId);
        Integer totalPage = 1;
        if(size <= 0 || page <= 0){
            throw new AppServiceException(AppExceptionDefinition.COLLECT_PARAM_CHECK_FAILED);
        }
        if(count % size == 0){
            totalPage = count / size;
        }else {
            totalPage = count / size + 1;
        }
        if(page >= totalPage){
            page = totalPage;
        }
        Integer offset = size * (page-1);
        List<CollectDTO> collectAll = collectMapper.getCollectAll(userId, offset, size);
        Page<CollectDTO> pageination = new Page<CollectDTO>(collectAll,page,size,totalPage);
        return pageination;
    }

    @Override
    public CollectDTO getCollectById(Long userId, Long collectId, Long spuId) throws ServiceException {
        return collectMapper.getCollectById(userId,collectId,spuId);
    }
}
