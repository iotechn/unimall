package com.iotechn.unimall.admin.api.goods;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.admin.api.admin.AdminService;
import com.iotechn.unimall.biz.service.goods.GoodsBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.domain.SpuAttributeDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.goods.SpuDTO;
import com.iotechn.unimall.data.dto.goods.SpuTreeNodeDTO;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import com.iotechn.unimall.data.mapper.SkuMapper;
import com.iotechn.unimall.data.mapper.SpuAttributeMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private GoodsBizService goodsBizService;

    /**
     * 后台低频接口， 无需缓存
     * @return
     * @throws ServiceException
     */
    @Override
    public List<SpuTreeNodeDTO> getSpuBigTree(Long adminId) throws ServiceException {
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<>());
        List<SpuTreeNodeDTO> list = categoryDOS.stream().filter((item) -> (item.getParentId().equals(0l))).map(item -> {
            SpuTreeNodeDTO dto = new SpuTreeNodeDTO();
            dto.setLabel(item.getTitle());
            dto.setValue("C_" + item.getId());
            dto.setId(item.getId());
            dto.setChildren(new LinkedList<>());
            return dto;
        }).collect(Collectors.toList());
        list.forEach(item -> {
            categoryDOS.forEach(categoryDO -> {
                if (categoryDO.getParentId().equals(item.getId())) {
                    SpuTreeNodeDTO spuTreeNodeDTO = new SpuTreeNodeDTO();
                    spuTreeNodeDTO.setChildren(new LinkedList<>());
                    spuTreeNodeDTO.setValue("C_" + categoryDO.getId());
                    spuTreeNodeDTO.setId(categoryDO.getId());
                    spuTreeNodeDTO.setLabel(categoryDO.getTitle());
                    item.getChildren().add(spuTreeNodeDTO);
                    categoryDOS.forEach(subCategoryDO -> {
                        if (subCategoryDO.getParentId().equals(spuTreeNodeDTO.getId())) {
                            SpuTreeNodeDTO childSpuNodeDTO = new SpuTreeNodeDTO();
                            childSpuNodeDTO.setId(subCategoryDO.getId());
                            childSpuNodeDTO.setLabel(subCategoryDO.getTitle());
                            childSpuNodeDTO.setValue("C_" + subCategoryDO.getId());
                            spuTreeNodeDTO.getChildren().add(childSpuNodeDTO);
                            List<SpuDO> spuList = spuMapper.getSpuTitleByCategoryId(subCategoryDO.getId());
                            List<SpuTreeNodeDTO> spuTreeNodeList = spuList.stream().map(spuItem -> {
                                SpuTreeNodeDTO goodsNode = new SpuTreeNodeDTO();
                                goodsNode.setValue("G_" + spuItem.getId());
                                goodsNode.setLabel(spuItem.getTitle());
                                goodsNode.setId(spuItem.getId());
                                return goodsNode;
                            }).collect(Collectors.toList());
                            childSpuNodeDTO.setChildren(spuTreeNodeList);
                        }
                    });
                }
            });
        });
        return list;
    }

    @Override
    public String create(SpuDTO spuDTO, Long adminId) throws ServiceException {
        //参数校验
        if (CollectionUtils.isEmpty(spuDTO.getSkuList())) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_SKU_LIST_EMPTY);
        }
        if (spuDTO.getId() != null) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_CREATE_HAS_ID);
        }
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuDO.setGmtCreate(now);
        return null;
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
        Date now = new Date();
        SpuDO spuDO = new SpuDO();
        BeanUtils.copyProperties(spuDTO, spuDO);
        spuDO.setGmtUpdate(now);
        spuMapper.updateById(spuDO);
        skuMapper.delete(new EntityWrapper<SkuDO>().eq("spu_id", spuDTO.getId()));
        for (SkuDO skuDO : spuDTO.getSkuList()) {
            skuDO.setSpuId(spuDO.getId());
            skuDO.setGmtUpdate(now);
            skuDO.setGmtCreate(now);
            skuDO.setFreezeStock(0);
            skuMapper.insert(skuDO);
        }
        spuAttributeMapper.delete(new EntityWrapper<SpuAttributeDO>().eq("spu_id", spuDTO.getId()));
        for (SpuAttributeDO spuAttributeDO : spuDTO.getAttributeList()) {
            spuAttributeDO.setSpuId(spuDTO.getId());
            spuAttributeDO.setGmtUpdate(now);
            spuAttributeDO.setGmtCreate(now);
            spuAttributeMapper.insert(spuAttributeDO);
        }
        goodsBizService.clearGoodsCache(spuDTO.getId());
        return "ok";
    }

    @Override
    public Page<SpuDTO> list(Integer page, Integer limit, Long categoryId, String title, Long adminId) throws ServiceException {
        return goodsBizService.getGoodsPage(page, limit, categoryId, "id", title);
    }

    @Override
    public SpuDTO detail(Long spuId, Long adminId) throws ServiceException {
        return goodsBizService.getGoods(spuId, null);
    }
}
