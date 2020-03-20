package com.iotechn.unimall.admin.api.category;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.core.exception.*;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.CategoryTreeNodeDTO;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import com.iotechn.unimall.data.mapper.SpuMapper;
import com.iotechn.unimall.data.model.Page;
import com.iotechn.unimall.plugin.core.inter.IPluginUpdateCategory;
import com.iotechn.unimall.plugin.core.manager.PluginsManager;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
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

    @Autowired
    private PluginsManager pluginsManager;

    private static final String CA_CATEGORY_TREE = "CA_CATEGORY_TREE";

    private static final String ADMIN_QUERY_CATEGORY_LIST = "ADMIN_QUERY_CATEGORY_LIST";

    private static final String CA_CATEGORY_SECOND_LEVEL_TREE = "CA_CATEGORY_SECOND_LEVEL_TREE";

    /**
     * @return
     * @throws ServiceException
     */


    /*获取两级目录树*/
    public List<CategoryTreeNodeDTO> categorySecondLevelTree() throws ServiceException {
        List<CategoryTreeNodeDTO> objList = cacheComponent.getObjList(CA_CATEGORY_SECOND_LEVEL_TREE, CategoryTreeNodeDTO.class);
        if (objList != null) {
            return objList;
        }
        List<CategoryTreeNodeDTO> list = categoryBizService.categorySecondLevelTree();
        cacheComponent.putObj(CA_CATEGORY_SECOND_LEVEL_TREE, list, 60 * 60);
        return list;
    }


    /*获取三级目录树。*/
    //TODO 做下优化
    @Override
    public List<CategoryTreeNodeDTO> categoryTree() throws ServiceException {
        List<CategoryTreeNodeDTO> objList = cacheComponent.getObjList(CA_CATEGORY_TREE, CategoryTreeNodeDTO.class);
        if (objList != null) {
            return objList;
        }
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new EntityWrapper<>());
        List<CategoryTreeNodeDTO> list = categoryDOS.stream().filter((item) -> (item.getParentId().equals(0l))).map(item -> {
            CategoryTreeNodeDTO dto = new CategoryTreeNodeDTO();
            dto.setLabel(item.getTitle());
            dto.setLevel(0);
            dto.setFullName(dto.getLabel());
            dto.setValue(item.getId());
            dto.setChildren(new LinkedList<>());
            return dto;
        }).collect(Collectors.toList());
        list.forEach(item -> {
            categoryDOS.forEach(categoryDO -> {
                if (categoryDO.getParentId().equals(item.getValue())) {
                    CategoryTreeNodeDTO categoryTreeNodeDTO = new CategoryTreeNodeDTO();
                    categoryTreeNodeDTO.setChildren(new LinkedList<>());
                    categoryTreeNodeDTO.setValue(categoryDO.getId());
                    categoryTreeNodeDTO.setLabel(categoryDO.getTitle());
                    categoryTreeNodeDTO.setLevel(1);
                    categoryTreeNodeDTO.setParent(item.getValue());
                    categoryTreeNodeDTO.setFullName(item.getFullName() + "/" + categoryDO.getTitle());
                    item.getChildren().add(categoryTreeNodeDTO);
                    categoryDOS.forEach(subCategoryDO -> {
                        if (subCategoryDO.getParentId().equals(categoryTreeNodeDTO.getValue())) {
                            CategoryTreeNodeDTO childCategoryNodeDTO = new CategoryTreeNodeDTO();
                            childCategoryNodeDTO.setLabel(subCategoryDO.getTitle());
                            childCategoryNodeDTO.setValue(subCategoryDO.getId());
                            childCategoryNodeDTO.setLevel(2);
                            childCategoryNodeDTO.setParent(categoryTreeNodeDTO.getValue());
                            childCategoryNodeDTO.setFullName(categoryTreeNodeDTO.getFullName() + "/" + subCategoryDO.getTitle());
                            categoryTreeNodeDTO.getChildren().add(childCategoryNodeDTO);
                        }
                    });
                }
            });
        });
        cacheComponent.putObj(CA_CATEGORY_TREE, list, 60 * 60);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryDO addCategory(Long adminId, String title, Long parentId, String iconUrl, String picUrl, Integer level) throws ServiceException {
        CategoryDO parent = null;
        CategoryDO categoryDO = new CategoryDO();
        if (!parentId.equals(0l)) {
            parent = categoryMapper.selectById(parentId);
            if (parent == null) {
                throw new AdminServiceException(ExceptionDefinition.PARENT_NODE_INFORMATION_ERROR);
            }
            categoryDO.setLevel(parent.getLevel() + 1);
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
        cacheComponent.del(CA_CATEGORY_TREE);
        cacheComponent.del(ADMIN_QUERY_CATEGORY_LIST);
        cacheComponent.del(CA_CATEGORY_SECOND_LEVEL_TREE);
        cacheComponent.del(CategoryBizService.CA_CATEGORY_ID_HASH);
        cacheComponent.del(CategoryBizService.CA_CATEGORY_LIST);
        pluginInvokeUpdateCategory(categoryDO.getId());
        return categoryDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCategory(Long adminId, Long id) throws ServiceException {
        Integer count_category = categoryMapper.selectCount(new EntityWrapper<CategoryDO>().eq("parent_id", id));
        Integer count_spu = spuMapper.selectCount(new EntityWrapper<SpuDO>().eq("category_id", id));

        if (count_category != 0 || count_spu != 0) {
            throw new AppServiceException(ExceptionDefinition.CATEGORY_OUGHT_TO_EMPTY);
        }
        cacheComponent.del(CA_CATEGORY_TREE);
        cacheComponent.del(ADMIN_QUERY_CATEGORY_LIST);
        cacheComponent.del(CA_CATEGORY_SECOND_LEVEL_TREE);
        cacheComponent.del(CategoryBizService.CA_CATEGORY_ID_HASH);
        cacheComponent.del(CategoryBizService.CA_CATEGORY_LIST);
        pluginInvokeUpdateCategory(id);
        return categoryMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CategoryTreeNodeDTO updateCategory(Long adminId, Long id, String title, Long parentId, String iconUrl, String picUrl, Integer level) throws ServiceException {
        if (id == null || parentId == null) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_OR_PARENT_NODE_IS_EMPTY);
        }
        CategoryDO categoryParent = categoryMapper.selectById(parentId);
        CategoryDO category = categoryMapper.selectById(id);

        if (category == null || (categoryParent == null && !parentId.equals(0L))) {
            throw new AdminServiceException(ExceptionDefinition.NOT_FIND_PARENT_NODE_OR_NODE);
        }


        CategoryDO categoryDO = new CategoryDO();
        if (parentId.equals(0L)) {
            categoryDO.setLevel(0);
        } else {
            // 父节点等级必须在修改节点等级之上
            if(category.getLevel() <= categoryParent.getLevel()){
                throw new AdminServiceException(ExceptionDefinition.PARENT_LEVEL_MUST_HIGH_THAN_CURRENT);
            }

            categoryDO.setLevel(categoryParent.getLevel() + 1);
        }
        categoryDO.setId(id);
        categoryDO.setGmtUpdate(new Date());
        categoryDO.setParentId(parentId);
        categoryDO.setTitle(title);
        categoryDO.setPicUrl(picUrl);
        categoryDO.setIconUrl(iconUrl);
        if (categoryMapper.updateById(categoryDO) <= 0) {
            throw new AdminServiceException(ExceptionDefinition.CATEGORY_UPDATE_FAILURE);
        }

        if(category.getLevel().intValue() == 2 && categoryDO.getLevel().intValue() != 2){
            int spuCount = spuMapper.selectCount(new EntityWrapper<SpuDO>().eq("category_id", id));
            if(spuCount > 0){
                throw new AdminServiceException(ExceptionDefinition.CATEGORY_EXIST_SPU);
            }
        }

        List<CategoryDO> two_level = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", id));
        if(!CollectionUtils.isEmpty(two_level)){
            for (CategoryDO two : two_level) {
                if(two.getLevel().intValue() == 2 && (categoryDO.getLevel().intValue() + 1) != 2) {
                    int spuCount = spuMapper.selectCount(new EntityWrapper<SpuDO>().eq("category_id", two.getId()));
                    if(spuCount > 0){
                        throw new AdminServiceException(ExceptionDefinition.CATEGORY_EXIST_SPU);
                    }
                }

                two.setLevel(categoryDO.getLevel() + 1);
                categoryMapper.updateById(two);
            }
        }

        CategoryTreeNodeDTO categoryTreeNodeDTO = new CategoryTreeNodeDTO();
        List<CategoryTreeNodeDTO> list = getCategoryList();
        for (CategoryTreeNodeDTO temp : list) {
            if (categoryDO.getId().equals(temp.getValue())) {
                BeanUtils.copyProperties(temp, categoryTreeNodeDTO);
                break;
            }
        }
        cacheComponent.del(CA_CATEGORY_TREE);
        cacheComponent.del(ADMIN_QUERY_CATEGORY_LIST);
        cacheComponent.del(CA_CATEGORY_SECOND_LEVEL_TREE);
        cacheComponent.del(CategoryBizService.CA_CATEGORY_ID_HASH);
        cacheComponent.del(CategoryBizService.CA_CATEGORY_LIST);
        pluginInvokeUpdateCategory(categoryDO.getId());
        return categoryTreeNodeDTO;
    }

    //首先的到所有的类目的List<CategoryTreeNodeDTO>,在根据SQL查询得到的数据转化成传往前端的数据
    @Override
    public Page<CategoryTreeNodeDTO> queryCategory(Long adminId, Long id, String title, Integer level, Long parentId, Integer pageNo, Integer limit) throws ServiceException {
        EntityWrapper wrapper = new EntityWrapper();
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
            wrapper.eq("parent_id", parentId);
        }
        wrapper.orderBy("level");
        Integer count = categoryMapper.selectCount(wrapper);

        List<CategoryDO> categoryDOS = categoryMapper.selectPage(new RowBounds((pageNo - 1) * limit, limit), wrapper);
        List<CategoryTreeNodeDTO> totalCategory = getCategoryList();
        List<CategoryTreeNodeDTO> list = categoryDOS.stream().map(item -> {
            CategoryTreeNodeDTO dto = new CategoryTreeNodeDTO();
            for (CategoryTreeNodeDTO temp : totalCategory) {
                if (temp.getValue().equals(item.getId())) {
                    BeanUtils.copyProperties(temp, dto);
                    return dto;
                }
            }
            BeanUtils.copyProperties(item, dto);
            ;
            return dto;
        }).collect(Collectors.toList());
        Page<CategoryTreeNodeDTO> page = new Page<>(list, pageNo, limit, count);
        return page;
    }


    //TODO 可以做出父节点查询所有子节点
    //获得所有类目按类目等级排序的类目list,
    private List<CategoryTreeNodeDTO> getCategoryList() {
        List<CategoryTreeNodeDTO> objList = cacheComponent.getObjList(ADMIN_QUERY_CATEGORY_LIST, CategoryTreeNodeDTO.class);
        if (objList != null) {
            return objList;
        }
        EntityWrapper wrapper = new EntityWrapper();
        wrapper.orderBy("level");
        List<CategoryDO> categoryDOS = categoryMapper.selectList(wrapper);
        List<CategoryTreeNodeDTO> list = categoryDOS.stream().map(item -> {
            CategoryTreeNodeDTO dto = new CategoryTreeNodeDTO();
            dto.setLabel(item.getTitle());
            dto.setLevel(item.getLevel());
            dto.setValue(item.getId());
            dto.setParent(item.getParentId());
            dto.setIconUrl(item.getIconUrl());
            dto.setPicUrl(item.getPicUrl());
            if (item.getLevel() == 0) {
                dto.setFullName(dto.getLabel());
            }
            return dto;
        }).collect(Collectors.toList());

        for (CategoryTreeNodeDTO cOne : list) {

            for (CategoryTreeNodeDTO cTwo : list) {
                if (cOne.getParent().equals(cTwo.getValue())) {
                    cOne.setFullName(cTwo.getFullName() + "/" + cOne.getLabel());
                    break;
                }
            }

        }
        cacheComponent.putObj(ADMIN_QUERY_CATEGORY_LIST, list, 60 * 60);
        return list;
    }

    private void pluginInvokeUpdateCategory(Long categoryId) {
        List<IPluginUpdateCategory> plugins = pluginsManager.getPlugins(IPluginUpdateCategory.class);
        if (!CollectionUtils.isEmpty(plugins)) {
            for (IPluginUpdateCategory updateGoods : plugins) {
                updateGoods.invokeCategoryUpdate(categoryId);
            }
        }
    }


}
