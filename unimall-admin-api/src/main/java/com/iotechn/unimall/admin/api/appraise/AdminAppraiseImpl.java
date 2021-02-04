package com.iotechn.unimall.admin.api.appraise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.service.storage.StorageBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.AppraiseDO;
import com.iotechn.unimall.data.domain.ImgDO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.mapper.AppraiseMapper;
import com.iotechn.unimall.data.mapper.ImgMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-15
 * Time: 下午3:56
 */
@Service
public class AdminAppraiseImpl implements AdminAppraise {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private StorageBizService storageBizService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id, Long adminId) throws ServiceException {
        AppraiseDO appraiseDO = appraiseMapper.selectById(id);
        if (appraiseMapper.deleteById(id) > 0) {
            List<String> imgs = imgMapper.getImgs(BizType.APPRAISE.getCode(), id);
            imgMapper.delete(new QueryWrapper<ImgDO>()
                    .eq("biz_type",BizType.APPRAISE.getCode())
                    .eq("biz_id",id));

            // 用户删除评价，需要删除对应产品的评论缓存, 并且删除图片
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    cacheComponent.delPrefixKey(CacheConst.PRT_APPRAISE_LIST + appraiseDO.getSpuId());
                    storageBizService.delete(imgs);
                }
            });
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public Page<AppraiseResponseDTO> list(Long id, String userName, String spuName, Long orderId, Integer score, String content, Integer pageNo, Integer limit, Long adminId) throws ServiceException {
        return appraiseMapper.selectAppraisePage(Page.div(pageNo, limit, AppraiseResponseDTO.class), id, userName, spuName, orderId, score, content);
    }
}
