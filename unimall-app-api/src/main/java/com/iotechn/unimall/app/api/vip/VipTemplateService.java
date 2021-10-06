package com.iotechn.unimall.app.api.vip;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.VipTemplateDO;

import java.util.List;

@HttpOpenApi(group = "vip.template", description = "VIP模板")
public interface VipTemplateService {

    @HttpMethod(description = "查询")
    public List<VipTemplateDO> list() throws ServiceException;
}
