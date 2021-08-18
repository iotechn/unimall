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
import com.iotechn.unimall.biz.service.location.LocationBizService;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.product.*;
import com.iotechn.unimall.data.enums.AdvertUnionType;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.enums.SpuActivityType;
import com.iotechn.unimall.data.enums.SpuStatusType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/11.
 */
@Service
public class AdminProductServiceImpl implements AdminProductService, InitializingBean {

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
    private LocationMapper locationMapper;

    @Autowired
    private LocationSpuMapper locationSpuMapper;

    @Autowired
    private LocationSkuMapper locationSkuMapper;

    @Autowired
    private LocationBizService locationBizService;

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
        List<SpuTreeNodeDTO> list = new ArrayList<>();
        Integer recordLevelOne = 0;
        Integer recordLevelTwo = 0;
        for (int i = 0; i < categoryDOS.size(); i++) {
            if (i != 0 && categoryDOS.get(i - 1).getLevel().equals(0) && categoryDOS.get(i).getLevel().equals(1)) {
                recordLevelOne = i;
            }
            if (i != 0 && categoryDOS.get(i - 1).getLevel().equals(1) && categoryDOS.get(i).getLevel().equals(2)) {
                recordLevelTwo = i;
                break;
            }
        }

        for (int i = 0; i < recordLevelOne; i++) {
            CategoryDO categoryOnI = categoryDOS.get(i);    //一级类目
            SpuTreeNodeDTO dtoOnI = new SpuTreeNodeDTO();
            dtoOnI.setLabel(categoryOnI.getTitle());
            dtoOnI.setValue("C_" + categoryOnI.getId());
            dtoOnI.setId(categoryOnI.getId());
            dtoOnI.setChildren(new LinkedList<>());
            for (int j = recordLevelOne; j < recordLevelTwo; j++) {
                CategoryDO categoryOnJ = categoryDOS.get(j);    //二级类目
                if (!categoryOnJ.getParentId().equals(dtoOnI.getId())) {
                    continue;
                }

                SpuTreeNodeDTO dtoOnJ = new SpuTreeNodeDTO();
                dtoOnJ.setLabel(categoryOnJ.getTitle());
                dtoOnJ.setValue("C_" + categoryOnJ.getId());
                dtoOnJ.setId(categoryOnJ.getId());
                dtoOnJ.setChildren(new LinkedList<>());

                for (int p = recordLevelTwo; p < categoryDOS.size(); p++) {
                    CategoryDO categoryOnP = categoryDOS.get(p);    //三级类目
                    if (!categoryOnP.getParentId().equals(dtoOnJ.getId())) {
                        continue;
                    }

                    SpuTreeNodeDTO dtoOnP = new SpuTreeNodeDTO();
                    dtoOnP.setLabel(categoryOnP.getTitle());
                    dtoOnP.setValue("C_" + categoryOnP.getId());
                    dtoOnP.setId(categoryOnP.getId());
                    dtoOnP.setChildren(new LinkedList<>());

                    for (int k = 0; k < spuDOS.size(); k++) {
                        if (k != 0 && spuDOS.get(k - 1).getCategoryId().equals(dtoOnP.getId()) && !spuDOS.get(k).getCategoryId().equals(dtoOnP.getId())) {
                            break;
                        }
                        SpuDO spuDO = spuDOS.get(k);        //商品
                        if (spuDO.getCategoryId().equals(dtoOnP.getId())) {
                            SpuTreeNodeDTO dtoOnK = new SpuTreeNodeDTO();
                            dtoOnK.setLabel(spuDO.getTitle());
                            dtoOnK.setValue("G_" + spuDO.getId());
                            dtoOnK.setId(spuDO.getId());
                            dtoOnP.getChildren().add(dtoOnK);
                        }
                    }
                    dtoOnJ.getChildren().add(dtoOnP);
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
        // 1.参数校验
        if (adminSpuDTO.getOriginalPrice() < adminSpuDTO.getPrice() || adminSpuDTO.getPrice() < adminSpuDTO.getVipPrice() || adminSpuDTO.getOriginalPrice() < adminSpuDTO.getVipPrice()) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_PRICE_CHECKED_FAILED);
        }
        // 1.2.校验Sku是否重复
        Set<String> barCodes = adminSpuDTO.getSkuList().stream().map(item -> item.getBarCode()).collect(Collectors.toSet());
        if (barCodes.size() != adminSpuDTO.getSkuList().size()) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_UPLOAD_SKU_BARCODE_REPEAT);
        }
        List<SkuDO> existSkuDO = skuMapper.selectList(new QueryWrapper<SkuDO>().in("bar_code", barCodes));
        if (!CollectionUtils.isEmpty(existSkuDO)) {
            String spuIds = existSkuDO.stream().map(item -> item.getSpuId().toString()).collect(Collectors.joining(","));
            String skuIds = existSkuDO.stream().map(item -> item.getBarCode()).collect(Collectors.joining(","));
            throw new AdminServiceException(CoreExceptionDefinition
                    .buildVariableException(ExceptionDefinition.PRODUCT_CREATE_BARCODE_REPEAT, spuIds, skuIds));
        }
        // 2.1.插入主表
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(adminSpuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuDO.setGmtCreate(now);
        spuMapper.insert(spuDO);
        // 添加SPU仓库信息
        locationBizService.initSpu(spuDO.getId());
        adminSpuDTO.setId(spuDO.getId());
        // 2.2.插入SKU表
        for (AdminSkuDTO adminSkuDTO : adminSpuDTO.getSkuList()) {
            SkuDO skuDO = new SkuDO();
            BeanUtils.copyProperties(adminSkuDTO, skuDO);
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.PRODUCT_PRICE_CHECKED_FAILED);
            }
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            skuDO.setGmtCreate(now);
            skuMapper.insert(skuDO);
            // 添加SKU库存映射
            locationBizService.initSku(spuDO.getId(), skuDO.getId());
        }
        // 2.3.插入spuAttr
        this.insertSpuAttribute(adminSpuDTO, now);
        // 2.4.插入IMG
        this.insertSpuImg(adminSpuDTO, spuDO.getId(), now);
        // 2.5.插入规格维度
        List<SpuSpecificationDO> specificationList = adminSpuDTO.getSpecificationList();
        specificationList.forEach(item -> {
            // 为SpuSpecificationDO 赋值上默认值
            item.setSpuId(spuDO.getId());
            item.setId(null);
            item.setGmtCreate(now);
            item.setGmtUpdate(now);
        });
        spuSpecificationMapper.insertBatchSomeColumn(specificationList);
        // 3.1. 创建商品缓存
        this.createSpuCache(spuDO);
        // TODO 3.2. 创建Sku库存缓存
//        for (SkuDO skuDO : adminSpuDTO.getSkuList()) {
//            cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
//        }
        // 4. TODO 同步到搜索引擎
//        if (searchEngine != null) {
//            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//                @Override
//                public void afterCommit() {
//                    AdminProductServiceImpl.this.transmissionSearchEngine(spuDO.getId());
//                }
//            });
//
//        }
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(AdminSpuDTO spuDTO, Long adminId) throws ServiceException {
        // 1. 参数校验
        if (spuDTO.getId() == null) {
            throw new AdminServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        if (spuDTO.getOriginalPrice() < spuDTO.getPrice() || spuDTO.getPrice() < spuDTO.getVipPrice() || spuDTO.getOriginalPrice() < spuDTO.getVipPrice()) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_PRICE_CHECKED_FAILED);
        }
        // 将旧值查询出来，若价格没有改变，则无需更新价格排序缓存
        // SpuDO spuFromDB = spuMapper.selectById(spuDTO.getId());
        // 2.1. 更新主表
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuMapper.updateById(spuDO);
        // 2.2. 更新barCodes
        Set<String> barCodes = new HashSet<>();
        for (AdminSkuDTO adminSkuDTO : spuDTO.getSkuList()) {
            SkuDO skuDO = new SkuDO();
            BeanUtils.copyProperties(adminSkuDTO, skuDO);
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.PRODUCT_PRICE_CHECKED_FAILED);
            }
            skuDO.setId(null);
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            if (skuMapper.update(skuDO,
                    new QueryWrapper<SkuDO>()
                            .eq("bar_code", skuDO.getBarCode())) <= 0) {
                skuDO.setGmtCreate(now);
                skuMapper.insert(skuDO);
                // 添加SKU库存映射
                locationBizService.initSku(spuDTO.getId(), skuDO.getId());
            }
            if (!barCodes.add(skuDO.getBarCode())) {
                throw new AdminServiceException(ExceptionDefinition.PRODUCT_UPLOAD_SKU_BARCODE_REPEAT);
            }
        }
        // 2.2.2. 删除多余barCode
        List<Long> willDeleteSkuIds = skuMapper.selectList(
                new QueryWrapper<SkuDO>()
                        .select("id")
                        .eq("spu_id", spuDO.getId())
                        .notIn("bar_code", barCodes)).stream().map(SkuDO::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(willDeleteSkuIds)) {
            skuMapper.delete(new QueryWrapper<SkuDO>().in("id", willDeleteSkuIds));
            locationSkuMapper.delete(new QueryWrapper<LocationSkuDO>().in("sku_id", willDeleteSkuIds));
        }
        // 2.3. 更新spuAttr
        spuAttributeMapper.delete(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuDTO.getId()));
        this.insertSpuAttribute(spuDTO, now);
        imgMapper.delete(new QueryWrapper<ImgDO>().eq("biz_id", spuDO.getId()).eq("biz_type", BizType.GOODS.getCode()));
        // 2.4. 插入IMG
        this.insertSpuImg(spuDTO, spuDO.getId(), now);
        // TODO 2.5. 更新规格维度
        // TODO 3. 删除缓存
        // 对于更新缓存，需要在事务提交后更新，防止事务提交期间产生读请求，将旧值覆盖到新值上
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 4 更新搜索引擎
                AdminProductServiceImpl.this.transmissionSearchEngine(spuDO.getId());
            }
        });
        return "ok";
    }

    @Override
    public List<LocationSpuDTO> listLocation(Long spuId, Long adminId) throws ServiceException {
        List<LocationSpuDO> locationSpuList = locationSpuMapper.selectList(new QueryWrapper<LocationSpuDO>().eq("spu_id", spuId));
        List<LocationSkuDO> locationSkuList = locationSkuMapper.selectList(new QueryWrapper<LocationSkuDO>().eq("spu_id", spuId));
        Map<Long, List<LocationSkuDO>> locationSkuMap = locationSkuList.stream().collect(Collectors.groupingBy(LocationSkuDO::getSpuId));
        return locationSpuList.stream().map(item -> {
            LocationSpuDTO dto = new LocationSpuDTO();
            BeanUtils.copyProperties(item, dto);
            dto.setSkuList(locationSkuMap.get(item.getSpuId()));
            return dto;
        }).collect(Collectors.toList());
    }

    private void insertSpuAttribute(AdminSpuDTO spuDTO, Date now) {
        if (!CollectionUtils.isEmpty(spuDTO.getAttributeList())) {
            for (SpuAttributeDO spuAttributeDO : spuDTO.getAttributeList()) {
                spuAttributeDO.setSpuId(spuDTO.getId());
                spuAttributeDO.setGmtUpdate(now);
                spuAttributeDO.setGmtCreate(now);
            }
            spuAttributeMapper.insertBatchSomeColumn(spuDTO.getAttributeList());
        }
    }

    private void insertSpuImg(AdminSpuDTO spuDTO, Long bizId, Date now) {
        List<String> imgList = spuDTO.getImgList();
        List<ImgDO> imgDomainList = imgList.stream().map(item -> {
            ImgDO imgDO = new ImgDO();
            imgDO.setBizType(BizType.GOODS.getCode());
            imgDO.setBizId(bizId);
            imgDO.setUrl(item);
            imgDO.setGmtCreate(now);
            imgDO.setGmtUpdate(now);
            return imgDO;
        }).collect(Collectors.toList());
        imgMapper.insertBatchSomeColumn(imgDomainList);
    }

    @Override
    public Page<AdminSpuDTO> list(Integer page, Integer limit, Long categoryId, String title, String barcode, Integer status, Long adminId) throws ServiceException {
        QueryWrapper<SpuDO> wrapper = new QueryWrapper<SpuDO>().select(ProductBizService.SPU_EXCLUDE_DETAIL_FIELDS);
        // 1.标题搜索
        if (!ObjectUtils.isEmpty(title)) {
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
        if (!ObjectUtils.isEmpty(barcode)) {
            List<SkuDO> skuList = skuMapper.selectList(new QueryWrapper<SkuDO>().eq("bar_code", barcode));
            if (!CollectionUtils.isEmpty(skuList)) {
                SkuDO skuDO = skuList.get(0);
                wrapper.eq("id", skuDO.getSpuId());
            }
        }
        Page<SpuDO> domainPage = spuMapper.selectPage(Page.div(page, limit, SpuDO.class), wrapper);
        List<Long> spuIds = domainPage.getItems().stream().map(SpuDO::getId).collect(Collectors.toList());
        // 仓库信息
        Map<Long, LocationDO> locationMap = locationMapper.selectList(new QueryWrapper<LocationDO>()).stream().collect(Collectors.toMap(LocationDO::getId, v -> v));
        // 规格信息
        List<SkuDO> skuListOfAll = skuMapper.selectList(new QueryWrapper<SkuDO>().in("spu_id", spuIds));
        Map<Long, List<SkuDO>> skuMap = skuListOfAll.stream().collect(Collectors.groupingBy(SkuDO::getSpuId));
        // 库存信息
        List<LocationSkuDO> locationSkuListOfAll = locationSkuMapper.selectList(new QueryWrapper<LocationSkuDO>().in("spu_id", spuIds));
        Map<Long, List<LocationSkuDO>> locationSkuMap = locationSkuListOfAll.stream().collect(Collectors.groupingBy(LocationSkuDO::getSpuId));
        // 上架、销量信息
        List<LocationSpuDO> locationSpuListOfAll = locationSpuMapper.selectList(new QueryWrapper<LocationSpuDO>().in("spu_id", spuIds));
        Map<String, LocationSpuDO> locationSpuMap = locationSpuListOfAll.stream().collect(Collectors.toMap(k -> (k.getLocationId() + "-" + k.getSpuId()), v -> v));
        Page<AdminSpuDTO> dtoPage = domainPage.trans(item -> {
            AdminSpuDTO adminSpuDTO = new AdminSpuDTO();
            BeanUtils.copyProperties(item, adminSpuDTO);
            adminSpuDTO.setSkuList(skuMap.get(item.getId()).stream().map(skuItem -> {
                AdminSkuDTO skuDTO = new AdminSkuDTO();
                BeanUtils.copyProperties(skuItem, skuDTO);
                return skuDTO;
            }).collect(Collectors.toList()));
            List<LocationSkuDO> locationSkuDOS = locationSkuMap.get(item.getId());
            // 库存映射表
            Map<Long, List<LocationSkuDO>> locationSkuStockMap = locationSkuDOS.stream().collect(Collectors.groupingBy(LocationSkuDO::getSkuId));
            for (AdminSkuDTO skuDTO : adminSpuDTO.getSkuList()) {
                skuDTO.setLocationSkuList(locationSkuStockMap.get(skuDTO.getId()).stream().map(locationSkuItem -> {
                    LocationSkuDTO locationSkuDTO = new LocationSkuDTO();
                    BeanUtils.copyProperties(locationSkuItem, locationSkuDTO);
                    // 1. 封装位置信息
                    String locationTitle = locationMap.get(locationSkuItem.getLocationId()).getTitle();
                    locationSkuDTO.setLocationTitle(locationTitle);
                    // 2. 封装商品上架、销量信息
                    LocationSpuDO locationSpuDO = locationSpuMap.get(locationSkuDTO.getLocationId() + "-" + locationSkuDTO.getSpuId());
                    locationSkuDTO.setSpuSales(locationSpuDO.getSales());
                    locationSkuDTO.setSpuStatus(locationSpuDO.getStatus());
                    return locationSkuDTO;
                }).collect(Collectors.toList()));
            }
            return adminSpuDTO;
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
        adminSpuDTO.setSkuList(skuList.stream().map(item -> {
            AdminSkuDTO adminSkuDTO = new AdminSkuDTO();
            BeanUtils.copyProperties(item, adminSkuDTO);
            return adminSkuDTO;
        }).collect(Collectors.toList()));
        ;
        // 2. Spu属性
        List<SpuAttributeDO> attributeList = spuAttributeMapper.selectList(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        adminSpuDTO.setAttributeList(attributeList);
        // 3. categoryIds
        List<Long> categoryIds = categoryBizService.getCategoryFamily(adminSpuDTO.getCategoryId());
        adminSpuDTO.setCategoryIds(categoryIds);
        // 4. spu specificationList
        List<SpuSpecificationDO> specificationList = spuSpecificationMapper.selectList(
                new QueryWrapper<SpuSpecificationDO>()
                        .eq("spu_id", spuId).orderByAsc("title"));
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
                AdminProductServiceImpl.this.deleteSpuCache(spuDOBefore);
                AdminProductServiceImpl.this.deleteSearchEngine(spuDOBefore.getId());
                GlobalExecutor.execute(() -> {
                    for (String img : deleteImgList) {
                        storageClient.delete(img);
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
                    AdminProductServiceImpl.this.deleteSpuCache(spuDOBefore);
                    AdminProductServiceImpl.this.deleteSearchEngine(spuDOBefore.getId());
                }
            }
        });
        return "ok";
    }

    @Override
    public String rebuildProductCache(Long adminId) throws ServiceException {
        // TODO 1.删除旧缓存
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String shelves(Long locationId, Long spuId, Integer status, Long adminId) throws ServiceException {
        QueryWrapper<LocationSpuDO> wrapper = new QueryWrapper<LocationSpuDO>().eq("location_id", locationId).eq("spu_id", spuId);
        LocationSpuDO locationSpuDO = locationSpuMapper.selectOne(wrapper);
        if (locationSpuDO == null) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
        }
        SpuDO spuDO = spuMapper.selectById(locationSpuDO.getSpuId());
        if (spuDO == null) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
        }
        // 不可下架活动校验
        if (status == SpuStatusType.STOCK.getCode().intValue()) {
            this.checkProductActivity(spuDO);
        }
        LocationSpuDO updateLocationSpuDO = new LocationSpuDO();
        updateLocationSpuDO.setStatus(status);
        if (locationSpuMapper.update(updateLocationSpuDO, wrapper) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        // TODO 编辑缓存
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String editStock(Long locationId, Long skuId, Integer stock, Long adminId) throws ServiceException {
        QueryWrapper<LocationSkuDO> wrapper = new QueryWrapper<LocationSkuDO>().eq("sku_id", skuId).eq("location_id", locationId);
        LocationSkuDO locationSkuDO = locationSkuMapper.selectOne(wrapper);
        if (locationSkuDO == null) {
            throw new AdminServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
        }
        // 更改库存
        LocationSkuDO updateLocationSkuDO = new LocationSkuDO();
        updateLocationSkuDO.setStock(stock);
        if (locationSkuMapper.update(updateLocationSkuDO, wrapper) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        // TODO 编辑缓存
        return "ok";
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

    /**
     * 1.放入各个类目ZSET
     * 2.放入基本信息Hash表
     * 3.不放入详细信息Hash表，读取时再放入缓存
     *
     * @param spuDO
     */
    private void createSpuCache(SpuDO spuDO) {
        // TODO 1. 创建商品缓存
    }

    /**
     * 1.删除各个类目ZSET
     * 2.删除基本信息Hash表
     * 3.删除商品详情信息Hash表
     *
     * @param spuDO
     */
    private void deleteSpuCache(SpuDO spuDO) {
        // TODO 1. 删除各个类目ZSET
    }

    private void transmissionSearchEngine(Long id) {
        // TODO
//        if (searchEngine != null) {
//            GlobalExecutor.execute(() -> {
//                SpuDO spuFromDB = AdminProductServiceImpl.this.productBizService.getProductByIdFromDB(id);
//                searchEngine.dataTransmission(spuFromDB);
//            });
//        }
    }

    private void deleteSearchEngine(Long id) {
        // TODO
//        if (searchEngine != null) {
//            GlobalExecutor.execute(() -> {
//                SpuDO spuDO = new SpuDO();
//                spuDO.setId(id);
//                searchEngine.deleteData(spuDO);
//            });
//        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        List<SpuDO> spuDOS = spuMapper.selectList(new QueryWrapper<>());
//        List<SkuDO> skuDOS = skuMapper.selectList(new QueryWrapper<>());
//        List<LocationDO> locationDOS = locationMapper.selectList();
//        for (LocationDO locationDO : locationDOS) {
//            for (SpuDO spuDO : spuDOS) {
//                LocationSpuDO locationSpuDO = new LocationSpuDO();
//                locationSpuDO.setStatus(SpuStatusType.SELLING.getCode());
//                locationSpuDO.setLocationId(locationDO.getId());
//                locationSpuDO.setSales(0);
//                locationSpuDO.setSpuId(spuDO.getId());
//                locationSpuMapper.insert(locationSpuDO);
//            }
//            for (SkuDO skuDO : skuDOS) {
//                LocationSkuDO locationSkuDO = new LocationSkuDO();
//                locationSkuDO.setStock(10);
//                locationSkuDO.setLocationId(locationDO.getId());
//                locationSkuDO.setSkuId(skuDO.getId());
//                locationSkuDO.setSpuId(skuDO.getSpuId());
//                locationSkuMapper.insert(locationSkuDO);
//            }
//        }
    }
}
