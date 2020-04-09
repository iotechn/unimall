package com.iotechn.unimall.admin.api.goods;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.iotechn.unimall.biz.service.goods.GoodsBizService;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.dto.goods.SpuTreeNodeDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.enums.SpuStatusType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.plugin.core.inter.IPluginUpdateGoods;
import com.iotechn.unimall.plugin.core.manager.PluginsManager;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/11.
 */
@Service
public class AdminGoodsServiceImpl implements AdminGoodsService {

    @Autowired
    private CategoryMapper categoryMapper;

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
    private GoodsBizService goodsBizService;

    @Autowired
    private PluginsManager pluginsManager;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private GroupShopMapper groupShopMapper;

    private static final Column[] spuBaseColumns = {
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

    /**
     * 后台低频接口， 无需缓存
     *
     * @return
     * @throws ServiceException
     */
    public List<SpuTreeNodeDTO> getSpuBigTree(Long adminId) throws ServiceException {
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<CategoryDO>().orderBy("level"));
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
                            dtoOnK.setChildren(new LinkedList<>());
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
    public String create(SpuDTO spuDTO, Long adminId) throws ServiceException {
        //参数校验
        if (CollectionUtils.isEmpty(spuDTO.getSkuList())) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_SKU_LIST_EMPTY);
        }
        if (spuDTO.getId() != null) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_CREATE_HAS_ID);
        }
        if (spuDTO.getOriginalPrice() < spuDTO.getPrice() || spuDTO.getPrice() < spuDTO.getVipPrice() || spuDTO.getOriginalPrice() < spuDTO.getVipPrice()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
        }
        //校验Sku是否重复
        Set<String> barCodes = spuDTO.getSkuList().stream().map(item -> item.getBarCode()).collect(Collectors.toSet());
        if (barCodes.size() != spuDTO.getSkuList().size()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_UPLOAD_SKU_BARCODE_REPEAT);
        }
        List<SkuDO> existSkuDO = skuMapper.selectList(new EntityWrapper<SkuDO>().in("bar_code", barCodes));
        if (!CollectionUtils.isEmpty(existSkuDO)) {
            String spuIds = existSkuDO.stream().map(item -> item.getSpuId().toString()).collect(Collectors.joining(","));
            String skuIds = existSkuDO.stream().map(item -> item.getBarCode()).collect(Collectors.joining(","));
            throw new AdminServiceException(ExceptionDefinition
                    .buildVariableException(ExceptionDefinition.GOODS_CREATE_BARCODE_REPEAT, spuIds, skuIds));
        }
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuDO.setGmtCreate(now);
        spuDO.setSales(0);
        spuMapper.insert(spuDO);
        spuDTO.setId(spuDO.getId());
        //插入SKU表
        for (SkuDO skuDO : spuDTO.getSkuList()) {
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
            }
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            skuDO.setGmtCreate(now);
            skuDO.setFreezeStock(0);
            skuMapper.insert(skuDO);
        }
        //插入spuAttr
        insertSpuAttribute(spuDTO, now);
        //插入IMG
        insertSpuImg(spuDTO, spuDO.getId(), now);
        goodsBizService.clearGoodsCache(spuDO.getId());
        pluginUpdateInvoke(spuDO.getId());

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String edit(SpuDTO spuDTO, Long adminId) throws ServiceException {
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
            skuDO.setFreezeStock(null);
            if (skuMapper.update(skuDO,
                    new EntityWrapper<SkuDO>()
                            .eq("bar_code", skuDO.getBarCode())) <= 0) {
                skuDO.setFreezeStock(0);
                skuDO.setGmtCreate(now);
                skuMapper.insert(skuDO);
            }
            boolean succ = barCodes.add(skuDO.getBarCode());
            if (!succ) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_UPLOAD_SKU_BARCODE_REPEAT);
            }
        }
        //删除多余barCode
        skuMapper.delete(new EntityWrapper<SkuDO>().eq("spu_id", spuDO.getId()).notIn("bar_code", barCodes));
        //插入spuAttr
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuDTO.getId()));
        insertSpuAttribute(spuDTO, now);
        imgMapper.delete(new EntityWrapper<ImgDO>().eq("biz_id", spuDO.getId()).eq("biz_type", BizType.GOODS.getCode()));
        //插入IMG
        insertSpuImg(spuDTO, spuDO.getId(), now);
        goodsBizService.clearGoodsCache(spuDTO.getId());
        pluginUpdateInvoke(spuDTO.getId());

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return "ok";
    }

    private void insertSpuAttribute(SpuDTO spuDTO, Date now) {
        if (!CollectionUtils.isEmpty(spuDTO.getAttributeList())) {
            for (SpuAttributeDO spuAttributeDO : spuDTO.getAttributeList()) {
                spuAttributeDO.setSpuId(spuDTO.getId());
                spuAttributeDO.setGmtUpdate(now);
                spuAttributeDO.setGmtCreate(now);
                spuAttributeMapper.insert(spuAttributeDO);
            }
        }
    }

    private void insertSpuImg(SpuDTO spuDTO, Long bizId, Date now) {
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
        Wrapper<SpuDO> wrapper = new EntityWrapper<SpuDO>();

        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }

        if (categoryId != null && categoryId != 0L) {
            List<CategoryDO> childrenList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", categoryId));
            // 传入类目拥有的三级类目集合
            LinkedList<Long> childrenIds = new LinkedList<>();

            // 传入类目没有子类目
            if (CollectionUtils.isEmpty(childrenList)) {
                // 则传入类目为三级类目
                childrenIds.add(categoryId);
            } else {

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
            }
            wrapper.in("category_id", childrenIds);
        }

        if (status != null) {
            wrapper.eq("status", status.intValue() <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode());
        }

        if (barcode != null) {
            List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("bar_code", barcode));
            if (!CollectionUtils.isEmpty(skuDOList)) {
                SkuDO skuDO = skuDOList.get(0);
                wrapper.eq("id", skuDO.getSpuId());
            }
        }

        wrapper.setSqlSelect(spuBaseColumns);
        List<SpuDO> spuDOS = spuMapper.selectPage(new RowBounds((page - 1) * limit, limit), wrapper);

        //组装SPU
        List<SpuDTO> spuDTOList = new ArrayList<>();
        spuDOS.forEach(item -> {
            SpuDTO spuDTO = new SpuDTO();
            BeanUtils.copyProperties(item, spuDTO);
            List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("spu_id", item.getId()));
            spuDTO.setSkuList(skuDOList);
            spuDTOList.add(spuDTO);
        });

        Integer count = spuMapper.selectCount(wrapper);
        Page<SpuDTO> dtoPage = new Page<>(spuDTOList, page, limit, count);

        return dtoPage;
    }

    @Override
    public SpuDTO detail(Long spuId, Long adminId) throws ServiceException {
        return goodsBizService.getGoods(spuId, 0l);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long spuId, Long adminId) throws ServiceException {

        GroupShopDO groupShopDO = new GroupShopDO();
        groupShopDO.setSpuId(spuId);
        if(groupShopMapper.selectOne(groupShopDO) != null){
            throw new AdminServiceException(ExceptionDefinition.GROUP_SHOP_ALREAD_EXIST);
        }

        if (spuMapper.deleteById(spuId) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        cartMapper.delete(new EntityWrapper<CartDO>().in("sku_id", skuMapper.getSkuIds(spuId)));
        skuMapper.delete(new EntityWrapper<SkuDO>().eq("spu_id", spuId));
        imgMapper.delete(new EntityWrapper<ImgDO>().eq("biz_id", spuId).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        goodsBizService.clearGoodsCache(spuId);
        pluginUpdateInvoke(spuId);

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String batchDelete(String idsJson, Long adminId) throws ServiceException {
        List<Long> ids = JSONObject.parseArray(idsJson, Long.class);
        if (CollectionUtils.isEmpty(ids)) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        if (spuMapper.delete(new EntityWrapper<SpuDO>().in("id", ids)) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        List<Long> skuIds = skuMapper.selectSkuIdsBySpuIds(ids);
        cartMapper.delete(new EntityWrapper<CartDO>().in("sku_id", skuIds));
        skuMapper.delete(new EntityWrapper<SkuDO>().in("spu_id", ids));
        imgMapper.delete(new EntityWrapper<ImgDO>().in("biz_id", ids).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().in("spu_id", ids));
        for (Long spuId : ids) {
            goodsBizService.clearGoodsCache(spuId);
        }
        return "ok";
    }

    private void pluginUpdateInvoke(Long spuId) {
        List<IPluginUpdateGoods> plugins = pluginsManager.getPlugins(IPluginUpdateGoods.class);
        if (!CollectionUtils.isEmpty(plugins)) {
            for (IPluginUpdateGoods updateGoods : plugins) {
                updateGoods.invokeGoodsUpdate(spuId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SpuDTO freezeOrActivation(Long spuId, Integer status, Long adminId) throws ServiceException {
        SpuDO spuDO = spuMapper.selectById(spuId);

        if (spuDO == null) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }

        status = status <= SpuStatusType.STOCK.getCode() ? SpuStatusType.STOCK.getCode() : SpuStatusType.SELLING.getCode();

        if (spuDO.getStatus().intValue() == status.intValue()) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NEED_STATUS_ERROR);
        }

        spuDO.setStatus(status);
        spuDO.setGmtUpdate(new Date());
        if (spuMapper.updateById(spuDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_UPDATE_SQL_FAILED);
        }

        SpuDTO spuDTO = new SpuDTO();
        BeanUtils.copyProperties(spuDO, spuDTO);
        List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("spu_id", spuDO.getId()));
        spuDTO.setSkuList(skuDOList);

        cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
        return spuDTO;
    }
}
