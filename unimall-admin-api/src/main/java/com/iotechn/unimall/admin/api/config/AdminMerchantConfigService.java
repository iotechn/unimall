package com.iotechn.unimall.admin.api.config;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.annotation.param.Range;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.CategoryDO;
import com.iotechn.unimall.data.domain.ConfigDO;
import com.iotechn.unimall.data.dto.CategoryTreeNodeDTO;
import com.iotechn.unimall.data.dto.ConfigDTO;
import com.iotechn.unimall.data.model.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-20
 * Time: 上午10:18
 */

@HttpOpenApi(group = "admin.merchant", description = "商铺信息配置")
public interface AdminMerchantConfigService {

    @HttpMethod(description = "创建", permission = "promote:merchant:create", permissionParentName = "推广管理", permissionName = "商铺信息管理")
    public boolean addMerchant(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "商铺标题") String title,
            @HttpParam(name = "logoUrl", type = HttpParamType.COMMON, description = "商铺logo") String logoUrl,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "商铺描述") String description,
            @HttpParam(name = "address", type = HttpParamType.COMMON, description = "商铺地址") String address,
            @NotNull @HttpParam(name = "showType", type = HttpParamType.COMMON, description = "展示方式") Integer showType
    ) throws ServiceException;

    @HttpMethod(description = "修改", permission = "promote:merchant:update", permissionParentName = "推广管理", permissionName = "商铺信息管理")
    public boolean updateMerchant(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId,
            @NotNull @HttpParam(name = "title", type = HttpParamType.COMMON, description = "商铺标题") String title,
            @HttpParam(name = "logoUrl", type = HttpParamType.COMMON, description = "商铺logo") String logoUrl,
            @HttpParam(name = "description", type = HttpParamType.COMMON, description = "商铺描述") String description,
            @HttpParam(name = "address", type = HttpParamType.COMMON, description = "商铺地址") String address,
            @NotNull @HttpParam(name = "showType", type = HttpParamType.COMMON, description = "展示方式") Integer showType
    ) throws ServiceException;

    @HttpMethod(description = "查询", permission = "promote:merchant:query", permissionParentName = "推广管理", permissionName = "商铺信息管理")
    public ConfigDTO getMerchant(
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;


}
