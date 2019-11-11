package com.iotechn.unimall.admin.api.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.biz.service.goods.GoodsBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.*;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.dto.goods.SpuTreeNodeDTO;
import com.iotechn.unimall.data.enums.BizType;
import com.iotechn.unimall.data.mapper.*;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.plugin.core.inter.IPluginUpdateGoods;
import com.iotechn.unimall.plugin.core.manager.PluginsManager;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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

    /**
     * 后台低频接口， 无需缓存
     * @return
     * @throws ServiceException
     */
    public List<SpuTreeNodeDTO> getSpuBigTree(Long adminId) throws  ServiceException{
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<CategoryDO>().orderBy("level"));
        List<SpuDO> spuDOS = spuMapper.getSpuTitleAll();
        List<SpuTreeNodeDTO> list = new ArrayList<>();
        Integer recordLevelOne = 0;
        Integer recordLevelTwo = 0;
        for (int i = 0; i < categoryDOS.size(); i++) {
            if(i != 0 && categoryDOS.get(i-1).getLevel().equals(0) && categoryDOS.get(i).getLevel().equals(1)){
                recordLevelOne = i;
            }
            if(i != 0 && categoryDOS.get(i-1).getLevel().equals(1) && categoryDOS.get(i).getLevel().equals(2)){
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
                if(!categoryOnJ.getParentId().equals(dtoOnI.getId())){
                    continue;
                }

                SpuTreeNodeDTO dtoOnJ = new SpuTreeNodeDTO();
                dtoOnJ.setLabel(categoryOnJ.getTitle());
                dtoOnJ.setValue("C_" + categoryOnJ.getId());
                dtoOnJ.setId(categoryOnJ.getId());
                dtoOnJ.setChildren(new LinkedList<>());
                
                for (int p = recordLevelTwo; p <categoryDOS.size() ; p++) {
                    CategoryDO categoryOnP = categoryDOS.get(p);    //三级类目
                    if(!categoryOnP.getParentId().equals(dtoOnJ.getId())){
                        continue;
                    }

                    SpuTreeNodeDTO dtoOnP = new SpuTreeNodeDTO();
                    dtoOnP.setLabel(categoryOnP.getTitle());
                    dtoOnP.setValue("C_" + categoryOnP.getId());
                    dtoOnP.setId(categoryOnP.getId());
                    dtoOnP.setChildren(new LinkedList<>());

                    for (int k = 0; k < spuDOS.size(); k++) {
                        if(k != 0 && spuDOS.get(k-1).getCategoryId().equals(dtoOnP.getId()) && !spuDOS.get(k).getCategoryId().equals(dtoOnP.getId())){
                            break;
                        }
                        SpuDO spuDO = spuDOS.get(k);        //商品
                        if(spuDO.getCategoryId().equals(dtoOnP.getId())){
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

//    @Override
//    public List<SpuTreeNodeDTO> getSpuBigTree(Long adminId) throws ServiceException {
//        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<>());
//        List<SpuTreeNodeDTO> list = categoryDOS.stream().filter((item) -> (item.getParentId().equals(0l))).map(item -> {
//            SpuTreeNodeDTO dto = new SpuTreeNodeDTO();
//            dto.setLabel(item.getTitle());
//            dto.setValue("C_" + item.getId());
//            dto.setId(item.getId());
//            dto.setChildren(new LinkedList<>());
//            return dto;
//        }).collect(Collectors.toList());
//        list.forEach(item -> {
//            categoryDOS.forEach(categoryDO -> {
//                if (categoryDO.getParentId().equals(item.getId())) {
//                    SpuTreeNodeDTO spuTreeNodeDTO = new SpuTreeNodeDTO();
//                    spuTreeNodeDTO.setChildren(new LinkedList<>());
//                    spuTreeNodeDTO.setValue("C_" + categoryDO.getId());
//                    spuTreeNodeDTO.setId(categoryDO.getId());
//                    spuTreeNodeDTO.setLabel(categoryDO.getTitle());
//                    item.getChildren().add(spuTreeNodeDTO);
//                    categoryDOS.forEach(subCategoryDO -> {
//                        if (subCategoryDO.getParentId().equals(spuTreeNodeDTO.getId())) {
//                            SpuTreeNodeDTO childSpuNodeDTO = new SpuTreeNodeDTO();
//                            childSpuNodeDTO.setId(subCategoryDO.getId());
//                            childSpuNodeDTO.setLabel(subCategoryDO.getTitle());
//                            childSpuNodeDTO.setValue("C_" + subCategoryDO.getId());
//                            spuTreeNodeDTO.getChildren().add(childSpuNodeDTO);
//                            List<SpuDO> spuList = spuMapper.getSpuTitleByCategoryId(subCategoryDO.getId());
//                            List<SpuTreeNodeDTO> spuTreeNodeList = spuList.stream().map(spuItem -> {
//                                SpuTreeNodeDTO goodsNode = new SpuTreeNodeDTO();
//                                goodsNode.setValue("G_" + spuItem.getId());
//                                goodsNode.setLabel(spuItem.getTitle());
//                                goodsNode.setId(spuItem.getId());
//                                return goodsNode;
//                            }).collect(Collectors.toList());
//                            childSpuNodeDTO.setChildren(spuTreeNodeList);
//                        }
//                    });
//                }
//            });
//        });
//        return list;
//    }

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
        List<String> barCodes = spuDTO.getSkuList().stream().map(item -> item.getBarCode()).collect(Collectors.toList());
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
        List<String> barCodes = new LinkedList<>();
        for (SkuDO skuDO : spuDTO.getSkuList()) {
            if (skuDO.getOriginalPrice() < skuDO.getPrice() || skuDO.getPrice() < skuDO.getVipPrice() || skuDO.getOriginalPrice() < skuDO.getVipPrice()) {
                throw new AdminServiceException(ExceptionDefinition.GOODS_PRICE_CHECKED_FAILED);
            }
            skuDO.setId(null);
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            skuDO.setFreezeStock(0);
            if (skuMapper.update(skuDO,
                    new EntityWrapper<SkuDO>()
                            .eq("bar_code", skuDO.getBarCode())) <= 0) {
                skuDO.setGmtCreate(now);
                skuMapper.insert(skuDO);
            }
            barCodes.add(skuDO.getBarCode());
        }
        //删除多余barCode
        skuMapper.delete(new EntityWrapper<SkuDO>().eq("spu_id", spuDO.getId()).notIn("bar_code",barCodes));
        //插入spuAttr
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuDTO.getId()));
        insertSpuAttribute(spuDTO, now);
        imgMapper.delete(new EntityWrapper<ImgDO>().eq("biz_id", spuDO.getId()).eq("biz_type", BizType.GOODS.getCode()));
        //插入IMG
        insertSpuImg(spuDTO, spuDO.getId(), now);
        goodsBizService.clearGoodsCache(spuDTO.getId());
        pluginUpdateInvoke(spuDTO.getId());
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
    public Page<SpuDTO> list(Integer page, Integer limit, Long categoryId, String title, Long adminId) throws ServiceException {
        return goodsBizService.getGoodsPage(page, limit, categoryId, "id", false, title);
    }

    @Override
    public SpuDTO detail(Long spuId, Long adminId) throws ServiceException {
        return goodsBizService.getGoods(spuId, null);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long spuId, Long adminId) throws ServiceException {
        if (spuMapper.deleteById(spuId) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_EXIST);
        }
        cartMapper.delete(new EntityWrapper<CartDO>().in("sku_id", skuMapper.getSkuIds(spuId)));
        skuMapper.delete(new EntityWrapper<SkuDO>().eq("spu_id", spuId));
        imgMapper.delete(new EntityWrapper<ImgDO>().eq("biz_id", spuId).eq("biz_type", BizType.GOODS.getCode()));
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuId));
        goodsBizService.clearGoodsCache(spuId);
        pluginUpdateInvoke(spuId);
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
}
