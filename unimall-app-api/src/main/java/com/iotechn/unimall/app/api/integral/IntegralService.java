package com.iotechn.unimall.app.api.integral;

import com.dobbinsoft.fw.core.annotation.HttpMethod;
import com.dobbinsoft.fw.core.annotation.HttpOpenApi;
import com.dobbinsoft.fw.core.annotation.HttpParam;
import com.dobbinsoft.fw.core.annotation.HttpParamType;
import com.dobbinsoft.fw.core.annotation.param.NotNull;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.IntegralIndexDataDTO;

/**
 * 将多个接口聚合到一起，减少HTTP访问次数
 * Created by rize on 2019/7/14.
 */
@HttpOpenApi(group = "integral", description = "聚合接口")
public interface IntegralService {

    @HttpMethod(description = "获取首页聚合数据")
    public IntegralIndexDataDTO getIndexData() throws ServiceException;

}
