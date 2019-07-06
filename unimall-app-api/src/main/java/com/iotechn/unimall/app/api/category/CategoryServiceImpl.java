package com.iotechn.unimall.app.api.category;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.Const;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CacheComponent cacheComponent;

    private static final String CA_CATEGORY_LIST = "CA_CATEGORY_LIST";

    @Override
    public List<CategoryDTO> categoryList() throws ServiceException {
        List<CategoryDTO> categoryDTOListFormCache = cacheComponent.getObjList(CA_CATEGORY_LIST, CategoryDTO.class);
        if (categoryDTOListFormCache != null) {
            return categoryDTOListFormCache;
        }
        //从数据库查询
        List<CategoryDO> categoryDOList = categoryMapper.selectList(new EntityWrapper<>());
        //组装DTO
        List<CategoryDTO> categoryDTOList = new LinkedList<>();
        categoryDOList.forEach(categoryDO -> {
            if (categoryDO.getParentId() == 0) {
                CategoryDTO categoryDTO = new CategoryDTO();
                BeanUtils.copyProperties(categoryDO, categoryDTO);
                categoryDTOList.add(categoryDTO);
            }
        });

        //遍历二、三级
        categoryDTOList.forEach(categoryDTO -> {
            categoryDOList.forEach(categoryDO -> {
                if (categoryDO.getParentId().equals(categoryDTO.getId())) {
                    List<CategoryDTO> childrenList = categoryDTO.getChildrenList();
                    if (childrenList == null) {
                        childrenList = new LinkedList<>();
                        categoryDTO.setChildrenList(childrenList);
                    }
                    CategoryDTO childCategoryDTO = new CategoryDTO();
                    BeanUtils.copyProperties(categoryDO, childCategoryDTO);
                    childCategoryDTO.setChildrenList(new LinkedList<>());
                    childrenList.add(childCategoryDTO);
                    categoryDOList.forEach(leaf -> {
                        if (childCategoryDTO.getId().equals(leaf.getParentId())) {
                            CategoryDTO leafCategoryDTO = new CategoryDTO();
                            BeanUtils.copyProperties(leaf, leafCategoryDTO);
                            childCategoryDTO.getChildrenList().add(leafCategoryDTO);
                        }
                    });
                }
            });
        });



        //放入缓存
        cacheComponent.putObj(CA_CATEGORY_LIST, categoryDTOList, Const.CACHE_ONE_DAY);
        return categoryDTOList;
    }
}
