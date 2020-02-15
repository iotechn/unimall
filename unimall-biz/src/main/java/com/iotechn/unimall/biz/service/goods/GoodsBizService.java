package com.iotechn.unimall.biz.service.goods;

import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
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
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.appraise.AppraiseResponseDTO;
import com.iotechn.unimall.data.dto.freight.FreightTemplateDTO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.enums.SpuStatusType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by rize on 2019/7/12.
 */
@Service
public class GoodsBizService {

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
    private CategoryMapper categoryMapper;

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
    private FootprintBizService footprintBizService;

    @Autowired
    private AppraiseBizService appraiseBizService;

    @Autowired
    private GroupShopSkuMapper groupShopSkuMapper;

    private static final Column[] baseColumns = {
            Column.create().column("id"),
            Column.create().column("original_price").as("originalPrice"),
            Column.create().column("price"),
            Column.create().column("vip_price").as("vipPrice"),
            Column.create().column("title"),
            Column.create().column("sales"),
            Column.create().column("img"),
            Column.create().column("description"),
            Column.create().column("category_id").as("categoryId"),
            Column.create().column("freight_template_id").as("freightTemplateId"),
            Column.create().column("unit"),
            Column.create().column("status")};

    public Page<SpuDTO> getGoodsPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        Wrapper<SpuDO> wrapper = new EntityWrapper<SpuDO>();


        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        } else {
            //若关键字为空，尝试从缓存取列表
            Page objFromCache = cacheComponent.getObj(CA_SPU_PAGE_PREFIX + categoryId + "_" + pageNo + "_" + pageSize + "_" + orderBy + "_" + isAsc, Page.class);
            if (objFromCache != null) {
                return objFromCache;
            }
        }

        if(orderBy != null && isAsc != null){
            wrapper.orderBy(orderBy, isAsc);
        }

        if (categoryId != null && categoryId != 0L) {
            List<CategoryDO> childrenList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", categoryId));

            if (CollectionUtils.isEmpty(childrenList)) {
                //目标节点为叶子节点,即三级类目
                wrapper.eq("category_id", categoryId);
            } else {
                //目标节点存在子节点
                LinkedList<Long> childrenIds = new LinkedList<>();
                CategoryDO categoryDO = categoryMapper.selectById(categoryId);

                // 检验传入类目是一级还是二级类目
                if (categoryDO.getParentId() != 0L) {
                    //二级分类
                    childrenList.forEach(item -> {
                        childrenIds.add(item.getId());
                    });
                } else {
                    //一级分类
                    childrenList.forEach(item -> {
                        List<CategoryDO> leafList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", item.getId()));
                        if (!CollectionUtils.isEmpty(leafList)) {
                            leafList.forEach(leafItem -> {
                                childrenIds.add(leafItem.getId());
                            });
                        }
                    });
                }
                wrapper.in("category_id", childrenIds);
            }
        }

        wrapper.eq("status", SpuStatusType.SELLING.getCode());
        wrapper.setSqlSelect(baseColumns);
        List<SpuDO> spuDOS = spuMapper.selectPage(new RowBounds((pageNo - 1) * pageSize, pageSize), wrapper);
        //组装SPU
        List<SpuDTO> spuDTOList = new ArrayList<>();
        Map<String, String> salesHashAll = cacheComponent.getHashAll(CA_SPU_SALES_HASH);
        spuDOS.forEach(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            if (salesHashAll != null) {
                String salesStr = salesHashAll.get("S" + item.getId());
                if (!StringUtils.isEmpty(salesStr)) {
                    spuDTO.setSales(new Integer(salesStr));
                }
            }
            spuDTOList.add(spuDTO);
        });

        Integer count = spuMapper.selectCount(wrapper);
        Page<SpuDTO> page = new Page<>(spuDTOList, pageNo, pageSize, count);
        if (StringUtils.isEmpty(title)) {
            //若关键字为空，制作缓存
            cacheComponent.putObj(CA_SPU_PAGE_PREFIX + categoryId + "_" + pageNo + "_" + pageSize + "_" + orderBy + "_" + isAsc, page, Const.CACHE_ONE_DAY);
        }
        return page;
    }


    /**
     * 通过Id获取SpuDO 领域对象
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

    public SpuDTO getGoods(Long spuId, Long userId) throws ServiceException {
        SpuDTO spuDTOFromCache = cacheComponent.getObj(CA_SPU_PREFIX + spuId, SpuDTO.class);
        if (spuDTOFromCache != null) {
            packSpuCollectInfo(spuDTOFromCache, userId);
            //获取第一页评论
            Page<AppraiseResponseDTO> spuAppraise = appraiseBizService.getSpuAllAppraise(spuId, 1, 10);
            spuDTOFromCache.setAppraisePage(spuAppraise);
            if (userId != null && userId != 0l) {
                footprintBizService.addOrUpdateFootprint(userId, spuId);
            }
            if (userId != null && userId == 0l) {
                // 从管理员后台进入，返回最新的库存
                List<SkuDO> skuDOList = skuMapper.selectList(
                        new EntityWrapper<SkuDO>()
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
                new EntityWrapper<SkuDO>()
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
        List<SpuAttributeDO> spuAttributeList = spuAttributeMapper.selectList(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuId));
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
        if (userId != null) {
            footprintBizService.addOrUpdateFootprint(userId, spuId);
        }
        return spuDTO;
    }

    public void clearGoodsCache(Long spuId) {

        cacheComponent.del(CA_SPU_PREFIX + spuId);

        cacheComponent.delPrefixKey(CA_SPU_PAGE_PREFIX);

        cacheComponent.delHashObj(CA_SPU_HASH, "S" + spuId);

    }

    private void packSpuCollectInfo(SpuDTO spuDTO, Long userId) throws ServiceException {
        if (userId != null) {
            Boolean collectStatus = collectBizService.getCollectBySpuId(spuDTO.getId(), userId);
            spuDTO.setCollect(collectStatus);
        }
    }

}
