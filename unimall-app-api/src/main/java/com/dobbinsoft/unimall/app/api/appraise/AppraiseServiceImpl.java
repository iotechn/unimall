package com.dobbinsoft.unimall.app.api.appraise;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.storage.StorageClient;
import com.dobbinsoft.unimall.biz.service.appriaise.AppraiseBizService;
import com.dobbinsoft.unimall.biz.service.order.OrderBizService;
import com.dobbinsoft.unimall.data.constant.CacheConst;
import com.dobbinsoft.unimall.data.domain.AppraiseDO;
import com.dobbinsoft.unimall.data.domain.ImgDO;
import com.dobbinsoft.unimall.data.domain.OrderDO;
import com.dobbinsoft.unimall.data.domain.OrderSkuDO;
import com.dobbinsoft.unimall.data.dto.appraise.AppraiseRequestDTO;
import com.dobbinsoft.unimall.data.dto.appraise.AppraiseRequestItemDTO;
import com.dobbinsoft.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.dobbinsoft.unimall.data.enums.BizType;
import com.dobbinsoft.unimall.data.enums.OrderStatusType;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.AppraiseMapper;
import com.dobbinsoft.unimall.data.mapper.ImgMapper;
import com.dobbinsoft.unimall.data.mapper.OrderMapper;
import com.dobbinsoft.unimall.data.mapper.OrderSkuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import com.dobbinsoft.fw.support.utils.StringUtils;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

/*
@author kbq
@date  2019/7/6 - 11:08
*/
@Service("appraiseService")
public class AppraiseServiceImpl implements AppraiseService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ImgMapper imgMapper;
    @Autowired
    private AppraiseMapper appraiseMapper;
    @Autowired
    private OrderSkuMapper orderSkuMapper;
    @Autowired
    private CacheComponent cacheComponent;
    @Autowired
    private AppraiseBizService appraiseBizService;
    @Autowired
    private OrderBizService orderBizService;
    @Autowired
    private StorageClient storageClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(AppraiseRequestDTO appraiseRequestDTO, Long userId) throws ServiceException {
        // 1. 参数校验 是否有对应等待评价的订单
        if (appraiseRequestDTO.getOrderId() == null) {
            throw new ServiceException(ExceptionDefinition.APPRAISE_PARAM_CHECK_FAILED);
        }
        OrderDO orderDO = orderMapper.selectOne(
                new QueryWrapper<OrderDO>()
                        .eq("id", appraiseRequestDTO.getOrderId())
                        .eq("status", OrderStatusType.WAIT_APPRAISE.getCode())
                        .eq("user_id", userId));
        if (orderDO == null) {
            throw new ServiceException(ExceptionDefinition.APPRAISE_ORDER_CHECK_FAILED);
        }

        // 2. 如果传入评价list中没有数据，就直接转变订单状态发出
        LocalDateTime now = LocalDateTime.now();
        if (CollectionUtils.isEmpty(appraiseRequestDTO.getAppraiseDTOList())) {
            OrderDO updateOrderDO = new OrderDO();
            updateOrderDO.setStatus(OrderStatusType.COMPLETE.getCode());
            orderBizService.changeOrderSubStatus(orderDO.getOrderNo(), OrderStatusType.COMPLETE.getCode(), updateOrderDO);
        }

        // 3. 循环读取订单评价中所有商品的评价
        List<Long> spuIds = new ArrayList<>();
        for (AppraiseRequestItemDTO appraiseDTO : appraiseRequestDTO.getAppraiseDTOList()) {
            Long count = orderSkuMapper.selectCount(new QueryWrapper<OrderSkuDO>()
                    .eq("order_id", appraiseRequestDTO.getOrderId())
                    .eq("spu_id", appraiseDTO.getSpuId())
                    .eq("sku_id", appraiseDTO.getSkuId()));
            //从order_sku表中 验证是否有对应的表单和商品
            if (count == 0) {
                throw new ServiceException(ExceptionDefinition.APPRAISE_PARAM_CHECK_FAILED);
            }
            AppraiseDO appraiseDO = new AppraiseDO();
            // 防止传入id,导致插入数据库出错
            BeanUtils.copyProperties(appraiseDTO, appraiseDO, "id");
            appraiseDO.setSpuId(appraiseDTO.getSpuId());
            appraiseDO.setOrderId(appraiseRequestDTO.getOrderId()); //从传入数据取出，不使用DTO中的冗余数据
            appraiseDO.setUserId(userId);
            appraiseDO.setGmtCreate(now);
            appraiseDO.setGmtUpdate(appraiseDO.getGmtCreate());
            appraiseMapper.insert(appraiseDO);  //插入该订单该商品评价
            //删除商品评论缓存
            spuIds.add(appraiseDTO.getSpuId());
            String imgUrlS = appraiseDTO.getImgUrl();
            if (StringUtils.isEmpty(imgUrlS)) {
                continue;
            }
            String[] imgUrlList = imgUrlS.split(",");   //传入图片
            for (String imgUrl : imgUrlList) {
                ImgDO imgDO = new ImgDO();
                imgDO.setBizType(BizType.APPRAISE.getCode());
                imgDO.setBizId(appraiseDO.getId());
                imgDO.setUrl(imgUrl);
                imgDO.setGmtCreate(now);
                imgDO.setGmtUpdate(imgDO.getGmtCreate());
                imgMapper.insert(imgDO);
            }
        }

        // 4. 改变订单状态
        OrderDO updateOrderDO = new OrderDO();
        updateOrderDO.setStatus(OrderStatusType.COMPLETE.getCode());
        orderBizService.changeOrderSubStatus(orderDO.getOrderNo(), OrderStatusType.WAIT_APPRAISE.getCode(), updateOrderDO);
        // 5. 清理缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                for (Long spuId : spuIds) {
                    cacheComponent.delPrefixKey(CacheConst.PRT_APPRAISE_LIST + spuId);
                }
            }
        });
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long appraiseId, Long userId) throws ServiceException {
        AppraiseDO appraiseDO = appraiseMapper.selectById(appraiseId);
        if (appraiseMapper.delete(new QueryWrapper<AppraiseDO>()
                .eq("id", appraiseId)
                .eq("user_id", userId)) > 0) {
            List<String> imgs = imgMapper.getImgs(BizType.APPRAISE.getCode(), appraiseId);
            imgMapper.delete(new QueryWrapper<ImgDO>()
                    .eq("biz_type",BizType.APPRAISE.getCode())
                    .eq("biz_id",appraiseId));
            // 用户删除评价，需要删除对应产品的评论缓存
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    cacheComponent.delPrefixKey(CacheConst.PRT_APPRAISE_LIST + appraiseDO.getSpuId());
                    for (String img : imgs) {
                        storageClient.delete(img);
                    }
                }
            });
            return "ok";
        }
        throw new ServiceException(ExceptionDefinition.APPRAISE_PARAM_CHECK_FAILED);

    }

    @Override
    public Page<AppraiseResponseDTO> getUserAllAppraise(Long userId, Integer pageNo, Integer pageSize) throws ServiceException {
        return appraiseBizService.getUserAppraisePage(userId, pageNo, pageSize);
    }


    @Override
    public Page<AppraiseResponseDTO> getSpuAppraisePage(Long spuId, Integer pageNo, Integer pageSize) throws ServiceException {
        return appraiseBizService.getSpuAppraisePage(spuId, pageNo, pageSize);
    }

    @Override
    public AppraiseResponseDTO getAppraiseById(Long appraiseId) throws ServiceException {
        AppraiseResponseDTO appraiseResponseDTO = appraiseMapper.selectOneById(appraiseId);
        if (appraiseResponseDTO == null) {
            throw new ServiceException(ExceptionDefinition.APPRAISE_PARAM_CHECK_FAILED);
        }
        appraiseResponseDTO.setImgList(imgMapper.getImgs(BizType.APPRAISE.getCode(), appraiseResponseDTO.getId()));
        return appraiseResponseDTO;
    }

}
