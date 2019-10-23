package com.iotechn.unimall.app.api.footprint;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.FootprintDTO;
import com.iotechn.unimall.data.model.Page;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午8:33
 */

@HttpOpenApi(group = "footprint", description = "足迹")
public interface FootprintService {

    @HttpMethod(description = "删除足迹")
    public boolean deleteFootprint(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId,
            @NotNull @HttpParam(name = "footprintId", type = HttpParamType.COMMON, description = "足迹Id") Long footprintId) throws ServiceException;

    @HttpMethod(description = "分页查询所有足迹,通过时间顺序")
    public List<FootprintDTO> getAllFootprint(
            @NotNull @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户ID") Long userId) throws ServiceException;

}
