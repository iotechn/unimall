package com.iotechn.unimall.app.api.groupshop;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.dto.product.GroupShopDTO;

import java.rmi.ServerException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/24
 * Time: 15:15
 */
@HttpOpenApi(group = "groupshop", description = "团购服务")
public interface GroupShopService {

    @HttpMethod(description = "获取团购列表")
    public Page<GroupShopDTO> getGroupShopPage(
            @HttpParam(name = "page", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @HttpParam(name = "pageSize", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer pageSize) throws ServerException;


}
