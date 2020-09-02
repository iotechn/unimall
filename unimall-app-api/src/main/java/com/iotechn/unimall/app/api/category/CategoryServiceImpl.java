package com.iotechn.unimall.app.api.category;

import com.iotechn.unimall.biz.service.category.CategoryBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryBizService categoryBizService;

    @Override
    public List<CategoryDTO> categoryList() throws ServiceException {
        return categoryBizService.categoryThreeLevelTree();
    }

    /**
     * @param categoryId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<Long> getCategoryFamily(Long categoryId) throws ServiceException {
        return categoryBizService.getCategoryFamily(categoryId);
    }
}
