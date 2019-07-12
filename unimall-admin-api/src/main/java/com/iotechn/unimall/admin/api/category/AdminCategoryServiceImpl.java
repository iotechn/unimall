package com.iotechn.unimall.admin.api.category;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.component.CacheComponent;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.dto.CategoryTreeNodeDTO;
import com.iotechn.unimall.data.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
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
    private CacheComponent cacheComponent;

    private static final String CA_CATEGORY_TREE = "CA_CATEGORY_TREE";
    /**
     * @return
     * @throws ServiceException
     */
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
                    item.getChildren().add(categoryTreeNodeDTO);
                    categoryDOS.forEach(subCategoryDO -> {
                        if (subCategoryDO.getParentId().equals(categoryTreeNodeDTO.getValue())) {
                            CategoryTreeNodeDTO childCategoryNodeDTO = new CategoryTreeNodeDTO();
                            childCategoryNodeDTO.setLabel(subCategoryDO.getTitle());
                            childCategoryNodeDTO.setValue(subCategoryDO.getId());
                            categoryTreeNodeDTO.getChildren().add(childCategoryNodeDTO);
                        }
                    });
                }
            });
        });
        cacheComponent.putObj(CA_CATEGORY_TREE, list, 60 * 60);
        return list;
    }

}
