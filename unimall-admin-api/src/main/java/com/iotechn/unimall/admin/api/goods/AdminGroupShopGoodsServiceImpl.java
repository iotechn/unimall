package com.iotechn.unimall.admin.api.goods;/*
@PackageName:com.iotechn.unimall.admin.api.goods
@ClassName: AdminGroupShopGoodsServiceImpl
@Description:
@author kbq
@date 19-11-13下午4:21
*/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.admin.api.order.AdminOrderService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.goods.GroupShopSkuDTO;
import com.iotechn.unimall.data.dto.goods.GroupShopDTO;
import com.iotechn.unimall.data.enums.GroupShopAutomaticRefundType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class AdminGroupShopGoodsServiceImpl implements AdminGroupShopGoodsService {

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private GroupShopMapper groupShopMapper;

    @Autowired
    private GroupShopSkuMapper groupShopSkuMapper;

    @Autowired
    private CacheComponent cacheComponent;

    private static final String GROUP_SHOP_CACHE = "CA_GROUP_SHOP";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addGroupShopSpu(Long adminId, Long spuId, Long gmtStart, Long gmtEnd, Integer minimumNumber, Integer automaticRefund, List groupShopSkuList) throws ServiceException {
        // 1.转化为对应的GroupShopSkuDo类的链表
        List<GroupShopSkuDO> groupShopSkuDOList = (List<GroupShopSkuDO>) groupShopSkuList.stream().map(t -> {
            GroupShopSkuDO groupShopSkuDO = JSONObject.toJavaObject((JSON) t, GroupShopSkuDO.class);
            return groupShopSkuDO;
        }).sorted(Comparator.comparingInt(o -> ((GroupShopSkuDO) o).getSkuGroupShopPrice())).collect(Collectors.toList());

        if (gmtStart.compareTo(gmtEnd) >= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_START_MUST_LESS_THAN_END);
        }

        // 2.检验数据库中是否存在spuId对应的sku
        SpuDO spuDO = spuMapper.selectById(spuId);
        List<SkuDO> skuDOList = skuMapper.selectList((new EntityWrapper<SkuDO>().eq("spu_id", spuId)));
        if (spuDO == null || CollectionUtils.isEmpty(skuDOList)) {
            throw new AdminServiceException(ExceptionDefinition.SPU_NO_EXITS_OR_ONLY_SPU);
        }
        // 2.1 检验groupShop表中是否存在此商品
        Integer count = groupShopMapper.selectCount((new EntityWrapper<GroupShopDO>().eq("spu_id", spuId)));
        if (count > 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_ALREADY_EXIT);
        }

        Date timeStart = new Date(gmtStart);
        Date timeEnd = new Date(gmtEnd);
        Date now = new Date();
        GroupShopDO groupShopDO = new GroupShopDO();

        groupShopDO.setAlreadyBuyNumber(0);
        groupShopDO.setMinimumNumber(minimumNumber);
        groupShopDO.setGmtStart(timeStart);
        groupShopDO.setGmtEnd(timeEnd);
        groupShopDO.setAutomaticRefund(automaticRefund.compareTo(0) > 0 ? GroupShopAutomaticRefundType.YES.getCode() : GroupShopAutomaticRefundType.NO.getCode());
        groupShopDO.setSpuId(spuId);
        groupShopDO.setMinPrice(groupShopSkuDOList.get(0).getSkuGroupShopPrice());
        groupShopDO.setMaxPrice(groupShopSkuDOList.get(groupShopSkuDOList.size() - 1).getSkuGroupShopPrice());
        groupShopDO.setGmtCreate(now);
        groupShopDO.setGmtUpdate(now);
        // 3.设置是否在活动时间的状态
        setGroupShopStatus(now, gmtStart, gmtEnd, spuDO, groupShopDO);
        if (groupShopMapper.insert(groupShopDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_ADD_SQL_QUERY_ERROR);
        }

        if (skuDOList.size() != groupShopSkuDOList.size()) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_NUMBER_ERROR);
        }
        // 4.插入groupShopSkuList
        this.insertGroupShopSkuList(groupShopSkuDOList, skuDOList, groupShopDO.getId(), now);
        cacheComponent.delPrefixKey(GROUP_SHOP_CACHE);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteGroupShopSpu(Long adminId, Long id) throws ServiceException {

        GroupShopDO groupShopDO = groupShopMapper.selectById(id);
        if (groupShopDO == null) {
            throw new AdminServiceException(ExceptionDefinition.SPU_NO_EXITS_OR_ONLY_SPU);
        }

        if (groupShopDO.getStatus() > 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_ALREAD_ATCIVE);
        }

        if (groupShopMapper.deleteById(id) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_DELETE_SQL_QUERY_ERROR);
        }
        if (groupShopSkuMapper.delete((new EntityWrapper<GroupShopSkuDO>().eq("group_shop_id", id))) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR);
        }

        cacheComponent.delPrefixKey(GROUP_SHOP_CACHE);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GroupShopDTO editGroupShopSpu(Long adminId, Long id, Long spuId, Long gmtStart, Long gmtEnd, Integer minimumNumber, Integer automaticRefund, List groupShopSkuList) throws ServiceException {
        // 1.转化为对应的GroupShopSkuDo类的链表
        List<GroupShopSkuDO> groupShopSkuDOList = (List<GroupShopSkuDO>) groupShopSkuList.stream().map(t -> {
            GroupShopSkuDO groupShopSkuDO = JSONObject.toJavaObject((JSON) t, GroupShopSkuDO.class);
            return groupShopSkuDO;
        }).sorted(Comparator.comparingInt(o -> ((GroupShopSkuDO) o).getSkuGroupShopPrice())).collect(Collectors.toList());

        if (gmtStart.compareTo(gmtEnd) >= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_START_MUST_LESS_THAN_END);
        }

        SpuDO spuDO = spuMapper.selectById(spuId);
        List<SkuDO> skuDOList = skuMapper.selectList((new EntityWrapper<SkuDO>().eq("spu_id", spuId)));
        if (spuDO == null || CollectionUtils.isEmpty(skuDOList)) {
            throw new AdminServiceException(ExceptionDefinition.SPU_NO_EXITS_OR_ONLY_SPU);
        }

        GroupShopDO groupShopDO = groupShopMapper.selectById(id);
        if (groupShopDO == null) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_NO_EXITS);
        }

        if (groupShopDO.getStatus() > 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_ALREAD_ATCIVE);
        }

        Date timeStart = new Date(gmtStart);
        Date timeEnd = new Date(gmtEnd);
        Date now = new Date();

        groupShopDO.setMinimumNumber(minimumNumber);
        groupShopDO.setGmtStart(timeStart);
        groupShopDO.setGmtEnd(timeEnd);
        groupShopDO.setAutomaticRefund(automaticRefund.compareTo(0) > 0 ? StatusType.ACTIVE.getCode() : StatusType.LOCK.getCode());
        groupShopDO.setSpuId(spuId);
        groupShopDO.setMinPrice(groupShopSkuDOList.get(0).getSkuGroupShopPrice());
        groupShopDO.setMinPrice(groupShopSkuDOList.get(groupShopSkuDOList.size() - 1).getSkuGroupShopPrice());
        groupShopDO.setGmtUpdate(now);
        this.setGroupShopStatus(now, gmtStart, gmtEnd, spuDO, groupShopDO);
        if (groupShopMapper.updateById(groupShopDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SPU_UPDATE_SQL_QUERY_ERROR);
        }

        if (groupShopSkuMapper.delete((new EntityWrapper<GroupShopSkuDO>().eq("group_shop_id", id))) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_DELETE_SQL_QUERY_ERROR);
        }

        if (skuDOList.size() != groupShopSkuDOList.size()) {
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_NUMBER_ERROR);
        }
        // 4.插入groupShopSkuList
        this.insertGroupShopSkuList(groupShopSkuDOList, skuDOList, groupShopDO.getId(), now);
        cacheComponent.delPrefixKey(GROUP_SHOP_CACHE);
        return new GroupShopDTO();
    }

    @Override
    public Page<GroupShopDTO> queryGroupShop(Long adminId, Long id, String spuName, Integer status, Integer page, Integer limit) throws ServiceException {
        EntityWrapper<GroupShopDO> groupShopSpuDOEntityWrapper = new EntityWrapper<GroupShopDO>();
        if (id != null) {
            groupShopSpuDOEntityWrapper.eq("id", id);
        }

        if (status != null) {
            groupShopSpuDOEntityWrapper.eq("status", status);
        }

        if (spuName != null) {
            EntityWrapper<SpuDO> spuDOEntityWrapper = new EntityWrapper<>();
            spuDOEntityWrapper.setSqlSelect("id");
            spuDOEntityWrapper.like("title", spuName);
            List<Object> objectList = spuMapper.selectObjs(spuDOEntityWrapper);
            List<String> collect = objectList.stream().map(s -> String.valueOf(s)).collect(Collectors.toList());
            groupShopSpuDOEntityWrapper.in("spu_id", collect);
        }

        List<GroupShopDO> groupShopDOList = groupShopMapper.selectPage(new RowBounds((page - 1) * limit, limit), groupShopSpuDOEntityWrapper);
        Integer count = groupShopMapper.selectCount(groupShopSpuDOEntityWrapper);
        List<GroupShopDTO> groupShopDTOList = new LinkedList<>();

        for (GroupShopDO groupShopDO : groupShopDOList) {
            GroupShopDTO groupShopDTO = new GroupShopDTO();
            SpuDO spuDO = spuMapper.selectById(groupShopDO.getSpuId());
            BeanUtils.copyProperties(spuDO, groupShopDTO);
            BeanUtils.copyProperties(groupShopDO, groupShopDTO);
            /**
             * 添加groupShopSkuDTOList
             */
            List<SkuDO> skuDOList = skuMapper.selectList((new EntityWrapper<SkuDO>().eq("spu_id", spuDO.getId())));
            List<GroupShopSkuDO> groupShopSkuDOList = groupShopSkuMapper.selectList((new EntityWrapper<GroupShopSkuDO>()).eq("group_shop_id", groupShopDO.getId()));
            List<GroupShopSkuDTO> groupShopSkuDTOList = groupShopSkuDOList.stream().map(s -> {
                GroupShopSkuDTO groupShopSkuDTO = new GroupShopSkuDTO();
                BeanUtils.copyProperties(s, groupShopSkuDTO);
                return groupShopSkuDTO;
            }).collect(Collectors.toList());

            for (SkuDO skuDO : skuDOList) {
                for (GroupShopSkuDTO groupShopSkuDTO : groupShopSkuDTOList) {
                    if (groupShopSkuDTO.getSkuId().equals(skuDO.getId())) {
                        BeanUtils.copyProperties(skuDO, groupShopSkuDTO);
                        break;
                    }
                }
            }
            groupShopDTO.setGroupShopSkuDTOList(groupShopSkuDTOList);
            groupShopDTOList.add(groupShopDTO);
        }
        return new Page<GroupShopDTO>(groupShopDTOList, page, limit, count);
    }

    private void setGroupShopStatus(Date now, Long gmtStart, Long gmtEnd, SpuDO spuDO, GroupShopDO groupShopDO) {
        if (now.getTime() >= gmtStart.longValue() && now.getTime() < gmtEnd.longValue()) {
            //3.1 商品本身是否处于下架状态
            if (spuDO.getStatus().equals(StatusType.ACTIVE.getCode())) {
                groupShopDO.setStatus(StatusType.ACTIVE.getCode());
            } else {
                groupShopDO.setStatus(StatusType.LOCK.getCode());
            }
        } else {
            groupShopDO.setStatus(StatusType.LOCK.getCode());
        }
    }

    private void insertGroupShopSkuList(List<GroupShopSkuDO> groupShopSkuDOList, List<SkuDO> skuDOList, Long groupShopId, Date now) throws ServiceException {
        for (GroupShopSkuDO groupShopSkuDO : groupShopSkuDOList) {
            boolean judge = false;
            for (SkuDO sku : skuDOList) {
                if (sku.getId().equals(groupShopSkuDO.getSkuId())) {
                    judge = true;
                    break;
                }
            }

            if (!judge) {
                throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_ID_ERROR);
            }

            if (groupShopSkuDO.getSkuGroupShopPrice() == null || groupShopSkuDO.getSkuGroupShopPrice().equals(0)) {
                throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_PRICE_ERROR);
            }

            groupShopSkuDO.setGroupShopId(groupShopId);
            groupShopSkuDO.setGmtCreate(now);
            groupShopSkuDO.setGmtUpdate(now);
            if (groupShopSkuMapper.insert(groupShopSkuDO) <= 0) {
                throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_SKU_ADD_SQL_QUERY_ERROR);
            }
        }
    }

}
