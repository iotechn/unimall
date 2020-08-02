package com.iotechn.unimall.app.api.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.biz.service.product.ProductBizService;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.domain.SpuSpecificationDO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;


/**
 * Created by rize on 2019/7/2.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductBizService productBizService;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private SpuAttributeMapper spuAttributeMapper;

    @Autowired
    private SpuSpecificationMapper spuSpecificationMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public Page<SpuDO> getProductPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        return productBizService.getProductPage(pageNo, pageSize, categoryId, orderBy, isAsc, title);
    }

    @Override
    public SpuDTO getProduct(Long spuId, Long userId) throws ServiceException {
        // 获取基本信息
        SpuDTO spuDTO = cacheComponent.getHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId, SpuDTO.class);
        if (spuDTO == null) {
            SpuDO spuDO = spuMapper.selectOne(new QueryWrapper<SpuDO>().select(ProductBizService.SPU_EXCLUDE_DETAIL_FIELDS).eq("id", spuId));
            if (spuDO != null) {
                spuDO.setDetail(null);
                cacheComponent.putHashObj(CacheConst.PRT_SPU_HASH_BUCKET, "P" + spuId, spuDO);
                spuDTO = new SpuDTO();
                BeanUtils.copyProperties(spuDO, spuDTO);
            } else {
                throw new AppServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
            }
        }
        // 封装其他信息
        SpuDTO detailSpuInfo = cacheComponent.getHashObj(CacheConst.PRT_SPU_DETAIL_HASH_BUCKET, "P" + spuId, SpuDTO.class);
        if (detailSpuInfo == null) {
            // 从DB中封装属性
            detailSpuInfo = new SpuDTO();
            // 1. SkuDOList
            List<SkuDO> skuDOList = skuMapper.selectList(new QueryWrapper<SkuDO>().eq("spu_id", spuDTO.getId()));
            detailSpuInfo.setSkuList(skuDOList);
            // 2. 图片列表
            List<String> imgs = imgMapper.getImgs(BizType.GOODS.getCode(), spuId);
            detailSpuInfo.setImgList(imgs);
            // 3. 设置detail
            SpuDO spuLoadDetail = spuMapper.selectOne(new QueryWrapper<SpuDO>().select("detail").eq("id", spuId));
            detailSpuInfo.setDetail(spuLoadDetail.getDetail());
            // 4. 设置商品属性
            List<SpuAttributeDO> attributeList = spuAttributeMapper.selectList(new QueryWrapper<SpuAttributeDO>().eq("spu_id", spuId));
            detailSpuInfo.setAttributeList(attributeList);
            // 5. 设置商品规格
            List<SpuSpecificationDO> specificationList = spuSpecificationMapper.selectList(new QueryWrapper<SpuSpecificationDO>().eq("spu_id", spuId));
            detailSpuInfo.setSpecificationList(specificationList);
            cacheComponent.putHashObj(CacheConst.PRT_SPU_DETAIL_HASH_BUCKET, "P" + spuId, detailSpuInfo);
        }
        // 记住忽略以上 N 个属性
        BeanUtils.copyProperties(spuDTO, detailSpuInfo, "skuList", "imgList", "detail", "attributeList", "specificationList");
        spuDTO = detailSpuInfo;
        // 封装各个Sku的库存
        spuDTO.getSkuList().forEach(item -> {
            String stockStr = cacheComponent.getHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + item.getId());
            if (stockStr == null) {
                // 几乎不可能。这次判断是悲观考虑
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        // 在事务中，获取最新的值
                        Integer stockForUpdate = skuMapper.getStockForUpdate(item.getId());
                        cacheComponent.putHashRaw(CacheConst.PRT_SKU_STOCK_BUCKET, "K" + item.getId(), stockForUpdate + "");
                        item.setStock(stockForUpdate);
                    }
                });
            } else {
                Integer stock = Integer.parseInt(stockStr);
                item.setStock(stock);
            }
        });
        //TODO 封装 SPU 运费模板 MicroFix 运费模板存在RPC。建议移动到detailSpuInfo中，但是编辑运费模板的时候需要删除detailSpuInfo的缓存
        // spuDTO.setFreightTemplate(freightTemplateBizService.getFreightTemplateById(spuDTO.getFreightTemplateId()));
        if (userId != null) {
            // 添加用户足迹
//            footprintBizService.addOrUpdateFootprint(spuId, userId);
            // TODO 封装用户收藏信息
            // spuDTO.setFavorite(cacheComponent.getHashRaw(ShoppingConst.CA_PRODUCT_FAVORITE_HASH_BUCKET + spuId, "U" + userId) != null);
        }
        // TODO 封装活动信息
//        if (spuDTO.getActivityType() != null && spuDTO.getActivityType() != SpuActivityType.NONE.getCode()
//                && spuDTO.getGmtActivityEnd() != null && spuDTO.getGmtActivityEnd().getTime() > System.currentTimeMillis()) {
//            // 若存在正在进行的活动，封装活动实例。这里活动的缓存应该由各个活动BizService内部决定是否使用缓存？如何设计缓存？
//            if (spuDTO.getActivityType() == SpuActivityType.GROUP_SHOP.getCode()) {
//                GroupShopDTO groupShopDTO = groupShopBizService.getGroupShopById(spuDTO.getActivityId());
//                spuDTO.setActivity(groupShopDTO);
//            }
//        }
        return spuDTO;
    }
}
