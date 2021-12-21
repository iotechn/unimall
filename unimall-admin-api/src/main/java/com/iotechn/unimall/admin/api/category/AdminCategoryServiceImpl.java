package com.iotechn.unimall.admin.api.category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dobbinsoft.fw.core.exception.AdminServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.component.CacheComponent;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.data.domain.AdvertDO;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.enums.AdvertUnionType;
import com.iotechn.unimall.data.enums.CategoryLevelType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import com.iotechn.unimall.data.mapper.AdvertMapper;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/12.
 */
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private AdvertMapper advertMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    @Autowired
    private CacheComponent cacheComponent;

    /**
     * 获取两级类目树,用于添加类目时选择使用 商品、优惠券
     */
    @Override
    public List<CategoryDTO> categoryTree() throws ServiceException {
        return categoryBizService.categorySecondLevelTree();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDO create(String title, Long parentId, String picUrl, Long adminId) throws ServiceException {
        CategoryDO parent = null;
        CategoryDO categoryDO = new CategoryDO();
        if (!parentId.equals(0l)) {
            parent = categoryMapper.selectById(parentId);
            if (parent == null || parent.getLevel().intValue() > CategoryLevelType.TWO.getCode()) {
                throw new AdminServiceException(ExceptionDefinition.CATEGORY_PARENT_NODE_INFORMATION_ERROR);
            }
            categoryDO.setLevel(parent.getLevel() + 1);
            if (parent.getLevel().intValue() == CategoryLevelType.ONE.getCode().intValue()) {
                categoryDO.setFirstLevelId(parentId);
            } else if (parent.getLevel().intValue() == CategoryLevelType.ONE.getCode().intValue()) {
                categoryDO.setFirstLevelId(parent.getFirstLevelId());
            }
        } else {
            categoryDO.setLevel(CategoryLevelType.ONE.getCode());
        }
        categoryDO.setParentId(parentId);
        categoryDO.setPicUrl(picUrl);
        categoryDO.setTitle(title);
        Date now = new Date();
        categoryDO.setGmtCreate(now);
        categoryDO.setGmtUpdate(now);
        if (categoryMapper.insert(categoryDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        this.clearCache();
        return categoryDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long id, Long adminId) throws ServiceException {
        /**
         *  删除类目时 不允许类目有子类目
         *  删除类目时 不允许类目有商品
         *  删除类目时 不允许有广告关联类目
         */
        Integer countCategory = categoryMapper.selectCount(new QueryWrapper<CategoryDO>().eq("parent_id", id));
        Integer countSpu = spuMapper.selectCount(new QueryWrapper<SpuDO>().eq("category_id", id));
        Integer countAdvert = advertMapper.selectCount(
                new QueryWrapper<AdvertDO>()
                        .eq("union_type", AdvertUnionType.CATEGORY.getCode())
                        .eq("union_value", id.toString()));
        if (countCategory != 0 || countSpu != 0) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_OUGHT_TO_EMPTY);
        }
        if (countAdvert != 0) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_EXIST_ADVERT);
        }
        if (categoryMapper.deleteById(id) > 0) {
            this.clearCache();
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO update(Long id, String title, Long parentId, String iconUrl, String picUrl, Long adminId) throws ServiceException {
        CategoryDO categoryParent = null;
        if (parentId.longValue() != 0L) {
            categoryMapper.selectById(parentId);
        }
        CategoryDO category = categoryMapper.selectById(id);

        // 1. 检验类目是否存在,非一级类目的父类目是否存在
        if (category == null || (categoryParent == null && !parentId.equals(0L))) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_NOT_FIND_PARENT_NODE_OR_NODE);
        }

        CategoryDO updateDO = new CategoryDO();

        // 如果是一级类目修改，就不会变更父类目；如果没有变更父类目，也不会变更对应的一二级长辈类目
        if (!category.getParentId().equals(0L) && !category.getParentId().equals(parentId)) {
            // 节点只能平级变更
            if (category.getLevel().intValue() != categoryParent.getLevel().intValue() + 1) {
                throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
            }
            if (categoryParent.getLevel().intValue() == CategoryLevelType.ONE.getCode().intValue()) {
                updateDO.setFirstLevelId(parentId);
            } else if (categoryParent.getLevel().intValue() == CategoryLevelType.TWO.getCode().intValue()) {
                updateDO.setFirstLevelId(categoryParent.getFirstLevelId());
            }
        }
        updateDO.setId(id);
        updateDO.setGmtUpdate(new Date());
        updateDO.setParentId(parentId);
        updateDO.setTitle(title);
        updateDO.setPicUrl(picUrl);
        if (categoryMapper.updateById(updateDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_UPDATE_FAILURE);
        }

        // 如果变更了父节点，就需要变更当前节点的子孙节点的一二级长辈节点
        if (!category.getParentId().equals(parentId)) {
            // 只有二三级节点能变更父节点，其中只有二级节点变更后，会造成子节点的长辈节点变更。
            if (updateDO.getLevel().intValue() == 1) {
                QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
                wrapper.eq("parent_id", updateDO.getId());
                CategoryDO child = new CategoryDO();
                child.setFirstLevelId(updateDO.getFirstLevelId());
                categoryMapper.update(child, wrapper);
            }
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(updateDO, categoryDTO);
        List<CategoryDTO> list = categoryBizService.getCategoryList();
        for (CategoryDTO temp : list) {
            if (updateDO.getId().equals(temp.getId())) {
                BeanUtils.copyProperties(temp, categoryDTO);
                break;
            }
        }
        this.clearCache();
        return categoryDTO;
    }

    @Override
    public Page<CategoryDTO> list(Long id, String title, Integer level, Long parentId, Integer page, Integer limit, Long adminId) throws ServiceException {
        QueryWrapper wrapper = new QueryWrapper();
        if (id != null) {
            wrapper.eq("id", id);
        }
        if (title != null) {
            wrapper.like("title", title);
        }
        if (level != null) {
            wrapper.eq("level", level);
        }
        if (parentId != null) {
            List<Long> categorySelfAndChildren = categoryBizService.getCategorySelfAndChildren(parentId);
            if (!CollectionUtils.isEmpty(categorySelfAndChildren)) {
                wrapper.in("id", categorySelfAndChildren);
            }
        }
        wrapper.orderByAsc("level");

        Page selectPage = categoryMapper.selectPage(Page.div(page, limit, CategoryDO.class), wrapper);
        List<CategoryDO> categoryDOS = selectPage.getItems();
        List<CategoryDTO> totalCategory = categoryBizService.getCategoryList();
        List<CategoryDTO> list = categoryDOS.stream().map(item -> {
            CategoryDTO dto = new CategoryDTO();
            for (CategoryDTO temp : totalCategory) {
                if (temp.getId().equals(item.getId())) {
                    BeanUtils.copyProperties(temp, dto);
                    return dto;
                }
            }
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
        return new Page<>(list, page, limit, selectPage.getCount());
    }

    /**
     * 抽取删除缓存公共代码
     */
    private void clearCache() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                cacheComponent.del(CacheConst.CATEGORY_ALL_LIST);
                cacheComponent.del(CacheConst.CATEGORY_SECOND_LEVEL_TREE);
                cacheComponent.del(CacheConst.CATEGORY_THREE_LEVEL_TREE);
            }
        });
    }

}
