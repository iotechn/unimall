package com.iotechn.unimall.biz.service.category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iotechn.unimall.biz.constant.CacheConst;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.annotaion.AspectCommonCache;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
     * 将 叶子类目 的 Key 做为键 parentId_parent.parentId 作为值放入一个hash表中。查询familyId直接查一次hash表即可查出来。Hash表Size就等于叶子类目数量
     *
     * @param categoryId 仅可传入叶子节点
     * @return
     * @throws ServiceException
     */
    public List<Long> getCategoryFamily(Long categoryId) throws ServiceException {
        Map<String, String> hashAll = cacheComponent.getHashAll(CacheConst.CATEGORY_ID_HASH);
        if (hashAll == null) {
            //构建此Hash表
            final Map<String,String> newHash = new HashMap<>();
            //将所有子节点查询出来
            List<CategoryDTO> categoryDTOList = categoryThreeLevelTree();
            categoryDTOList.forEach(topItem -> {
                if (!CollectionUtils.isEmpty(topItem.getChildrenList()))
                    topItem.getChildrenList().forEach(subItem -> {
                        if (!CollectionUtils.isEmpty(subItem.getChildrenList()))
                            subItem.getChildrenList().forEach(leafItem -> {
                                newHash.put("S" + leafItem.getId(), subItem.getId() + "_" + topItem.getId());
                            });
                    });
            });
            hashAll = newHash;
            cacheComponent.putHashAll(CacheConst.CATEGORY_ID_HASH, hashAll, Const.CACHE_ONE_DAY);
        }

        LinkedList<Long> ids = new LinkedList<>();
        ids.add(categoryId);
        String str = hashAll.get("S" + categoryId);
        if (!StringUtils.isEmpty(str)) {
            String[] split = str.split("_");
            ids.add(new Long(split[0]));
            ids.add(new Long(split[1]));
        }
        return ids;
    }

    /**
     * 获得所有类目list
     */
    @AspectCommonCache(value = CacheConst.CATEGORY_ALL_LIST,second = 60 * 60 * 24)
    public List<CategoryDTO> getCategoryList() throws ServiceException{
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
        return resultList;
    }
}
