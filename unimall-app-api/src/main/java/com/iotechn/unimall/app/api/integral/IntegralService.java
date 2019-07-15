package com.iotechn.unimall.app.api.integral;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.exception.ServiceException;
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
