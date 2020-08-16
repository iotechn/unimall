package com.iotechn.unimall.biz.service.category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.data.constant.CacheConst;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.annotation.AspectCommonCache;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.enums.CategoryLevelType;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by rize on 2019/7/12.
 */
@Service
public class CategoryBizService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CacheComponent cacheComponent;

    /**
     * 获取一棵两级类目树
     */
    @AspectCommonCache(value = CacheConst.CATEGORY_SECOND_LEVEL_TREE)
    public List<CategoryDTO> categorySecondLevelTree() throws ServiceException {
        List<CategoryDO> categoryDOS = categoryMapper.selectList(new QueryWrapper<CategoryDO>()
                .eq("level",0)
                .or()
                .eq("level",1)
                .orderByAsc("level"));

        List<CategoryDO> firstLevelList = categoryDOS.stream().filter(item -> item.getLevel().intValue() == 0).collect(Collectors.toList());
        List<CategoryDO> secondLevelList = categoryDOS.stream().filter(item -> item.getLevel().intValue() == 1).collect(Collectors.toList());

        // 以ID为键，组装后的DTO为值，提升组装速度
        HashMap<Long,CategoryDTO> speedUp = new HashMap<>();

        // 组装一级类目
        List<CategoryDTO> resultTree = firstLevelList.stream().map(item -> {
            CategoryDTO first = new CategoryDTO();
            BeanUtils.copyProperties(item, first);
            first.setFullName(item.getTitle());
            speedUp.put(first.getId(),first);
            return first;
        }).collect(Collectors.toList());

        // 组装二级类目
        secondLevelList.stream().forEach(item ->{
            publicCodeAssembly(speedUp,item);
        });

        return resultTree;
    }

    /**
     * 获取一棵三级类目树,类内部有调用，因此不能切面
     */
    public List<CategoryDTO> categoryThreeLevelTree() throws ServiceException {
        List<CategoryDTO> categoryDTOListFormCache = cacheComponent.getObjList(CacheConst.CATEGORY_THREE_LEVEL_TREE, CategoryDTO.class);
        if (categoryDTOListFormCache != null) {
            return categoryDTOListFormCache;
        }
        //从数据库查询
        List<CategoryDO> categoryDOList = categoryMapper.selectList(new QueryWrapper<>());

        List<CategoryDO> firstLevelList = categoryDOList.stream().filter(item -> item.getLevel().intValue() == 0).collect(Collectors.toList());
        List<CategoryDO> secondLevelList = categoryDOList.stream().filter(item -> item.getLevel().intValue() == 1).collect(Collectors.toList());
        List<CategoryDO> thirdLevelList = categoryDOList.stream().filter(item -> item.getLevel().intValue() == 2).collect(Collectors.toList());

        // 以ID为键，组装后的DTO为值，提升组装速度
        HashMap<Long,CategoryDTO> speedUp = new HashMap<>();

        // 组装一级类目
        List<CategoryDTO> resultTree = firstLevelList.stream().map(item -> {
            CategoryDTO first = new CategoryDTO();
            BeanUtils.copyProperties(item, first);
            first.setFullName(item.getTitle());
            speedUp.put(first.getId(),first);
            return first;
        }).collect(Collectors.toList());

        // 组装二级类目
        secondLevelList.stream().forEach(item ->{
           publicCodeAssembly(speedUp,item);
        });

        // 组装三级类目
        thirdLevelList.stream().forEach(item ->{
            publicCodeAssembly(speedUp,item);
        });
        //放入缓存
        cacheComponent.putObj(CacheConst.CATEGORY_THREE_LEVEL_TREE, resultTree, Const.CACHE_ONE_DAY);
        return resultTree;
    }

    /**
     * 上面生成类目树的公用代码提取
     */
    private void publicCodeAssembly(HashMap<Long,CategoryDTO> speedUp, CategoryDO item){
        CategoryDTO parentDTO = speedUp.get(item.getParentId());
        if(parentDTO != null){
            if(parentDTO.getChildrenList() == null){
                parentDTO.setChildrenList(new ArrayList<>());
            }
            CategoryDTO child = new CategoryDTO();
            BeanUtils.copyProperties(item,child);
            child.setFullName(parentDTO.getFullName() + "/" + item.getTitle());
            parentDTO.getChildrenList().add(child);
            speedUp.put(item.getId(),child);
        }
    }

    /**
     * 获得所有类目list,类中有调用，不能使用切面
     *
     */
    public List<CategoryDTO> getCategoryList() throws ServiceException{
        List<CategoryDTO> categoryDTOListFormCache = cacheComponent.getObjList(CacheConst.CATEGORY_ALL_LIST, CategoryDTO.class);
        if (categoryDTOListFormCache != null) {
            return categoryDTOListFormCache;
        }

        List<CategoryDTO> categoryDTOS = categoryThreeLevelTree();
        List<CategoryDTO> resultList = new LinkedList<>();
        categoryDTOS.forEach(first -> {
            resultList.add(first);
            if (!CollectionUtils.isEmpty(first.getChildrenList()))
                first.getChildrenList().forEach(second -> {
                    resultList.add(second);
                    if (!CollectionUtils.isEmpty(second.getChildrenList()))
                        second.getChildrenList().forEach(third -> {
                            resultList.add(third);
                        });
                });
        });

        cacheComponent.putObj(CacheConst.CATEGORY_ALL_LIST, resultList, Const.CACHE_ONE_DAY);
        return resultList;
    }

    /**
     * 将 节点传入 获取其 父节点ID 和 父父节点ID 组成List
     *
     * @param categoryId 节点ID
     * @return
     * @throws ServiceException
     */
    public List<Long> getCategoryFamily(Long categoryId) {
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (categoryDO.getLevel() == CategoryLevelType.THREE.getCode()) {
            return Arrays.asList(categoryDO.getFirstLevelId(), categoryDO.getSecondLevelId(), categoryDO.getId());
        } else if (categoryDO.getLevel() == CategoryLevelType.TWO.getCode()) {
            return Arrays.asList(categoryDO.getFirstLevelId(), categoryDO.getId());
        } else {
            return Arrays.asList(categoryDO.getId());
        }
    }

    /**
     * 将一父节点传入，返回父节点本身与其子节点，及其孙节点ID列表
     *
     * @param categoryId
     * @return
     * @throws ServiceException
     */
    public List<Long> getCategorySelfAndChildren(Long categoryId) throws ServiceException {
        // 利用冗余数据实现
        List<CategoryDTO> categoryList = getCategoryList();
        CategoryDTO categoryDTO = new CategoryDTO();
        for (int i = 0; i < categoryList.size(); i++) {
            if(categoryList.get(i).getId().equals(categoryId)){
                categoryDTO = categoryList.get(i);
                break;
            }
        }

        LinkedList<CategoryDTO> queue = new LinkedList<CategoryDTO>();
        queue.add(categoryDTO);
        List<Long> ids = new LinkedList<>();
        while(!queue.isEmpty()){
            CategoryDTO pop = queue.pop();
            if(pop.getChildrenList() != null){
                queue.addAll(pop.getChildrenList());
            }
            ids.add(pop.getId());
        }
        return ids;
    }
}
