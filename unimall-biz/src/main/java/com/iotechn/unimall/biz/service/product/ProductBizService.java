package com.iotechn.unimall.biz.service.product;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.biz.service.appriaise.AppraiseBizService;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.biz.service.collect.CollectBizService;
import com.iotechn.unimall.biz.service.footpring.FootprintBizService;
import com.iotechn.unimall.biz.service.freight.FreightBizService;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/12.
 */
@Service
public class ProductBizService {

    /**
     * SPU 分页缓存
     */
    public static final String CA_SPU_PAGE_PREFIX = "CA_SPU_PAGE_";

    /**
     * SPU DTO 缓存  CA_SPU_+spuId
     */
    public static final String CA_SPU_PREFIX = "CA_SPU_";

    /**
     * SPU 销量缓存
     */
    private static final String CA_SPU_SALES_HASH = "CA_SPU_SALES_HASH";

    /**
     * SPU DO 缓存，加速 getById...  hashKey = 'S' + spuId
     */
    private static final String CA_SPU_HASH = "CA_SPU_HASH";

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuAttributeMapper spuAttributeMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    @Autowired
    private FreightBizService freightBizService;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private CollectBizService collectBizService;

    @Autowired
    private AppraiseBizService appraiseBizService;

    /**
     * SPU 排除掉detail字段的其他属性
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
        List<Long> categoryFamily = categoryBizService.getCategoryFamily(categoryId);
        return spuMapper.selectList(new QueryWrapper<SpuDO>().select("id", "sales").in("category_id", categoryFamily));
    }

    /**
     * TODO 将会删除 通过Id获取SpuDO 领域对象
     *
     * @param spuId
     * @return
     * @throws ServiceException
     */
    public SpuDO getSpuById(Long spuId) throws ServiceException {
        SpuDO objFromCache = cacheComponent.getHashObj(CA_SPU_HASH, "S" + spuId, SpuDO.class);
        if (objFromCache != null) {
            return objFromCache;
        }
        SpuDO spuDO = spuMapper.selectById(spuId);
        if (spuDO == null) {
            throw new AppServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        cacheComponent.putHashObj(CA_SPU_HASH, "S" + spuDO, spuDO, Const.CACHE_ONE_DAY);
        return spuDO;
    }

    /**
     * TODO 将会删除
     * @param spuId
     * @param userId
     * @return
     * @throws ServiceException
     */
    public SpuDTO getProduct(Long spuId, Long userId) throws ServiceException {
        SpuDTO spuDTOFromCache = cacheComponent.getObj(CA_SPU_PREFIX + spuId, SpuDTO.class);
        if (spuDTOFromCache != null) {
            packSpuCollectInfo(spuDTOFromCache, userId);
            //获取第一页评论
            Page<AppraiseResponseDTO> spuAppraise = appraiseBizService.getSpuAllAppraise(spuId, 1, 10);
            spuDTOFromCache.setAppraisePage(spuAppraise);
            if (userId != null && userId == 0l) {
                // 从管理员后台进入，返回最新的库存
                List<SkuDO> skuDOList = skuMapper.selectList(
                        new QueryWrapper<SkuDO>()
                                .eq("spu_id", spuId));
                spuDTOFromCache.setSkuList(skuDOList);
                int sum = skuDOList.stream().mapToInt(item -> item.getStock()).sum();
                spuDTOFromCache.setStock(sum);
            }
            return spuDTOFromCache;
        }
        SpuDO spuDO = spuMapper.selectById(spuId);
        SpuDTO spuDTO = new SpuDTO();
        BeanUtils.copyProperties(spuDO, spuDTO);
        spuDTO.setImgList(imgMapper.getImgs(BizType.GOODS.getCode(), spuId));
        List<SkuDO> skuDOList = skuMapper.selectList(
                new QueryWrapper<SkuDO>()
                        .eq("spu_id", spuId));
        spuDTO.setSkuList(skuDOList);
        //类目族
        spuDTO.setCategoryIds(categoryBizService.getCategoryFamily(spuDO.getCategoryId()));
        String salesStr = cacheComponent.getHashRaw(CA_SPU_SALES_HASH, "S" + spuId);
        if (!StringUtils.isEmpty(salesStr)) {
            spuDTO.setSales(new Integer(salesStr));
        }
        int sum = skuDOList.stream().mapToInt(item -> item.getStock()).sum();
        spuDTO.setStock(sum);
        //获取商品属性
        List<SpuAttributeDO> spuAttributeList = spuAttributeMapper.selectList(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        spuDTO.setAttributeList(spuAttributeList);
        //获取运费模板
        FreightTemplateDTO templateDTO = freightBizService.getTemplateById(spuDO.getFreightTemplateId());
        spuDTO.setFreightTemplate(templateDTO);
        //放入缓存
        cacheComponent.putObj(CA_SPU_PREFIX + spuId, spuDTO, Const.CACHE_ONE_DAY / 2);
        packSpuCollectInfo(spuDTO, userId);
        //获取第一页评论
        Page<AppraiseResponseDTO> spuAppraise = appraiseBizService.getSpuAllAppraise(spuId, 1, 10);
        spuDTO.setAppraisePage(spuAppraise);
        return spuDTO;
    }

    public void clearGoodsCache(Long spuId) {

        cacheComponent.del(CA_SPU_PREFIX + spuId);

        cacheComponent.delPrefixKey(CA_SPU_PAGE_PREFIX);

        cacheComponent.delHashKey(CA_SPU_HASH, "S" + spuId);

    }

    private void packSpuCollectInfo(SpuDTO spuDTO, Long userId) throws ServiceException {
        if (userId != null) {
            Boolean collectStatus = collectBizService.getCollectBySpuId(spuDTO.getId(), userId);
            spuDTO.setCollect(collectStatus);
        }
    }

}
