package com.iotechn.unimall.admin.api.address;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AddressDO;
import com.iotechn.unimall.data.model.Page;

/**
 * Description:
 * User: rize
 * Date: 2020/8/12
 * Time: 11:31
 */
@HttpOpenApi(group = "admin.address", description = "管理员地址管理")
public interface AdminAddressService {

    @HttpMethod(description = "列表", permission = "user:address:list", permissionParentName = "用户管理", permissionName = "地址管理")
    public Page<AddressDO> list(
            @HttpParam(name = "userId", type = HttpParamType.COMMON, description = "用户Id") Long userId,
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer page,
            @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页长度", valueDef = "20") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
