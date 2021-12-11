package com.iotechn.unimall.app.api.vip;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.annotation.param.Range;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.domain.VipOrderDO;

@HttpOpenApi(group = "vip.order", description = "VIP订单")
public interface VipOrderService {

    @HttpMethod(description = "预先支付")
    public Object prepay(
            @NotNull @HttpParam(name = "payPlatform", type = HttpParamType.COMMON, description = "支付平台") Integer payPlatform,
            @NotNull @HttpParam(name = "payChannel", type = HttpParamType.COMMON, description = "支付渠道") String payChannel,
            @NotNull @HttpParam(name = "templateId", type = HttpParamType.COMMON, description = "VIP模板ID") Long templateId,
            @NotNull @HttpParam(name = "ip", type = HttpParamType.IP, description = "ip地址") String ip,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

    @HttpMethod(description = "查询Vip订单")
    public Page<VipOrderDO> queryVipOrder(
            @HttpParam(name = "status", type = HttpParamType.COMMON, description = "状态") Integer status,
            @Range(min = 1) @HttpParam(name = "pageNo", type = HttpParamType.COMMON, description = "页码", valueDef = "1") Integer pageNo,
            @Range(min = 1) @HttpParam(name = "limit", type = HttpParamType.COMMON, description = "页码长度", valueDef = "10") Integer limit,
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户Id") Long userId) throws ServiceException;

}
