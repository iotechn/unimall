package com.iotechn.unimall.admin.api.product;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.AdminServiceException;
import com.dobbinsoft.fw.core.exception.CoreExceptionDefinition;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.fw.support.storage.StorageClient;
import com.iotechn.unimall.biz.executor.GlobalExecutor;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.product.AdminSpuDTO;
import com.iotechn.unimall.data.dto.product.SpuDTO;
import com.iotechn.unimall.data.dto.product.SpuTreeNodeDTO;
import com.iotechn.unimall.data.enums.*;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
    private FreightTemplateMapper freightTemplateMapper;

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

    @Autowired
    private ProductBizService productBizService;

//    @Autowired(required = false)
//    private SearchEngine searchEngine;

    @Autowired
    private StorageClient storageClient;

    /**
     * 后台低频接口，无需缓存，用于选择商品，需要
     *
     * @return
     * @throws ServiceException
     */
    public List<SpuTreeNodeDTO> getSpuBigTree(Long adminId) throws ServiceException {
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new QueryWrapper<CategoryDO>().orderByAsc("level"));
        List<SpuDO> spuDOS = spuMapper.getSpuTitleAll();

        HashMap<Long, List<SpuTreeNodeDTO>> productMap = new HashMap<>();
        for (int i = 0; i < spuDOS.size(); i++) {
            SpuDO spuDO = spuDOS.get(i);
            List<SpuTreeNodeDTO> orDefault = productMap.getOrDefault(spuDO.getCategoryId(), new ArrayList<>());
            SpuTreeNodeDTO dtoOnK = new SpuTreeNodeDTO();
            dtoOnK.setLabel(spuDO.getTitle());
            dtoOnK.setValue("G_" + spuDO.getId());
            dtoOnK.setId(spuDO.getId());
            orDefault.add(dtoOnK);
            productMap.put(spuDO.getCategoryId(),orDefault);
        }

        List<SpuTreeNodeDTO> list = new ArrayList<>();
        Integer recordLevelOne = 0;
        for (int i = 0; i < categoryDOS.size(); i++) {
            if (i != 0 && categoryDOS.get(i - 1).getLevel().equals(CategoryLevelType.ONE.getCode()) && categoryDOS.get(i).getLevel().equals(CategoryLevelType.TWO.getCode())) {
                recordLevelOne = i;
            }
        }

        for (int i = 0; i < recordLevelOne; i++) {
            CategoryDO categoryOnI = categoryDOS.get(i);    //一级类目
            SpuTreeNodeDTO dtoOnI = new SpuTreeNodeDTO();
            dtoOnI.setLabel(categoryOnI.getTitle());
            dtoOnI.setValue("C_" + categoryOnI.getId());
            dtoOnI.setId(categoryOnI.getId());
            dtoOnI.setChildren(new LinkedList<>());
            for (int j = recordLevelOne; j < categoryDOS.size(); j++) {
                CategoryDO categoryOnJ = categoryDOS.get(j);    //二级类目
                if (!categoryOnJ.getParentId().equals(dtoOnI.getId())) {
                    continue;
                }

                SpuTreeNodeDTO dtoOnJ = new SpuTreeNodeDTO();
                dtoOnJ.setLabel(categoryOnJ.getTitle());
                dtoOnJ.setValue("C_" + categoryOnJ.getId());
                dtoOnJ.setId(categoryOnJ.getId());
                List<SpuTreeNodeDTO> spuTreeNodeDTOS = productMap.get(categoryOnJ.getId());
                if (spuTreeNodeDTOS == null) {
                    dtoOnJ.setChildren(new ArrayList<>());
                } else {
                    dtoOnJ.setChildren(spuTreeNodeDTOS);
                }
                dtoOnI.getChildren().add(dtoOnJ);
            }
            list.add(dtoOnI);
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(AdminSpuDTO adminSpuDTO, Long adminId) throws ServiceException {
        return productBizService.create(adminSpuDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(AdminSpuDTO spuDTO, Long adminId) throws ServiceException {
        return productBizService.edit(spuDTO);
    }

    @Override
    public Page<SpuDTO> list(Integer page, Integer limit, Long categoryId, String title, String barcode, Integer status, Long adminId) throws ServiceException {
        // 1. 构建搜索条件
        QueryWrapper<SpuDO> wrapper = new QueryWrapper<SpuDO>().select(ProductBizService.SPU_EXCLUDE_DETAIL_FIELDS);
        // 1.1.标题搜索
        if (!ObjectUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        // 1.2.类目搜索
        if (categoryId != null && categoryId != 0L) {
            List<Long> categorySelfAndChildren = categoryBizService.getCategorySelfAndChildren(categoryId);
            wrapper.in("category_id", categorySelfAndChildren);
        }
        // 1.3.状态搜索
        if (status != null) {
            wrapper.eq("status", status.intValue() <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode());
        }
        // 1.4.条码搜索
        if (!ObjectUtils.isEmpty(barcode)) {
            List<SkuDO> skuDOList = skuMapper.selectList(new QueryWrapper<SkuDO>().eq("bar_code", barcode));
            if (!CollectionUtils.isEmpty(skuDOList)) {
                SkuDO skuDO = skuDOList.get(0);
                wrapper.eq("id", skuDO.getSpuId());
            }
        }
        Page<SpuDO> domainPage = spuMapper.selectPage(Page.div(page, limit, SpuDO.class), wrapper);
        // 返回空页
        if (CollectionUtils.isEmpty(domainPage.getItems())) {
            return domainPage.trans(item -> {
                SpuDTO spuDTO = new SpuDTO();
                BeanUtils.copyProperties(item, spuDTO);
                return spuDTO;
            });
        }
        // 2. 连接其他表
        // 2.1. SKU表
        List<Long> spuIds = domainPage.getItems().stream().map(SpuDO::getId).collect(Collectors.toList());
        Map<Long, List<SkuDO>> skuMap = skuMapper.selectList(new QueryWrapper<SkuDO>().in("spu_id", spuIds)).stream().collect(Collectors.groupingBy(SkuDO::getSpuId));
        // 2.2. 类目表
        Set<Long> categoryIds = domainPage.getItems().stream().map(SpuDO::getCategoryId).collect(Collectors.toSet());
        Map<Long, CategoryDO> categoryMap = categoryMapper.selectList(
                new QueryWrapper<CategoryDO>()
                        .in("id", categoryIds)
                        .or()
                        .eq("level", CategoryLevelType.ONE.getCode())).stream().collect(Collectors.toMap(CategoryDO::getId, v -> v));
        // 2.3. 运费模板
        Map<Long, String> freightTemplateMap = freightTemplateMapper.selectList(
                new QueryWrapper<FreightTemplateDO>()
                        .select("id", "title"))
                .stream().collect(Collectors.toMap(FreightTemplateDO::getId, FreightTemplateDO::getTitle));
        Page<SpuDTO> dtoPage = domainPage.trans(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            List<SkuDO> skuList = skuMap.get(item.getId());
            spuDTO.setSkuList(skuList);
            CategoryDO leaf = categoryMap.get(item.getCategoryId());
            if (leaf.getParentId() != null && leaf.getParentId().intValue() != 0) {
                CategoryDO parentCategory = categoryMap.get(leaf.getParentId());
                spuDTO.setCategoryFullTitle(parentCategory.getTitle() + "/" + leaf.getTitle());
            } else {
                spuDTO.setCategoryFullTitle(leaf.getTitle());
            }
            spuDTO.setFreightTemplateTitle(freightTemplateMap.get(item.getFreightTemplateId()));
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
        adminSpuDTO.setSkuList(skuList);
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
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
        }
        // 将需要删除的图片路径取出来
        List<String> deleteImgList = imgMapper.getImgs(BizType.GOODS.getCode(), spuId);

        cartMapper.delete(new QueryWrapper<CartDO>().in("sku_id", skuMapper.getSkuIds(spuId)));
        skuMapper.delete(new QueryWrapper<SkuDO>().eq("spu_id", spuId));
        imgMapper.delete(new QueryWrapper<ImgDO>().eq("biz_id", spuId).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                productBizService.deleteSpuCache(spuDOBefore);
                productBizService.deleteSearchEngine(spuDOBefore.getId());
                GlobalExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for (String img : deleteImgList) {
                            storageClient.delete(img);
                        }
                    }
                });
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
            throw new AdminServiceException(CoreExceptionDefinition.buildVariableException(ExceptionDefinition.PRODUCT_EXIST_ADVERT, list1.get(0).getTitle()));
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
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
        }
        if (spuMapper.delete(new QueryWrapper<SpuDO>().in("id", ids)) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
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
                    productBizService.deleteSpuCache(spuDOBefore);
                    productBizService.deleteSearchEngine(spuDOBefore.getId());
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
            productBizService.createSpuCache(spuDO);
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
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
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
                    productBizService.createSpuCache(spuDO);
                    //2. 重建Sku库存缓存
                    for (SkuDO skuDO : spuDTO.getSkuList()) {
                        cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
                    }
                    //3. 更新搜索引擎缓存
                    productBizService.transmissionSearchEngine(spuId);
                } else {
                    // 删除商品列表缓存
                    productBizService.deleteSpuCache(spuDO);
                    // 更新搜索引擎
                    productBizService.deleteSearchEngine(spuId);
                }
            }
        });
        return spuDTO;
    }

    /**
     * 校验SPU是否有团购这样的，不允许删除或下架的活动
     *
     * @param spuDO
     * @throws ServiceException
     */
    private void checkProductActivity(SpuDO spuDO) throws ServiceException {
        if (spuDO.getActivityType() != null && spuDO.getActivityType() == SpuActivityType.GROUP_SHOP.getCode()) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_UNION_ACTIVITY_CAN_NOT_BE_OFF_SHELF);
        }
    }

}
