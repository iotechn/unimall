package com.iotechn.unimall.app.api.category;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.CategoryDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
@HttpOpenApi(group = "category", description = "类目服务")
public interface CategoryService {

    @HttpMethod(description = "获取类目列表")
    public List<CategoryDTO> categoryList() throws ServiceException;

    @HttpMethod(description = "获取分类父节点")
    public List<Long> getCategoryFamily(
            @NotNull @HttpParam(name = "categoryId", type = HttpParamType.COMMON, description = "类目Id") Long categoryId) throws ServiceException;

}
