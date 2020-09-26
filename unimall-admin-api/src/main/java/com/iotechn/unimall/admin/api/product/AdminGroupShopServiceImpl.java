package com.iotechn.unimall.admin.api.product;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.GroupShopDO;
import com.iotechn.unimall.data.domain.SkuActivityPriceDO;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.goods.GroupShopDTO;
import com.iotechn.unimall.data.dto.goods.GroupShopSkuDTO;
import com.iotechn.unimall.data.enums.GroupShopAutomaticRefundType;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.GroupShopMapper;
import com.iotechn.unimall.data.mapper.SkuActivityPriceMapper;
import com.iotechn.unimall.data.mapper.SkuMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/*
@PackageName:com.iotechn.unimall.admin.api.goods
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
    public String create(Long spuId, Long gmtStart, Long gmtEnd, Integer minNum, Integer automaticRefund, String groupShopSkuListStr, Long adminId) throws ServiceException {
        // 1.转化为对应的GroupShopSkuDTO类的链表
        List<GroupShopSkuDTO> groupShopSkuDTOList = JSONObject.parseArray(groupShopSkuListStr, GroupShopSkuDTO.class)
                .stream().sorted(Comparator.comparingInt(GroupShopSkuDTO::getSkuGroupShopPrice)).collect(Collectors.toList());

        if (gmtStart.compareTo(gmtEnd) >= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_START_MUST_LESS_THAN_END);
        }

        // 2.1 检验数据库中是否存在spuId对应的sku
        SpuDO spuDO = spuMapper.selectById(spuId);
        List<SkuDO> skuDOList = skuMapper.selectList((new QueryWrapper<SkuDO>().eq("spu_id", spuId)));
        if (spuDO == null || CollectionUtils.isEmpty(skuDOList)) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXITS_OR_ONLY_SPU);
        }
        // 2.2 检验groupShop表中是否存在此商品
        Integer count = groupShopMapper.selectCount((new QueryWrapper<GroupShopDO>().eq("spu_id", spuId)));
        if (count > 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_ALREADY_EXIT);
        }
        // 2.3 校验商品是否还有未存在的活动
        if (spuDO.getActivityId() != null && spuDO.getActivityType() != null
                && spuDO.getActivityType() != SpuActivityType.NONE.getCode()
                && spuDO.getGmtActivityEnd() != null && spuDO.getGmtActivityEnd().getTime() > System.currentTimeMillis()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_SPU_EXIST_ACTIVITY);
        }

        // 3.创建团购活动信息
        // 3.1.插入主表
        Date timeStart = new Date(gmtStart);
        Date timeEnd = new Date(gmtEnd);
        Date now = new Date();
        GroupShopDO groupShopDO = new GroupShopDO();

        groupShopDO.setBuyerNum(0);
        groupShopDO.setMinNum(minNum);
        groupShopDO.setGmtStart(timeStart);
        groupShopDO.setGmtEnd(timeEnd);
        groupShopDO.setAutomaticRefund(automaticRefund.compareTo(0) > 0 ? GroupShopAutomaticRefundType.YES.getCode() : GroupShopAutomaticRefundType.NO.getCode());
        groupShopDO.setSpuId(spuId);
        groupShopDO.setMinPrice(groupShopSkuDTOList.get(0).getSkuGroupShopPrice());
        groupShopDO.setMaxPrice(groupShopSkuDTOList.get(groupShopSkuDTOList.size() - 1).getSkuGroupShopPrice());
        groupShopDO.setGmtCreate(now);
        groupShopDO.setGmtUpdate(now);
        // 由活动开始定时任务更改其状态
        groupShopDO.setStatus(StatusType.LOCK.getCode());
        if (groupShopMapper.insert(groupShopDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_ADD_SQL_QUERY_ERROR);
        }

        if (skuDOList.size() != groupShopSkuDTOList.size()) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_NUMBER_ERROR);
        }
        // 3.2 插入groupShopSkuList（插入从表）
        this.insertGroupShopSkuList(groupShopSkuDTOList, skuDOList, groupShopDO.getId(), now);
        // 4.修改商品表活动信息
        SpuDO updateSpuDO = new SpuDO();
        updateSpuDO.setId(spuId);
        updateSpuDO.setActivityType(SpuActivityType.GROUP_SHOP.getCode());
        updateSpuDO.setActivityId(groupShopDO.getId());
        updateSpuDO.setGmtActivityStart(timeStart);
        updateSpuDO.setGmtActivityEnd(timeEnd);
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
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXITS_OR_ONLY_SPU);
        }

        if (groupShopDO.getStatus() > 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_ALREADY_ACTIVE);
        }

        // 2. 删除团购
        if (groupShopMapper.deleteById(id) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_DELETE_SQL_QUERY_ERROR);
        }
        if (skuActivityPriceMapper.delete((
                new QueryWrapper<SkuActivityPriceDO>()
                        .eq("activity_id", id)
                        .eq("activity_type", SpuActivityType.GROUP_SHOP.getCode()))) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR);
        }

        // 3. 更新SPU状态
        // 3.1. 检查一下活动是否被其他活动覆盖
        SpuDO existSpu = spuMapper.selectById(groupShopDO.getSpuId());
        if (existSpu.getActivityType() == SpuActivityType.GROUP_SHOP.getCode()) {
            // 3.2.执行状态修改
            SpuDO updateSpuDO = new SpuDO();
            updateSpuDO.setId(groupShopDO.getSpuId());
            updateSpuDO.setActivityType(SpuActivityType.NONE.getCode());
            updateSpuDO.setGmtUpdate(new Date());
            spuMapper.updateById(updateSpuDO);
        }
        // 4. 清理缓存
        this.clearCache(groupShopDO.getSpuId());
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupShopDTO edit(Long id, Long spuId, Long gmtStart, Long gmtEnd, Integer minimumNumber, Integer automaticRefund, String groupShopSkuListStr, Long adminId) throws ServiceException {
        // 1.转化为对应的GroupShopSkuDO类的链表
        List<GroupShopSkuDTO> groupShopSkuDOList = JSONObject.parseArray(groupShopSkuListStr, GroupShopSkuDTO.class)
                .stream().sorted(Comparator.comparingInt(GroupShopSkuDTO::getSkuGroupShopPrice)).collect(Collectors.toList());
        // 2校验参数
        // 2.1.校验过期
        if (gmtStart.compareTo(gmtEnd) >= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_START_MUST_LESS_THAN_END);
        }

        // 2.2.校验团购
        GroupShopDO groupShopDO = groupShopMapper.selectById(id);
        if (groupShopDO == null) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXITS);
        }

        if (groupShopDO.getStatus() > 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_ALREADY_ACTIVE);
        }

        // 2.2. 校验SPU
        SpuDO spuDO = spuMapper.selectById(spuId);
        List<SkuDO> skuDOList = skuMapper.selectList((new QueryWrapper<SkuDO>().eq("spu_id", spuId)));
        if (spuDO == null || CollectionUtils.isEmpty(skuDOList)) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXITS_OR_ONLY_SPU);
        }

        if (skuDOList.size() != groupShopSkuDOList.size()) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_NUMBER_ERROR);
        }

        // 3. 修改团购信息
        // 3.1. 修改主表
        Date timeStart = new Date(gmtStart);
        Date timeEnd = new Date(gmtEnd);
        Date now = new Date();
        groupShopDO.setMinNum(minimumNumber);
        groupShopDO.setGmtStart(timeStart);
        groupShopDO.setGmtEnd(timeEnd);
        groupShopDO.setAutomaticRefund(automaticRefund.compareTo(0) > 0 ? StatusType.ACTIVE.getCode() : StatusType.LOCK.getCode());
        groupShopDO.setSpuId(spuId);
        groupShopDO.setMinPrice(groupShopSkuDOList.get(0).getSkuGroupShopPrice());
        groupShopDO.setMinPrice(groupShopSkuDOList.get(groupShopSkuDOList.size() - 1).getSkuGroupShopPrice());
        groupShopDO.setGmtUpdate(now);
        groupShopDO.setStatus(StatusType.LOCK.getCode());
        if (groupShopMapper.updateById(groupShopDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_UPDATE_SQL_QUERY_ERROR);
        }
        // 3.2.插入groupShopSkuList(即修改活动商品价格表)
        // 3.2.1. 删除旧有活动商品价格
        if (skuActivityPriceMapper.delete((
                new QueryWrapper<SkuActivityPriceDO>()
                        .eq("activity_id", id)
                        .eq("activity_type", SpuActivityType.GROUP_SHOP.getCode()))) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR);
        }
        // 3.3.2. 插入新的活动商品价格
        this.insertGroupShopSkuList(groupShopSkuDOList, skuDOList, groupShopDO.getId(), now);
        // 4.修改商品活动信息
        SpuDO updateSpuDO = new SpuDO();
        updateSpuDO.setId(spuId);
        updateSpuDO.setActivityType(SpuActivityType.GROUP_SHOP.getCode());
        updateSpuDO.setActivityId(groupShopDO.getId());
        updateSpuDO.setGmtActivityStart(timeStart);
        updateSpuDO.setGmtActivityEnd(timeEnd);
        updateSpuDO.setGmtUpdate(now);
        spuMapper.updateById(updateSpuDO);
        // 5.清理缓存
        this.clearCache(spuId);
        return new GroupShopDTO();
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

    private void insertGroupShopSkuList(List<GroupShopSkuDTO> groupShopSkuDTOList, List<SkuDO> skuDOList, Long groupShopId, Date now) throws ServiceException {
        for (GroupShopSkuDTO groupShopSkuDTO : groupShopSkuDTOList) {
            boolean judge = false;
            for (SkuDO sku : skuDOList) {
                if (sku.getId().equals(groupShopSkuDTO.getSkuId())) {
                    judge = true;
                    break;
                }
            }

            if (!judge) {
                throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_ID_ERROR);
            }

            if (groupShopSkuDTO.getSkuGroupShopPrice() == null || groupShopSkuDTO.getSkuGroupShopPrice().equals(0)) {
                throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_PRICE_ERROR);
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
                throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_ADD_SQL_QUERY_ERROR);
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
                cacheComponent.delPrefixKey(CacheConst.PRT_GROUP_SHOP_LIST);
                cacheComponent.delHashKey(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId);
            }
        });
    }

}
