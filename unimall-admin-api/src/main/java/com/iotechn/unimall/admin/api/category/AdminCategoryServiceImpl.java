package com.iotechn.unimall.admin.api.category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.core.exception.AdminServiceException;
import com.iotechn.unimall.core.exception.AppServiceException;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.Page;
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
    private SpuMapper spuMapper;

    @Autowired
    private CategoryBizService categoryBizService;

    @Autowired
    private CacheComponent cacheComponent;

    /**
     * 获取两级类目树,用于添加类目时选择使用
     */
    public List<CategoryDTO> categorySecondLevelTree() throws ServiceException {
        List<CategoryDTO> list = categoryBizService.categorySecondLevelTree();
        return list;
    }

    /**
     * 获取三级类目树,用于商品、优惠券之类添加的使用
     */
    @Override
    public List<CategoryDTO> categoryTree() throws ServiceException {
        List<CategoryDTO> list = categoryBizService.categoryThreeLevelTree();
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDO create(Long adminId, String title, Long parentId, String iconUrl, String picUrl) throws ServiceException {
        CategoryDO parent = null;
        CategoryDO categoryDO = new CategoryDO();
        if (!parentId.equals(0l)) {
            parent = categoryMapper.selectById(parentId);
            if (parent == null || parent.getLevel().intValue() >= 2) {
                throw new AdminServiceException(ExceptionDefinition.PARENT_NODE_INFORMATION_ERROR);
            }
            categoryDO.setLevel(parent.getLevel() + 1);

            if(parent.getLevel().intValue() == 0){
                categoryDO.setFirstLevelId(parentId);
            }else if(parent.getLevel().intValue() == 1){
                categoryDO.setFirstLevelId(parent.getFirstLevelId());
                categoryDO.setSecondLevelId(parentId);
            }
        } else {
            categoryDO.setLevel(0);
        }
        categoryDO.setParentId(parentId);
        categoryDO.setIconUrl(iconUrl);
        categoryDO.setPicUrl(picUrl);
        categoryDO.setTitle(title);
        Date now = new Date();
        categoryDO.setGmtCreate(now);
        categoryDO.setGmtUpdate(now);

        if (categoryMapper.insert(categoryDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.DATABASE_INSERT_FAILURE);
        }
        publicCodeDeleteCache();
        return categoryDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long adminId, Long id) throws ServiceException {
        Integer count_category = categoryMapper.selectCount(new QueryWrapper<CategoryDO>().eq("parent_id", id));
        Integer count_spu = spuMapper.selectCount(new QueryWrapper<SpuDO>().eq("category_id", id));

        if (count_category != 0 || count_spu != 0) {
            throw new AppServiceException(ExceptionDefinition.CATEGORY_OUGHT_TO_EMPTY);
        }

        publicCodeDeleteCache();
        return categoryMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDTO update(Long adminId, Long id, String title, Long parentId, String iconUrl, String picUrl) throws ServiceException {
        if (id == null || parentId == null) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_OR_PARENT_NODE_IS_EMPTY);
        }
        CategoryDO categoryParent = categoryMapper.selectById(parentId);
        CategoryDO category = categoryMapper.selectById(id);

        // 1. 检验类目是否存在,非一级类目的父类目是否存在
        if (category == null || (categoryParent == null && !parentId.equals(0L))) {
            throw new AdminServiceException(ExceptionDefinition.NOT_FIND_PARENT_NODE_OR_NODE);
        }

        CategoryDO updateDO = new CategoryDO();

        // 如果是一级类目修改，就不会变更父类目；如果没有变更父类目，也不会变更对应的一二级长辈类目
        if (!category.getParentId().equals(0L) && !category.getParentId().equals(parentId)) {
            // 节点只能平级变更
            if(category.getLevel().intValue() != categoryParent.getLevel().intValue() + 1){
                throw new AdminServiceException(ExceptionDefinition.UPDATE_ONLY_HORIZONTAL_MOVE);
            }
            if(categoryParent.getLevel().intValue() == 0){
                updateDO.setFirstLevelId(parentId);
            }else if(categoryParent.getLevel().intValue() == 1){
                updateDO.setFirstLevelId(categoryParent.getFirstLevelId());
                updateDO.setSecondLevelId(parentId);
            }
        }
        updateDO.setId(id);
        updateDO.setGmtUpdate(new Date());
        updateDO.setParentId(parentId);
        updateDO.setTitle(title);
        updateDO.setPicUrl(picUrl);
        updateDO.setIconUrl(iconUrl);
        if (categoryMapper.updateById(updateDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_UPDATE_FAILURE);
        }

        // 如果变更了父节点，就需要变更当前节点的子孙节点的一二级长辈节点
        if(!category.getParentId().equals(parentId)){
            // 只有二三级节点能变更父节点，其中只有二级节点变更后，会造成子节点的长辈节点变更。
            if(updateDO.getLevel().intValue() == 1){
                QueryWrapper<CategoryDO> wrapper = new QueryWrapper<>();
                wrapper.eq("parent_id",updateDO.getId());
                CategoryDO child = new CategoryDO();
                child.setFirstLevelId(updateDO.getFirstLevelId());
                categoryMapper.update(child,wrapper);
            }
        }

        CategoryDTO categoryDTO = new CategoryDTO();
        List<CategoryDTO> list = categoryBizService.getCategoryList();
        for (CategoryDTO temp : list) {
            if (updateDO.getId().equals(temp.getId())) {
                BeanUtils.copyProperties(temp, categoryDTO);
                break;
            }
        }
        publicCodeDeleteCache();
        return categoryDTO;
    }

    //首先的到所有的类目的List<CategoryDTO>,在根据SQL查询得到的数据转化成传往前端的数据
    @Override
    public Page<CategoryDTO> list(Long adminId, Long id, String title, Integer level, Long parentId, Integer page, Integer limit) throws ServiceException {
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
            if(!CollectionUtils.isEmpty(categorySelfAndChildren)){
                wrapper.in("id",categorySelfAndChildren);
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
    private void publicCodeDeleteCache(){
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
