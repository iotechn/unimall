package com.iotechn.unimall.admin.api.vip;


import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.domain.VipOrderDO;

@HttpOpenApi(group = "admin.vip.order", description = "VIP订单")
public interface AdminVipOrderService {

    @HttpMethod(description = "退款", permission = "vip:order:refund", permissionParentName = "VIP管理", permissionName = "VIP卡管理")
    public String refund(
            @NotNull @HttpParam(name = "id", type = HttpParamType.COMMON, description = "ID") Long id,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

    @HttpMethod(description = "查询", permission = "vip:order:list", permissionParentName = "VIP管理", permissionName = "VIP卡管理")
    public Page<VipOrderDO> list(
            @HttpParam(name = "orderNo", type = HttpParamType.COMMON, description = "订单号或者叫会员卡号") String orderNo,
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "状态") Integer status,
            @HttpParam(name = "phone", type = HttpParamType.COMMON, description = "手机号") String phone,
            @HttpParam(name = "templateId", type = HttpParamType.COMMON, description = "模板ID") Long templateId,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit,
            @NotNull @HttpParam(name = "adminId", type = HttpParamType.ADMIN_ID, description = "管理员ID") Long adminId) throws ServiceException;

}
