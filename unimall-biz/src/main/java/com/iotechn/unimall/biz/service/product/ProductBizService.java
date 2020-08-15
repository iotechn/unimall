package com.iotechn.unimall.biz.service.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.goods.SkuDTO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.mapper.SkuMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.data.search.SearchEngine;
import com.iotechn.unimall.data.search.exception.SearchEngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private CategoryBizService categoryBizService;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private SearchEngine searchEngine;

    /**
     * SPU 排除掉detail字段的其他字段名字形成的数组
     */
    public static final String[] SPU_EXCLUDE_DETAIL_FIELDS;

    private static final Logger logger = LoggerFactory.getLogger(ProductBizService.class);

    static {
        Field[] fields = SpuDO.class.getFields();
        SPU_EXCLUDE_DETAIL_FIELDS = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            TableField annotation = field.getAnnotation(TableField.class);
            if (annotation != null) {
                SPU_EXCLUDE_DETAIL_FIELDS[i] = annotation.value();
            } else {
                SPU_EXCLUDE_DETAIL_FIELDS[i] = field.getName();
            }
        }
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
            try {
                if (this.searchEngine != null) {
                    // TODO 使用搜索引擎逻辑
                }
            } catch (SearchEngineException e) {
                logger.error("[获取商品列表] 搜素引擎 异常", e);
            }
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
            throw new AppServiceException(ExceptionDefinition.GOODS_ORDER_BY_WAY_ILLEGAL);
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
        if (!StringUtils.isEmpty(title)) {
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
            return spuMapper.selectList(new QueryWrapper<SpuDO>().select("id", "sales").in("category_id", categoryFamily));
        } else {
            return spuMapper.selectList(new QueryWrapper<SpuDO>().select("id", "sales"));
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

    /**
     * 从缓存中查出SPU，不带detail字段
     *
     * @param spuId
     * @return
     */
    public SpuDTO getProductByIdFromCache(Long spuId) throws ServiceException {
        SpuDTO spuDTO = cacheComponent.getHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId, SpuDTO.class);
        if (spuDTO == null) {
            SpuDO spuDO = spuMapper.selectOne(new QueryWrapper<SpuDO>().select(ProductBizService.SPU_EXCLUDE_DETAIL_FIELDS).eq("id", spuId));
            if (spuDO != null) {
                cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId, spuDO);
                spuDTO = new SpuDTO();
                BeanUtils.copyProperties(spuDO, spuDTO);
            } else {
                throw new AppServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
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
        skuStockMap.forEach((k, v) -> skuMapper.decStock(k, v));
    }

    /**
     * 增加商品销量
     *
     * @param skuSalesMap
     */
    public void incSpuSales(Map<Long, Integer> skuSalesMap) {
        // TODO 增加销量，需要更新缓存
        skuSalesMap.forEach((k, v) -> spuMapper.incSales(k, v));
    }

}
