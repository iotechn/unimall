package com.iotechn.unimall.admin.api.product;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.goods.AdminSpuDTO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.dto.goods.SpuTreeNodeDTO;
import com.iotechn.unimall.data.enums.AdvertUnionType;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.enums.SpuStatusType;
import com.iotechn.unimall.data.mapper.*;
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

/**
 * Created by rize on 2019/7/11.
 */
@Service
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AdvertMapper advertMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuAttributeMapper spuAttributeMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SpuSpecificationMapper spuSpecificationMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    @Autowired
    private CacheComponent cacheComponent;

    /**
     * 后台低频接口，无需缓存，用于选择商品，需要
     * @return
     * @throws ServiceException
     */
    public List<SpuTreeNodeDTO> getSpuBigTree(Long adminId) throws ServiceException {
        List<CategoryDO> categoryDOList = categoryMapper.selectList(
                new QueryWrapper<CategoryDO>()
                        .orderByAsc("level"));
        Map<Long, List<SpuDO>> spuMap = spuMapper.selectList(
                new QueryWrapper<SpuDO>()
                        .select("id", "title", "category_id")).stream().collect(Collectors.groupingBy(SpuDO::getCategoryId));
        List<SpuTreeNodeDTO> list = new ArrayList<>();
        Integer recordLevelOne = 0;
        for (int i = 0; i < categoryDOList.size(); i++) {
            if (i != 0 && categoryDOList.get(i - 1).getLevel().equals(0) && categoryDOList.get(i).getLevel().equals(1)) {
                recordLevelOne = i;
            }
        }

        for (int i = 0; i < recordLevelOne; i++) {
            //1. 一级分类
            CategoryDO categoryOnI = categoryDOList.get(i);
            SpuTreeNodeDTO dtoOnI = new SpuTreeNodeDTO();
            dtoOnI.setLabel(categoryOnI.getTitle());
            dtoOnI.setValue("C_" + categoryOnI.getId());
            dtoOnI.setId(categoryOnI.getId());
            dtoOnI.setChildren(transSpu(spuMap.get(categoryOnI.getId())));
            list.add(dtoOnI);
            for (int j = recordLevelOne; j < categoryDOList.size(); j++) {
                //2.1. 将分类商品添加上去
                //2.2. 二级分类（一级子分类）
                CategoryDO categoryOnJ = categoryDOList.get(j);
                if (!categoryOnJ.getParentId().equals(dtoOnI.getId())) {
                    continue;
                }
                SpuTreeNodeDTO dtoOnJ = new SpuTreeNodeDTO();
                dtoOnJ.setLabel(categoryOnJ.getTitle());
                dtoOnJ.setValue("C_" + categoryOnJ.getId());
                dtoOnJ.setId(categoryOnJ.getId());
                dtoOnJ.setChildren(transSpu(spuMap.get(categoryOnJ.getId())));
                dtoOnI.getChildren().add(dtoOnJ);
            }
        }
        return list;
    }

    private List<SpuTreeNodeDTO> transSpu(List<SpuDO> list) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(item -> {
            SpuTreeNodeDTO temp = new SpuTreeNodeDTO();
            temp.setValue("G_" + item.getId());
            temp.setId(item.getId());
            temp.setLabel(item.getTitle());
            return temp;
        }).collect(Collectors.toList());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(AdminSpuDTO adminSpuDTO, Long adminId) throws ServiceException {
        // 参数校验
        if (adminSpuDTO.getId() != null) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_CREATE_HAS_ID);
        }
        if (CollectionUtils.isEmpty(adminSpuDTO.getSkuList())) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_SKU_LIST_EMPTY);
        }
        if (adminSpuDTO.getOriginalPrice() < adminSpuDTO.getPrice() || adminSpuDTO.getPrice() < adminSpuDTO.getVipPrice() || adminSpuDTO.getOriginalPrice() < adminSpuDTO.getVipPrice()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
        }
        // 校验Sku是否重复
        Set<String> barCodes = adminSpuDTO.getSkuList().stream().map(item -> item.getBarCode()).collect(Collectors.toSet());
        if (barCodes.size() != adminSpuDTO.getSkuList().size()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_UPLOAD_SKU_BARCODE_REPEAT);
        }
        List<SkuDO> existSkuDO = skuMapper.selectList(new QueryWrapper<SkuDO>().in("bar_code", barCodes));
        if (!CollectionUtils.isEmpty(existSkuDO)) {
            String spuIds = existSkuDO.stream().map(item -> item.getSpuId().toString()).collect(Collectors.joining(","));
            String skuIds = existSkuDO.stream().map(item -> item.getBarCode()).collect(Collectors.joining(","));
            throw new AdminServiceException(ExceptionDefinition
                    .buildVariableException(ExceptionDefinition.GOODS_CREATE_BARCODE_REPEAT, spuIds, skuIds));
        }
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(adminSpuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuDO.setGmtCreate(now);
        spuDO.setSales(0);
        spuMapper.insert(spuDO);
        adminSpuDTO.setId(spuDO.getId());
        // 插入SKU表
        for (SkuDO skuDO : adminSpuDTO.getSkuList()) {
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
            }
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            skuDO.setGmtCreate(now);
            skuMapper.insert(skuDO);
        }
        // 插入spuAttr
        insertSpuAttribute(adminSpuDTO, now);
        // 插入IMG
        insertSpuImg(adminSpuDTO, spuDO.getId(), now);
        // 1. 创建商品缓存
        this.createSpuCache(spuDO);
        // 2. 创建Sku库存缓存
        for (SkuDO skuDO : adminSpuDTO.getSkuList()) {
            cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
        }
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(AdminSpuDTO spuDTO, Long adminId) throws ServiceException {
        // 将旧值查询出来，若价格没有改变，则无需更新价格排序缓存
        if (spuDTO.getId() == null) {
            throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        if (CollectionUtils.isEmpty(spuDTO.getSkuList())) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_SKU_LIST_EMPTY);
        }
        if (spuDTO.getOriginalPrice() < spuDTO.getPrice() || spuDTO.getPrice() < spuDTO.getVipPrice() || spuDTO.getOriginalPrice() < spuDTO.getVipPrice()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
        }
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuMapper.updateById(spuDO);
        Set<String> barCodes = new HashSet<>();
        for (SkuDO skuDO : spuDTO.getSkuList()) {
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
            }
            skuDO.setId(null);
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            if (skuMapper.update(skuDO,
                    new QueryWrapper<SkuDO>()
                            .eq("bar_code", skuDO.getBarCode())) <= 0) {
                skuDO.setGmtCreate(now);
                skuMapper.insert(skuDO);
            }
            boolean succ = barCodes.add(skuDO.getBarCode());
            if (!succ) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_UPLOAD_SKU_BARCODE_REPEAT);
            }
        }
        // 删除多余barCode
        skuMapper.delete(new QueryWrapper<SkuDO>().eq("spu_id", spuDO.getId()).notIn("bar_code", barCodes));
        // 插入spuAttr
        spuAttributeMapper.delete(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuDTO.getId()));
        insertSpuAttribute(spuDTO, now);
        imgMapper.delete(new QueryWrapper<ImgDO>().eq("biz_id", spuDO.getId()).eq("biz_type", BizType.GOODS.getCode()));
        // 插入IMG
        insertSpuImg(spuDTO, spuDO.getId(), now);
        // 对于更新缓存，需要在事务提交后更新，防止事务提交期间产生读请求，将旧值覆盖到新值上
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 1. 放入类目缓存
                List<Long> categoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
                for (Long categoryId : categoryFamily) {
                    cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + categoryId, spuDO.getPrice(), "P" + spuDO.getId());
                }
            }
        });
        return "ok";
    }

    private void insertSpuAttribute(AdminSpuDTO spuDTO, Date now) {
        if (!CollectionUtils.isEmpty(spuDTO.getAttributeList())) {
            for (SpuAttributeDO spuAttributeDO : spuDTO.getAttributeList()) {
                spuAttributeDO.setSpuId(spuDTO.getId());
                spuAttributeDO.setGmtUpdate(now);
                spuAttributeDO.setGmtCreate(now);
                spuAttributeMapper.insert(spuAttributeDO);
            }
        }
    }

    private void insertSpuImg(AdminSpuDTO spuDTO, Long bizId, Date now) {
        List<String> imgList = spuDTO.getImgList();
        List<ImgDO> imgDOList = imgList.stream().map(item -> {
            ImgDO imgDO = new ImgDO();
            imgDO.setBizType(BizType.GOODS.getCode());
            imgDO.setBizId(bizId);
            imgDO.setUrl(item);
            imgDO.setGmtCreate(now);
            imgDO.setGmtUpdate(now);
            return imgDO;
        }).collect(Collectors.toList());
        imgMapper.insertImgs(imgDOList);
    }

    @Override
    public Page<SpuDTO> list(Integer page, Integer limit, Long categoryId, String title, String barcode, Integer status, Long adminId) throws ServiceException {
        QueryWrapper<SpuDO> wrapper = new QueryWrapper<SpuDO>().select(ProductBizService.SPU_EXCLUDE_DETAIL_FIELDS);
        // 1.标题搜索
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        // 2.类目搜索
        if (categoryId != null && categoryId != 0L) {
            List<Long> categorySelfAndChildren = categoryBizService.getCategorySelfAndChildren(categoryId);
            wrapper.in("category_id", categorySelfAndChildren);
        }
        // 3.状态搜索
        if (status != null) {
            wrapper.eq("status", status.intValue() <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode());
        }
        // 3.条码搜索
        if (!StringUtils.isEmpty(barcode)) {
            List<SkuDO> skuDOList = skuMapper.selectList(new QueryWrapper<SkuDO>().eq("bar_code", barcode));
            if (!CollectionUtils.isEmpty(skuDOList)) {
                SkuDO skuDO = skuDOList.get(0);
                wrapper.eq("id", skuDO.getSpuId());
            }
        }
        Page<SpuDTO> dtoPage = spuMapper.selectPage(Page.div(page, limit, SpuDO.class), wrapper).trans(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            List<SkuDO> skuDOList = skuMapper.selectList(new QueryWrapper<SkuDO>().eq("spu_id", item.getId()));
            spuDTO.setSkuList(skuDOList);
            return spuDTO;
        });
        return dtoPage;
    }

    @Override
    public AdminSpuDTO detail(Long spuId, Long adminId) throws ServiceException {
        SpuDO spuDO = spuMapper.selectById(spuId);
        AdminSpuDTO adminSpuDTO = new AdminSpuDTO();
        BeanUtils.copyProperties(spuDO, adminSpuDTO);
        // 0. imgList
        List<String> imgList = imgMapper.getImgs(BizType.GOODS.getCode(), spuId);
        adminSpuDTO.setImgList(imgList);
        // 1. skuList
        List<SkuDO> skuList = skuMapper.selectList(
                new QueryWrapper<SkuDO>()
                        .eq("spu_id", spuId));
        adminSpuDTO.setSkuList(skuList);;
        // 2. Spu属性
        List<SpuAttributeDO> attributeList = spuAttributeMapper.selectList(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        adminSpuDTO.setAttributeList(attributeList);
        // 3. categoryIds
        List<Long> categoryIds = categoryBizService.getCategoryFamily(adminSpuDTO.getCategoryId());
        adminSpuDTO.setCategoryIds(categoryIds);
        // 4. spu specificationList
        List<SpuSpecificationDO> specificationList = spuSpecificationMapper.selectList(
                new QueryWrapper<SpuSpecificationDO>()
                        .eq("spu_id", spuId));
        adminSpuDTO.setSpecificationList(specificationList);
        return adminSpuDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long spuId, Long adminId) throws ServiceException {
        // 校验是否有关联此商品的广告
        SpuDO spuDOBefore = spuMapper.selectById(spuId);
        // 校验是否有关联广告
        this.checkUnionAdvert(Arrays.asList(spuId.toString()));
        // 校验是否有不可下架的活动
        this.checkProductActivity(spuDOBefore);
        if (spuMapper.deleteById(spuId) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        cartMapper.delete(new QueryWrapper<CartDO>().in("sku_id", skuMapper.getSkuIds(spuId)));
        skuMapper.delete(new QueryWrapper<SkuDO>().eq("spu_id", spuId));
        imgMapper.delete(new QueryWrapper<ImgDO>().eq("biz_id", spuId).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                AdminProductServiceImpl.this.deleteSpuCache(spuDOBefore);
            }
        });
        return "ok";
    }

    private void checkUnionAdvert(List<String> ids) throws ServiceException {
        List<AdvertDO> list1 = advertMapper.selectList(
                new QueryWrapper<AdvertDO>()
                        .eq("union_type", AdvertUnionType.PRODUCT.getCode())
                        .in("union_value", ids).last("LIMIT 1"));
        if (!CollectionUtils.isEmpty(list1)) {
            throw new AdminServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.GOODS_EXIST_ADVERT, list1.get(0).getTitle()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchDelete(String idsJson, Long adminId) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        // 校验是否有广告关联
        List<String> idStrList = ids.stream().map(item -> item.toString()).collect(Collectors.toList());
        this.checkUnionAdvert(idStrList);
        List<SpuDO> beforeSpuList = spuMapper.selectBatchIds(ids);
        // 校验商品是否有不可下架的活动
        for (SpuDO spuDO : beforeSpuList) {
            this.checkProductActivity(spuDO);
        }
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        if (spuMapper.delete(new QueryWrapper<SpuDO>().in("id", ids)) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        List<Long> skuIds = skuMapper.selectSkuIdsBySpuIds(ids);
        cartMapper.delete(new QueryWrapper<CartDO>().in("sku_id", skuIds));
        skuMapper.delete(new QueryWrapper<SkuDO>().in("spu_id", ids));
        imgMapper.delete(new QueryWrapper<ImgDO>().in("biz_id", ids).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new QueryWrapper<SpuAttributeDO>().in("spu_id", ids));
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                for (SpuDO spuDOBefore : beforeSpuList) {
                    AdminProductServiceImpl.this.deleteSpuCache(spuDOBefore);
                }
            }
        });
        return "ok";
    }

    @Override
    public String rebuildProductCache(Long adminId) throws ServiceException {
        // 1.删除旧缓存
        cacheComponent.delPrefixKey(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET);
        cacheComponent.delPrefixKey(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET);
        cacheComponent.delPrefixKey(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET);
        cacheComponent.delPrefixKey(CacheConst.PRT_SKU_STOCK_BUCKET);
        cacheComponent.delPrefixKey(CacheConst.PRT_SPU_DETAIL_HASH_BUCKET);
        cacheComponent.delPrefixKey(CacheConst.PRT_SPU_HASH_BUCKET);
        // 2.重建新缓存
        List<SpuDO> spuDOS = spuMapper.selectList(new QueryWrapper<>());
        for (SpuDO spuDO : spuDOS) {
            // 2.1. 重建主表缓存
            this.createSpuCache(spuDO);
            // 2.2. 重建SKU缓存
            List<SkuDO> skuList = skuMapper.selectList(new QueryWrapper<SkuDO>().eq("spu_id", spuDO.getId()));
            for (SkuDO skuDO : skuList) {
                cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
            }
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdminSpuDTO freezeOrActivation(Long spuId, Integer status, Long adminId) throws ServiceException {
        SpuDO spuDO = spuMapper.selectById(spuId);
        if (spuDO == null) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        status = status <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode();
        // 不可下架活动校验
        if (status == SpuStatusType.STOCK.getCode()) {
            this.checkProductActivity(spuDO);
        }
        if (spuDO.getStatus().intValue() == status.intValue()) {
            throw new AdminServiceException(ExceptionDefinition.SYSTEM_BUSY);
        }
        spuDO.setStatus(status);
        spuDO.setGmtUpdate(new Date());
        if (spuMapper.updateById(spuDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        AdminSpuDTO spuDTO = new AdminSpuDTO();
        BeanUtils.copyProperties(spuDO, spuDTO);
        List<SkuDO> skuDOList = skuMapper.selectList(new QueryWrapper<SkuDO>().eq("spu_id", spuDO.getId()));
        spuDTO.setSkuList(skuDOList);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                if (spuDO.getStatus() > 0) {
                    //1. 重建商品列表缓存
                    AdminProductServiceImpl.this.createSpuCache(spuDO);
                    //2. 重建Sku库存缓存
                    for (SkuDO skuDO : spuDTO.getSkuList()) {
                        cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
                    }
                } else {
                    // 删除商品列表缓存
                    AdminProductServiceImpl.this.deleteSpuCache(spuDO);
                }
            }
        });
        return spuDTO;
    }

    /**
     * 校验SPU是否有团购这样的，不允许删除或下架的活动
     * @param spuDO
     * @throws ServiceException
     */
    private void checkProductActivity(SpuDO spuDO) throws ServiceException {
        if (spuDO.getActivityType() != null && spuDO.getActivityType() == SpuActivityType.GROUP_SHOP.getCode()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_UNION_ACTIVITY_CAN_NOT_BE_OFF_SHELF);
        }
    }

    /**
     * 1.放入各个类目ZSET
     * 2.放入基本信息Hash表
     * 3.不放入详细信息Hash表，读取时再放入缓存
     * @param spuDO
     */
    private void createSpuCache(SpuDO spuDO) {
        // 1. 放入类目缓存
        List<Long> categoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
        for (Long categoryId : categoryFamily) {
            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + categoryId, spuDO.getId(), "P" + spuDO.getId());
            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + categoryId, spuDO.getPrice(), "P" + spuDO.getId());
            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + categoryId, 0, "P" + spuDO.getId());
        }
        // 2. 放入Hash表中
        spuDO.setDetail(null);
        SpuDTO newSpuDTO = new SpuDTO();
        BeanUtils.copyProperties(spuDO, newSpuDTO);
        newSpuDTO.setCategoryIds(categoryFamily);
        cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuDO.getId(), newSpuDTO);
    }

    /**
     * 1.删除各个类目ZSET
     * 2.删除基本信息Hash表
     * 3.删除商品详情信息Hash表
     * @param spuDO
     */
    private void deleteSpuCache(SpuDO spuDO) {
        // 1. 删除各个类目ZSET
        List<Long> categoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
        for (Long categoryId : categoryFamily) {
            cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + categoryId, "P" + spuDO.getId());
            cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + categoryId, "P" + spuDO.getId());
            cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + categoryId, "P" + spuDO.getId());
        }
        // 2. 删除基本信息Hash表
        cacheComponent.delHashKey(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuDO.getId());
        // 3. 删除商品详情信息Hash表
        cacheComponent.delHashKey(CacheConst.PRT_SPU_DETAIL_HASH_BUCKET, "P" + spuDO.getId());
    }

}
