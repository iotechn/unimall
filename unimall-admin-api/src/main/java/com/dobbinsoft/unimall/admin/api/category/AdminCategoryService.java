package com.dobbinsoft.unimall.admin.api.category;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.dobbinsoft.unimall.data.domain.CategoryDO;
import com.dobbinsoft.unimall.data.dto.CategoryDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/12.
 */
@HttpOpenApi(group = "admin.category", description = "类目管理服务")
public interface AdminCategoryService {

    @HttpMethod(description = "获取二级类目树")
    public List<CategoryDTO> categoryTree() throws ServiceException;

    @HttpMethod(description = "创建", permission = "product:category:create", permissionParentName = "商品管理", permissionName = "类目管理")
    public CategoryDO create(
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "类目标题") String title,
            @HttpParam(name = "parentId", type = HttpParamType.COMMON, description = "类目父节点", valueDef = "0") Long parentId,
            @HttpParam(name = "picUrl", type = HttpParamType.COMMON, description = "类目图片") String picUrl,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "删除", permission = "product:category:delete", permissionParentName = "商品管理", permissionName = "类目管理")
    public String delete(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "类目ID") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "修改", permission = "product:category:edit", permissionParentName = "商品管理", permissionName = "类目管理")
    public CategoryDTO update(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "类目ID") Long id,
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "类目标题") String title,
            @NotNull @HttpParam(name = "parentId", type = HttpParamType.COMMON, description = "类目父节点") Long parentId,
            @HttpParam(name = "iconUrl", type = HttpParamType.COMMON, description = "类目图标") String iconUrl,
            @HttpParam(name = "picUrl", type = HttpParamType.COMMON, description = "类目图片") String picUrl,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    /**
     * 首先的到所有的类目的List<CategoryDTO>,在根据SQL查询得到的数据转化成传往前端的数据
     * @param id
     * @param title
     * @param level
     * @param parentId
     * @param page
     * @param limit
     * @param adminId
     * @return
     * @throws ServiceException
     */
    @HttpMethod(description = "查询", permission = "product:category:list", permissionParentName = "商品管理", permissionName = "类目管理")
    public Page<CategoryDTO> list(
            @HttpParam(name = "id", type = HttpParamType.COMMON, description = "类目ID") Long id,
            @HttpParam(name = "title", type = HttpParamType.COMMON, description = "类目标题") String title,
            @HttpParam(name = "level", type = HttpParamType.COMMON, description = "类目等级") Integer level,
            @HttpParam(name = "parentId", type = HttpParamType.COMMON, description = "父类目id") Long parentId,
            @Range(min = 1) @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;
}
