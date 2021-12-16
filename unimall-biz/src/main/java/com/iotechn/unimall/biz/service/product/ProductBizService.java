package com.iotechn.unimall.biz.service.product;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.BizServiceException;
import com.dobbinsoft.fw.core.exception.CoreExceptionDefinition;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.product.AdminSpuDTO;
import com.iotechn.unimall.data.dto.product.SkuDTO;
import com.iotechn.unimall.data.dto.product.SpuDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.enums.StatusType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/12.
 */
@Service
public class ProductBizService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuAttributeMapper spuAttributeMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private SpuSpecificationMapper spuSpecificationMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    @Autowired
    private CacheComponent cacheComponent;

//    @Autowired(required = false)
//    private SearchEngine searchEngine;


    /**
     * SPU 排除掉detail字段的其他字段名字形成的数组
     */
    public static final String[] SPU_EXCLUDE_DETAIL_FIELDS;

    private static final Logger logger = LoggerFactory.getLogger(ProductBizService.class);

    static {
        Field[] fields = SpuDO.class.getDeclaredFields();
        List<String> tempList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = StrUtil.toUnderlineCase(field.getName());
            if (!name.equals("detail")) {
                tempList.add(name);
            }
        }
        tempList.add("id");
        tempList.add("gmt_update");
        tempList.add("gmt_create");
        SPU_EXCLUDE_DETAIL_FIELDS = tempList.toArray(new String[0]);
    }

    /**
     * 创建商品 抽取方法
     * @param adminSpuDTO
     * @return
     * @throws ServiceException
     */
    public String create(AdminSpuDTO adminSpuDTO) throws ServiceException {
        // 1.参数校验
        if (adminSpuDTO.getId() != null) {
            throw new BizServiceException(ExceptionDefinition.PRODUCT_CREATE_HAS_ID);
        }
        if (CollectionUtils.isEmpty(adminSpuDTO.getSkuList())) {
            throw new BizServiceException(ExceptionDefinition.PRODUCT_SKU_LIST_EMPTY);
        }
        // 1.2.校验Sku是否重复
        Set<String> barCodes = adminSpuDTO.getSkuList().stream().map(item -> item.getBarCode()).collect(Collectors.toSet());
        if (barCodes.size() != adminSpuDTO.getSkuList().size()) {
            throw new BizServiceException(ExceptionDefinition.PRODUCT_UPLOAD_SKU_BARCODE_REPEAT);
        }
        List<SkuDO> existSkuDO = skuMapper.selectList(new QueryWrapper<SkuDO>().in("bar_code", barCodes));
        if (!CollectionUtils.isEmpty(existSkuDO)) {
            String spuIds = existSkuDO.stream().map(item -> item.getSpuId().toString()).collect(Collectors.joining(","));
            String skuIds = existSkuDO.stream().map(item -> item.getBarCode()).collect(Collectors.joining(","));
            throw new BizServiceException(CoreExceptionDefinition
                    .buildVariableException(ExceptionDefinition.PRODUCT_CREATE_BARCODE_REPEAT, spuIds, skuIds));
        }
        // 2.1.插入主表
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(adminSpuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuDO.setGmtCreate(now);
        spuDO.setSales(0);
        packPrice(adminSpuDTO, spuDO);
        spuMapper.insert(spuDO);
        adminSpuDTO.setId(spuDO.getId());
        // 2.2.插入SKU表
        for (SkuDO skuDO : adminSpuDTO.getSkuList()) {
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            skuDO.setGmtCreate(now);
            skuMapper.insert(skuDO);
        }
        // 2.3.插入spuAttr
        insertSpuAttribute(adminSpuDTO, now);
        // 2.4.插入IMG
        insertSpuImg(adminSpuDTO, spuDO.getId(), now);
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
        // 3.2. 创建Sku库存缓存
        for (SkuDO skuDO : adminSpuDTO.getSkuList()) {
            cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
        }
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

    /**
     * 封装商品原价/VIP价等
     * @param adminSpuDTO
     * @param spuDO
     * @throws BizServiceException
     */
    private void packPrice(AdminSpuDTO adminSpuDTO, SpuDO spuDO) throws BizServiceException {
        SkuDO minPriceSku = null;
        for (SkuDO skuDO : adminSpuDTO.getSkuList()) {
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new BizServiceException(ExceptionDefinition.PRODUCT_PRICE_CHECKED_FAILED);
            }
            if (minPriceSku == null) {
                minPriceSku = skuDO;
            }
            if (minPriceSku.getPrice() > skuDO.getPrice()) {
                minPriceSku = skuDO;
            }
        }
        spuDO.setOriginalPrice(minPriceSku.getOriginalPrice());
        spuDO.setPrice(minPriceSku.getPrice());
        spuDO.setVipPrice(minPriceSku.getVipPrice());
    }

    /**
     * 编辑商品 抽取方法
     * @param spuDTO
     * @return
     * @throws ServiceException
     */
    public String edit(AdminSpuDTO spuDTO) throws ServiceException {
        // 1. 参数校验
        if (spuDTO.getId() == null) {
            throw new BizServiceException(ExceptionDefinition.PARAM_CHECK_FAILED);
        }
        if (CollectionUtils.isEmpty(spuDTO.getSkuList())) {
            throw new BizServiceException(ExceptionDefinition.PRODUCT_SKU_LIST_EMPTY);
        }
        // 将旧值查询出来，若价格没有改变，则无需更新价格排序缓存
        SpuDO spuFromDB = spuMapper.selectById(spuDTO.getId());
        // 2.1. 更新主表
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        packPrice(spuDTO, spuDO);
        spuMapper.updateById(spuDO);
        // 2.2. 更新barCodes
        Set<String> barCodes = new HashSet<>();
        for (SkuDO skuDO : spuDTO.getSkuList()) {
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new BizServiceException(ExceptionDefinition.PRODUCT_PRICE_CHECKED_FAILED);
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
                throw new BizServiceException(ExceptionDefinition.PRODUCT_UPLOAD_SKU_BARCODE_REPEAT);
            }
        }
        // 2.2.2. 删除多余barCode
        skuMapper.delete(new QueryWrapper<SkuDO>().eq("spu_id", spuDO.getId()).notIn("bar_code", barCodes));
        // 2.3. 更新spuAttr
        spuAttributeMapper.delete(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuDTO.getId()));
        insertSpuAttribute(spuDTO, now);
        imgMapper.delete(new QueryWrapper<ImgDO>().eq("biz_id", spuDO.getId()).eq("biz_type", BizType.GOODS.getCode()));
        // 2.4. 插入IMG
        insertSpuImg(spuDTO, spuDO.getId(), now);
        // TODO 2.5. 更新规格维度
        // 3. 删除缓存
        // 对于更新缓存，需要在事务提交后更新，防止事务提交期间产生读请求，将旧值覆盖到新值上
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 3.1. 检测商品价格、类目是否发生了改变，根据情况更新缓存
                if (spuFromDB.getCategoryId().longValue() != spuDO.getCategoryId().longValue()) {
                    // 3.1.1. 若二者CategoryId不一致，则表示商品更改了类目 更新列表排序缓存
                    // 3.1.1.1 删除旧列表中的值
                    List<Long> oldCategoryFamily = categoryBizService.getCategoryFamily(spuFromDB.getCategoryId());
                    for (Long oldCid : oldCategoryFamily) {
                        cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + oldCid, "P" + spuDO.getId());
                        cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + oldCid, "P" + spuDO.getId());
                        cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + oldCid, "P" + spuDO.getId());
                    }
                    // 3.1.1.2 添加新列表中的值
                    if (spuDO.getStatus().intValue() == StatusType.ACTIVE.getCode()) {
                        List<Long> newCategoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
                        for (Long newCid : newCategoryFamily) {
                            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + newCid, spuDO.getId(), "P" + spuDO.getId());
                            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + newCid, spuDO.getPrice(), "P" + spuDO.getId());
                            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + newCid, spuFromDB.getSales(), "P" + spuDO.getId());
                        }
                    }
                }
                List<Long> categoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
                if (spuFromDB.getPrice().intValue() != spuDO.getPrice().intValue()) {
                    // 3.1.2.若二者Price不一致，则表示商品更新了价格 更新列表缓存
                    if (spuDO.getStatus().intValue() == StatusType.ACTIVE.getCode()) {
                        for (Long categoryId : categoryFamily) {
                            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + categoryId, spuDO.getPrice(), "P" + spuDO.getId());
                        }
                    }
                }
                // 3.2. 更新基本信息缓存
                SpuDTO basicSpuDTO = new SpuDTO();
                BeanUtils.copyProperties(spuDO, basicSpuDTO, "detail");
                basicSpuDTO.setCategoryIds(categoryFamily);
                cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuDO.getId(), basicSpuDTO);
                // 3.3. 删除详细信息缓存
                cacheComponent.delHashKey(CacheConst.PRT_SPU_DETAIL_HASH_BUCKET, "P" + spuDO.getId());
                // 3.4. 更新Sku库存缓存
                for (SkuDO skuDO : spuDTO.getSkuList()) {
                    cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
                }
                // 4 更新搜索引擎
                ProductBizService.this.transmissionSearchEngine(spuDO.getId());
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
        imgMapper.insertBatchSomeColumn(imgDOList);
    }

    public void transmissionSearchEngine(Long id) {
        // TODO
//        if (searchEngine != null) {
//            GlobalExecutor.execute(() -> {
//                SpuDO spuFromDB = AdminProductServiceImpl.this.productBizService.getProductByIdFromDB(id);
//                searchEngine.dataTransmission(spuFromDB);
//            });
//        }
    }

    public void deleteSearchEngine(Long id) {
        // TODO
//        if (searchEngine != null) {
//            GlobalExecutor.execute(() -> {
//                SpuDO spuDO = new SpuDO();
//                spuDO.setId(id);
//                searchEngine.deleteData(spuDO);
//            });
//        }
    }

    /**
     * 若搜索关键字为空，则从缓存中获取商品列表，此列表不包含detail(商品详情)字段
     *
     * @param pageNo
     * @param pageSize
     * @param categoryId
     * @param orderBy
     * @param isAsc
     * @param title
     * @return
     * @throws ServiceException
     */
    public Page<SpuDO> getProductPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        if (!StringUtils.isEmpty(title)) {
            // TODO 搜索引擎
//            try {
//                if (this.searchEngine != null) {
//                    SearchWrapperModel searchWrapper =
//                            new SearchWrapperModel()
//                                    .div(pageNo, pageSize)
//                                    .like("title", title);
//                    if (categoryId != null && categoryId > 0) {
//                        searchWrapper.eq("category_id", categoryId);
//                    }
//                    if (orderBy != null && isAsc != null) {
//                        if (isAsc)
//                            searchWrapper.orderByAsc(orderBy);
//                        else
//                            searchWrapper.orderByDesc(orderBy);
//                    }
//                    Page<SpuDO> searchRes = searchEngine.search(searchWrapper, SpuDO.class);
//                    return searchRes;
//                }
//            } catch (SearchEngineException e) {
//                logger.error("[获取商品列表] 搜素引擎 异常", e);
//                throw new AppServiceException(ExceptionDefinition.buildVariableException(ExceptionDefinition.SEARCH_ENGINE_INNER_EXCEPTION, e.getMessage()));
//            }
            // 使用DB逻辑
            return this.getProductPageFromDB(pageNo, pageSize, categoryId, orderBy, isAsc, title);
        }
        // 1. 从商品列表缓存中取出Id
        String zsetBucketKey;
        if ("price".equals(orderBy)) {
            zsetBucketKey = CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + categoryId;
        } else if ("id".equals(orderBy)) {
            zsetBucketKey = CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + categoryId;
        } else if ("sales".equals(orderBy)) {
            zsetBucketKey = CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + categoryId;
        } else {
            throw new BizServiceException(ExceptionDefinition.GOODS_ORDER_BY_WAY_ILLEGAL);
        }
        Page<String> page = cacheComponent.getZSetPage(zsetBucketKey, pageNo, pageSize, isAsc);
        if (page.getTotal() == 0) {
            // 若没有缓存，则尝试从DB读取
            List<SpuDO> productIdsFromDB = getProductIdsOnSaleFromDB(categoryId);
            // 传入一个categoryId，将所有该Category(包括子节点) 的全部商品Id和销量查询出来。若传入的CategoryId为null，则查出所有的商品
            if (!CollectionUtils.isEmpty(productIdsFromDB)) {
                // 若非空，则全部放入ZSet中
                Set<ZSetOperations.TypedTuple<String>> set = productIdsFromDB.stream().map(item -> (ZSetOperations.TypedTuple<String>) (new DefaultTypedTuple("P" + item.getId(), item.getSales().doubleValue()))).collect(Collectors.toSet());
                // 放入缓存
                cacheComponent.putZSetMulti(zsetBucketKey, set);
                // 重新从缓存中读取
                page = cacheComponent.getZSetPage(zsetBucketKey, pageNo, pageSize, isAsc);
            }
        }
        // 从Spu Hash桶里面取数据。获取到这页Id所对应的Spu
        List<SpuDO> spuList = cacheComponent.getHashMultiAsList(CacheConst.PRT_SPU_HASH_BUCKET, page.getItems(), SpuDO.class);
        boolean hasEmptyObj = false;
        for (int i = 0; i < spuList.size(); i++) {
            SpuDO spuDO = spuList.get(i);
            if (spuDO == null) {
                // 去数据库获取
                SpuDO spuDOFromDB = spuMapper.selectOne(new QueryWrapper<SpuDO>().select(SPU_EXCLUDE_DETAIL_FIELDS).eq("id", Long.parseLong(page.getItems().get(i).replace("P", ""))));
                if (spuDOFromDB == null) {
                    // 这种情况几乎不可能
                    hasEmptyObj = true;
                    logger.error("[缓存数据库不一致] key=" + zsetBucketKey + ";item=" + page.getItems().get(i));
                    cacheComponent.delZSet(zsetBucketKey, page.getItems().get(i));
                } else {
                    // 更新 spuList 列表
                    spuList.set(i, spuDOFromDB);
                    // 获取ClassifyIds
                    List<Long> familyCategoryIds = categoryBizService.getCategoryFamily(spuDOFromDB.getCategoryId());
                    SpuDTO spuDTO = new SpuDTO();
                    BeanUtils.copyProperties(spuDOFromDB, spuDTO);
                    spuDTO.setCategoryIds(familyCategoryIds);
                    cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuDOFromDB.getId(), spuDTO);
                }
            }
        }
        if (hasEmptyObj) {
            spuList = spuList.stream().filter(item -> item != null).collect(Collectors.toList());
        }
        return page.replace(spuList);
    }

    /**
     * 从数据库中获取商品，此列表不包含detail(商品详情)字段
     *
     * @param pageNo
     * @param pageSize
     * @param categoryId
     * @param orderBy
     * @param isAsc
     * @param title
     * @return
     */
    public Page<SpuDO> getProductPageFromDB(Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        QueryWrapper<SpuDO> wrapper = new QueryWrapper<SpuDO>();
        wrapper.select(SPU_EXCLUDE_DETAIL_FIELDS);
        if (orderBy != null && isAsc != null) {
            if (isAsc) {
                wrapper.orderByAsc(orderBy);
            } else {
                wrapper.orderByDesc(orderBy);
            }
        }
        if (categoryId != null) {
            wrapper.eq("category_id", categoryId);
        }
        if (!ObjectUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        return spuMapper.selectPage(Page.div(pageNo, pageSize, SpuDO.class), wrapper);
    }

    /**
     * 从数据库中获取列表Id、销量 （在售）
     *
     * @param categoryId
     * @return
     */
    private List<SpuDO> getProductIdsOnSaleFromDB(Long categoryId) throws ServiceException {
        if (categoryId != null) {
            List<Long> categoryFamily = categoryBizService.getCategorySelfAndChildren(categoryId);
            return spuMapper.selectList(new QueryWrapper<SpuDO>().select("id", "sales").eq("status", StatusType.ACTIVE.getCode()).in("category_id", categoryFamily));
        } else {
            return spuMapper.selectList(new QueryWrapper<SpuDO>().eq("status", StatusType.ACTIVE.getCode()).select("id", "sales"));
        }
    }

    /**
     * 从数据库中获取SKU详细信息
     *
     * @param skuIds
     * @return
     */
    public List<SkuDTO> getSkuListByIds(List<Long> skuIds) {
        return skuMapper.getSkuDTOListByIds(skuIds);
    }

    /**
     * 从数据库中获取SPU,并且不查出detail字段
     *
     * @param id
     * @return
     */
    public SpuDO getProductByIdFromDB(Long id) {
        return spuMapper.selectOne(new QueryWrapper<SpuDO>().select(SPU_EXCLUDE_DETAIL_FIELDS).eq("id", id));
    }

    public SpuDO getProductByIdFromDBForUpdate(Long id) {
        return spuMapper.selectOne(new QueryWrapper<SpuDO>().select(SPU_EXCLUDE_DETAIL_FIELDS).eq("id", id).last(" FOR UPDATE"));
    }

    /**
     * TODO 从缓存中查出SPU，不带detail字段
     *
     * @param spuId
     * @return
     */
    public SpuDTO getProductByIdFromCache(Long spuId) throws ServiceException {
        SpuDTO spuDTO = null; // cacheComponent.getHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId, SpuDTO.class);
        if (spuDTO == null) {
            SpuDO spuDO = spuMapper.selectOne(new QueryWrapper<SpuDO>().select(ProductBizService.SPU_EXCLUDE_DETAIL_FIELDS).eq("id", spuId));
            if (spuDO != null) {
                spuDTO = new SpuDTO();
                BeanUtils.copyProperties(spuDO, spuDTO);
                List<Long> categoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
                spuDTO.setCategoryIds(categoryFamily);
//                cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId, spuDTO);
            } else {
                throw new BizServiceException(ExceptionDefinition.PRODUCT_NOT_EXIST);
            }
        }
        return spuDTO;
    }

    /**
     * 扣减库存
     *
     * @param skuStockMap
     */
    public void decSkuStock(Map<Long, Integer> skuStockMap) {
        skuStockMap.forEach((k, v) -> skuMapper.decSkuStock(k, v));
    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    /**
     * 增加商品销量
     *
     * @param skuSalesMap
     */
    public void incSpuSales(Map<Long, Integer> skuSalesMap) {
        skuSalesMap.forEach((k, v) -> {
            // 1. 更新数据库
            spuMapper.incSales(k, v);
            // 2. 更新缓存
            SpuDTO spuDtoFromCache = cacheComponent.getHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + k, SpuDTO.class);
            int isTheSame = -1;
            Double nullSource = cacheComponent.incZSetSource(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + null, "P" + k, v);
            if (nullSource != null) {
                isTheSame = (int) Math.round(nullSource);
            }
            for (Long categoryId : spuDtoFromCache.getCategoryIds()) {
                Double source = cacheComponent.incZSetSource(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + categoryId, "P" + k, v);
                if (source != null) {
                    int i = (int) Math.round(source);
                    if (i != isTheSame) {
                        // 若不相等
                        isTheSame = - 1;
                    }
                }
            }
            if (isTheSame == -1) {
                // 则重新冲数据库建立新的缓存 （一般情况下不会执行到这里）
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        // 锁定此ID的记录，防止脏读
                        SpuDO newSpuDO = ProductBizService.this.getProductByIdFromDBForUpdate(k);
                        List<Long> categoryFamily = categoryBizService.getCategoryFamily(newSpuDO.getCategoryId());
                        SpuDTO newSpuDto = new SpuDTO();
                        BeanUtils.copyProperties(newSpuDO, newSpuDto);
                        newSpuDto.setCategoryIds(categoryFamily);
                        cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + k, newSpuDto);
                        for (Long categoryId : categoryFamily) {
                            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + categoryId, newSpuDO.getSales(), "P" + newSpuDO.getId());
                        }
                        cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + null, newSpuDO.getSales(), "P" + newSpuDO.getId());
                    }
                });
            } else {
                spuDtoFromCache.setSales(isTheSame);
                cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + k, spuDtoFromCache);
            }
            // TODO 3. 更新搜索引擎
//            final SpuDTO finalSpuDto = spuDtoFromCache;
//            GlobalExecutor.execute(() -> {
//                if (searchEngine != null) {
//                    SpuDO newSpuDO = new SpuDO();
//                    BeanUtils.copyProperties(finalSpuDto, newSpuDO);
//                    searchEngine.dataTransmission(newSpuDO);
//                }
//            });
        });
    }

    /**
     * 调整商品库存
     *
     * @param barCode
     * @param stock
     */
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.MANDATORY)
    public boolean adjustSkuStock(String barCode, Integer stock) {
        SkuDO skuFromDB = skuMapper.selectOne(new QueryWrapper<SkuDO>().select("id", "stock").eq("bar_code", barCode));
        if (skuFromDB == null || skuFromDB.getStock().intValue() == stock.intValue()) {
            return false;
        }
        SkuDO skuDO = new SkuDO();
        skuDO.setId(skuFromDB.getId());
        skuDO.setStock(stock);
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + skuDO.getId(), skuDO.getStock() + "");
            }
        });
        return skuMapper.updateById(skuDO) > 0;
    }

    /**
     * 1.放入各个类目ZSET
     * 2.放入基本信息Hash表
     * 3.不放入详细信息Hash表，读取时再放入缓存
     *
     * @param spuDO
     */
    public void createSpuCache(SpuDO spuDO) {
        // 1. 放入类目缓存
        List<Long> categoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
        if (spuDO.getStatus().intValue() == StatusType.ACTIVE.getCode()) {
            for (Long categoryId : categoryFamily) {
                cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + categoryId, spuDO.getId(), "P" + spuDO.getId());
                cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + categoryId, spuDO.getPrice(), "P" + spuDO.getId());
                cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + categoryId, 0, "P" + spuDO.getId());
            }
            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + null, spuDO.getId(), "P" + spuDO.getId());
            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + null, spuDO.getPrice(), "P" + spuDO.getId());
            cacheComponent.putZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + null, 0, "P" + spuDO.getId());
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
     *
     * @param spuDO
     */
    public void deleteSpuCache(SpuDO spuDO) {
        // 1. 删除各个类目ZSET
        List<Long> categoryFamily = categoryBizService.getCategoryFamily(spuDO.getCategoryId());
        for (Long categoryId : categoryFamily) {
            cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + categoryId, "P" + spuDO.getId());
            cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + categoryId, "P" + spuDO.getId());
            cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + categoryId, "P" + spuDO.getId());
        }
        cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_ID_ZSET + null, "P" + spuDO.getId());
        cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_PRICE_ZSET + null, "P" + spuDO.getId());
        cacheComponent.delZSet(CacheConst.PRT_CATEGORY_ORDER_SALES_ZSET + null, "P" + spuDO.getId());
        // 2. 删除基本信息Hash表
        cacheComponent.delHashKey(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuDO.getId());
        // 3. 删除商品详情信息Hash表
        cacheComponent.delHashKey(CacheConst.PRT_SPU_DETAIL_HASH_BUCKET, "P" + spuDO.getId());
    }

}
