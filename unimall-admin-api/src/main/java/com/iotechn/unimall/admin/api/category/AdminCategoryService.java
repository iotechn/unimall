package com.iotechn.unimall.admin.api.category;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.CategoryDTO;
import com.iotechn.unimall.data.dto.CategoryTreeNodeDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/12.
 */
@HttpOpenApi(group = "admin.category", description = "类目管理服务")
public interface AdminCategoryService {

    @HttpMethod(description = "获取类目树")
    public List<CategoryTreeNodeDTO> categoryTree() throws ServiceException;

}
