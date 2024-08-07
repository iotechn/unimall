package com.dobbinsoft.unimall.admin.api.product;

import com.dobbinsoft.fw.support.utils.JacksonUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.domain.GroupShopDO;
import com.dobbinsoft.unimall.data.domain.SkuActivityPriceDO;
import com.dobbinsoft.unimall.data.domain.SkuDO;
import com.dobbinsoft.unimall.data.domain.SpuDO;
import com.dobbinsoft.unimall.data.dto.product.GroupShopDTO;
import com.dobbinsoft.unimall.data.dto.product.GroupShopSkuDTO;
import com.dobbinsoft.unimall.data.enums.GroupShopAutomaticRefundType;
import com.dobbinsoft.unimall.data.enums.SpuActivityType;
import com.dobbinsoft.unimall.data.enums.StatusType;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.mapper.GroupShopMapper;
import com.dobbinsoft.unimall.data.mapper.SkuActivityPriceMapper;
import com.dobbinsoft.unimall.data.mapper.SkuMapper;
import com.dobbinsoft.unimall.data.mapper.SpuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import com.dobbinsoft.fw.support.utils.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/*
@PackageName:com.dobbinsoft.unimall.admin.api.goods
@ClassName: AdminGroupShopGoodsServiceImpl
@Description:
@author kbq
@date 19-11-13下午4:21
*/
@Service
public class AdminGroupShopServiceImpl implements AdminGroupShopService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private GroupShopMapper groupShopMapper;

    @Autowired
    private SkuActivityPriceMapper skuActivityPriceMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(Long spuId, LocalDateTime gmtStart, LocalDateTime gmtEnd, Integer minNum, Integer automaticRefund, String groupShopSkuListStr, Long adminId) throws ServiceException {
        // 1.转化为对应的GroupShopSkuDTO类的链表
        List<GroupShopSkuDTO> groupShopSkuDTOList = JacksonUtil.parseArray(groupShopSkuListStr, GroupShopSkuDTO.class)
                .stream().sorted(Comparator.comparingInt(GroupShopSkuDTO::getSkuGroupShopPrice)).collect(Collectors.toList());

        if (gmtStart.isAfter(gmtEnd)) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_START_MUST_LESS_THAN_END);
        }

        // 2.1 检验数据库中是否存在spuId对应的sku
        SpuDO spuDO = spuMapper.selectById(spuId);
        List<SkuDO> skuDOList = skuMapper.selectList((new QueryWrapper<SkuDO>().eq("spu_id", spuId)));
        if (spuDO == null || CollectionUtils.isEmpty(skuDOList)) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXIST_OR_ONLY_SPU);
        }
        // 2.2 检验groupShop表中是否存在此商品
        Long count = groupShopMapper.selectCount((new QueryWrapper<GroupShopDO>().eq("spu_id", spuId)));
        if (count > 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_ALREADY_EXIT);
        }
        // 2.3 校验商品是否还有未存在的活动
        if (spuDO.getActivityId() != null && spuDO.getActivityType() != null
                && spuDO.getActivityType().intValue() != SpuActivityType.NONE.getCode()
                && spuDO.getGmtActivityEnd() != null && spuDO.getGmtActivityEnd().isAfter(LocalDateTime.now())) {
            throw new ServiceException(ExceptionDefinition.PRODUCT_SPU_EXIST_ACTIVITY);
        }

        // 3.创建团购活动信息
        // 3.1.插入主表

        LocalDateTime now = LocalDateTime.now();
        GroupShopDO groupShopDO = new GroupShopDO();
        groupShopDO.setBuyerNum(0);
        groupShopDO.setMinNum(minNum);
        groupShopDO.setGmtStart(gmtStart);
        groupShopDO.setGmtEnd(gmtEnd);
        groupShopDO.setAutomaticRefund(automaticRefund.compareTo(0) > 0 ? GroupShopAutomaticRefundType.YES.getCode() : GroupShopAutomaticRefundType.NO.getCode());
        groupShopDO.setSpuId(spuId);
        groupShopDO.setMinPrice(groupShopSkuDTOList.get(0).getSkuGroupShopPrice());
        groupShopDO.setMaxPrice(groupShopSkuDTOList.get(groupShopSkuDTOList.size() - 1).getSkuGroupShopPrice());
        groupShopDO.setGmtCreate(now);
        groupShopDO.setGmtUpdate(now);
        // 由活动开始定时任务更改其状态
        groupShopDO.setStatus(StatusType.LOCK.getCode());
        if (groupShopMapper.insert(groupShopDO) <= 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SPU_ADD_SQL_QUERY_ERROR);
        }

        if (skuDOList.size() != groupShopSkuDTOList.size()) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SKU_NUMBER_ERROR);
        }
        // 3.2 插入groupShopSkuList（插入从表）
        this.insertGroupShopSkuList(groupShopSkuDTOList, skuDOList, groupShopDO.getId());
        // 4.修改商品表活动信息
        SpuDO updateSpuDO = new SpuDO();
        updateSpuDO.setId(spuId);
        updateSpuDO.setActivityType(SpuActivityType.GROUP_SHOP.getCode());
        updateSpuDO.setActivityId(groupShopDO.getId());
        updateSpuDO.setGmtActivityStart(gmtStart);
        updateSpuDO.setGmtActivityEnd(gmtEnd);
        updateSpuDO.setGmtUpdate(now);
        spuMapper.updateById(updateSpuDO);
        // 5 清理缓存
        this.clearCache(spuId);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id, Long adminId) throws ServiceException {
        // 1. 参数校验
        GroupShopDO groupShopDO = groupShopMapper.selectById(id);
        if (groupShopDO == null) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXIST_OR_ONLY_SPU);
        }

        if (groupShopDO.getStatus() > 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_ALREADY_ACTIVE);
        }

        // 2. 删除团购
        if (groupShopMapper.deleteById(id) <= 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SPU_DELETE_SQL_QUERY_ERROR);
        }
        if (skuActivityPriceMapper.delete((
                new QueryWrapper<SkuActivityPriceDO>()
                        .eq("activity_id", id)
                        .eq("activity_type", SpuActivityType.GROUP_SHOP.getCode()))) <= 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR);
        }

        // 3. 更新SPU状态
        // 3.1. 检查一下活动是否被其他活动覆盖
        SpuDO existSpu = spuMapper.selectById(groupShopDO.getSpuId());
        if (existSpu.getActivityType() == SpuActivityType.GROUP_SHOP.getCode()) {
            // 3.2.执行状态修改
            SpuDO updateSpuDO = new SpuDO();
            updateSpuDO.setId(groupShopDO.getSpuId());
            updateSpuDO.setActivityType(SpuActivityType.NONE.getCode());
            updateSpuDO.setGmtUpdate(LocalDateTime.now());
            spuMapper.updateById(updateSpuDO);
        }
        // 4. 清理缓存
        this.clearCache(groupShopDO.getSpuId());
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(Long id, Long spuId, LocalDateTime gmtStart, LocalDateTime gmtEnd, Integer minimumNumber, Integer automaticRefund, String groupShopSkuListStr, Long adminId) throws ServiceException {
        // 1.转化为对应的GroupShopSkuDO类的链表
        List<GroupShopSkuDTO> groupShopSkuDOList = JacksonUtil.parseArray(groupShopSkuListStr, GroupShopSkuDTO.class)
                .stream().sorted(Comparator.comparingInt(GroupShopSkuDTO::getSkuGroupShopPrice)).collect(Collectors.toList());
        // 2校验参数
        // 2.1.校验过期
        if (gmtStart.isAfter(gmtEnd)) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_START_MUST_LESS_THAN_END);
        }

        // 2.2.校验团购
        GroupShopDO groupShopDO = groupShopMapper.selectById(id);
        if (groupShopDO == null) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXIST);
        }

        if (groupShopDO.getStatus() > 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_ALREADY_ACTIVE);
        }

        // 2.2. 校验SPU
        SpuDO spuDO = spuMapper.selectById(spuId);
        List<SkuDO> skuDOList = skuMapper.selectList((new QueryWrapper<SkuDO>().eq("spu_id", spuId)));
        if (spuDO == null || CollectionUtils.isEmpty(skuDOList)) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXIST_OR_ONLY_SPU);
        }

        if (skuDOList.size() != groupShopSkuDOList.size()) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SKU_NUMBER_ERROR);
        }

        // 3. 修改团购信息
        // 3.1. 修改主表
        LocalDateTime now = LocalDateTime.now();
        groupShopDO.setMinNum(minimumNumber);
        groupShopDO.setGmtStart(gmtStart);
        groupShopDO.setGmtEnd(gmtEnd);
        groupShopDO.setAutomaticRefund(automaticRefund.compareTo(0) > 0 ? StatusType.ACTIVE.getCode() : StatusType.LOCK.getCode());
        groupShopDO.setSpuId(spuId);
        groupShopDO.setMinPrice(groupShopSkuDOList.get(0).getSkuGroupShopPrice());
        groupShopDO.setMinPrice(groupShopSkuDOList.get(groupShopSkuDOList.size() - 1).getSkuGroupShopPrice());
        groupShopDO.setGmtUpdate(now);
        groupShopDO.setStatus(StatusType.LOCK.getCode());
        if (groupShopMapper.updateById(groupShopDO) <= 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SPU_UPDATE_SQL_QUERY_ERROR);
        }
        // 3.2.插入groupShopSkuList(即修改活动商品价格表)
        // 3.2.1. 删除旧有活动商品价格
        if (skuActivityPriceMapper.delete((
                new QueryWrapper<SkuActivityPriceDO>()
                        .eq("activity_id", id)
                        .eq("activity_type", SpuActivityType.GROUP_SHOP.getCode()))) <= 0) {
            throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR);
        }
        // 3.3.2. 插入新的活动商品价格
        this.insertGroupShopSkuList(groupShopSkuDOList, skuDOList, groupShopDO.getId());
        // 4.修改商品活动信息
        SpuDO updateSpuDO = new SpuDO();
        updateSpuDO.setId(spuId);
        updateSpuDO.setActivityType(SpuActivityType.GROUP_SHOP.getCode());
        updateSpuDO.setActivityId(groupShopDO.getId());
        updateSpuDO.setGmtActivityStart(gmtStart);
        updateSpuDO.setGmtActivityEnd(gmtEnd);
        updateSpuDO.setGmtUpdate(now);
        spuMapper.updateById(updateSpuDO);
        // 5.清理缓存
        this.clearCache(spuId);
        return "ok";
    }

    @Override
    public Page<GroupShopDTO> list(Long id, String spuName, Integer status, Integer page, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper<GroupShopDO> queryWrapper = new QueryWrapper<GroupShopDO>();
        if (id != null) {
            queryWrapper.eq("id", id);
        }
        if (status != null) {
            queryWrapper.eq("status", status);
        }

        if (!StringUtils.isEmpty(spuName)) {
            List<SpuDO> objectList = spuMapper.selectList(
                    new QueryWrapper<SpuDO>()
                            .select("id")
                            .like("title", spuName));
            List<Long> spuIds = objectList.stream().map(SpuDO::getId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(objectList)) {
                return new Page<>(new ArrayList<>(), page, limit, 0);
            }
            queryWrapper.in("spu_id", spuIds);
        }

        Page<GroupShopDTO> groupShopDTOPage = groupShopMapper
                .selectPage(Page.div(page, limit, GroupShopDO.class), queryWrapper).trans(groupShopDO -> {
                    GroupShopDTO groupShopDTO = new GroupShopDTO();
                    SpuDO spuDO = spuMapper.selectById(groupShopDO.getSpuId());
                    BeanUtils.copyProperties(spuDO, groupShopDTO);
                    BeanUtils.copyProperties(groupShopDO, groupShopDTO);
                    /**
                     * 添加groupShopSkuDTOList
                     */
                    Map<Long, SkuDO> skuMap = skuMapper.selectList((new QueryWrapper<SkuDO>().eq("spu_id", spuDO.getId()))).stream().collect(Collectors.toMap(SkuDO::getId, v -> v));
                    List<SkuActivityPriceDO> skuActivityPriceDOList = skuActivityPriceMapper.selectList((
                            new QueryWrapper<SkuActivityPriceDO>())
                            .eq("activity_id", groupShopDO.getId())
                            .eq("activity_type", SpuActivityType.GROUP_SHOP.getCode()));
                    List<GroupShopSkuDTO> groupShopSkuDTOList = skuActivityPriceDOList.stream().map(item -> {
                        GroupShopSkuDTO groupShopSkuDTO = new GroupShopSkuDTO();
                        groupShopSkuDTO.setSkuId(item.getSkuId());
                        groupShopSkuDTO.setGroupShopId(item.getActivityId());
                        groupShopSkuDTO.setSkuGroupShopPrice(item.getActivityPrice());
                        SkuDO skuDO = skuMap.get(item.getSkuId());
                        BeanUtils.copyProperties(skuDO, groupShopSkuDTO);
                        groupShopSkuDTO.setTitle(skuDO.getTitle());
                        return groupShopSkuDTO;
                    }).sorted((o1, o2) -> (int)(o1.getSkuId().longValue() - o2.getSkuId().longValue())).collect(Collectors.toList());
                    groupShopDTO.setGroupShopSkuDTOList(groupShopSkuDTOList);
                    return groupShopDTO;
                });
        return groupShopDTOPage;
    }

    private void insertGroupShopSkuList(List<GroupShopSkuDTO> groupShopSkuDTOList, List<SkuDO> skuDOList, Long groupShopId) throws ServiceException {
        LocalDateTime now = LocalDateTime.now();
        for (GroupShopSkuDTO groupShopSkuDTO : groupShopSkuDTOList) {
            boolean judge = false;
            for (SkuDO sku : skuDOList) {
                if (sku.getId().equals(groupShopSkuDTO.getSkuId())) {
                    judge = true;
                    break;
                }
            }

            if (!judge) {
                throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SKU_ID_ERROR);
            }

            if (groupShopSkuDTO.getSkuGroupShopPrice() == null || groupShopSkuDTO.getSkuGroupShopPrice().equals(0)) {
                throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SKU_PRICE_ERROR);
            }
            // 插入到商品活动价表
            SkuActivityPriceDO skuActivityPriceDO = new SkuActivityPriceDO();
            skuActivityPriceDO.setActivityType(SpuActivityType.GROUP_SHOP.getCode());
            skuActivityPriceDO.setActivityId(groupShopId);
            skuActivityPriceDO.setActivityPrice(groupShopSkuDTO.getSkuGroupShopPrice());
            skuActivityPriceDO.setGmtUpdate(now);
            skuActivityPriceDO.setGmtCreate(now);
            skuActivityPriceDO.setSkuId(groupShopSkuDTO.getSkuId());
            if (skuActivityPriceMapper.insert(skuActivityPriceDO) <= 0) {
                throw new ServiceException(ExceptionDefinition.GROUP_SHOP_SKU_ADD_SQL_QUERY_ERROR);
            }
        }
    }

    /**
     * 清除团购列表缓存
     */
    private void clearCache(Long spuId) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
//                cacheComponent.delPrefixKey(CacheConst.PRT_GROUP_SHOP_LIST);
                // TODO 别忘记刷新商品缓存
//                cacheComponent.delHashKey(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId);
            }
        });
    }

}
