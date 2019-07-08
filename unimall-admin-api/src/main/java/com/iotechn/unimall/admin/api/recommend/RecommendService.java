package com.iotechn.unimall.admin.api.recommend;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;

import java.rmi.ServerException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午3:42
 */

@HttpOpenApi(group = "recommend",description = "推荐商品")
public interface RecommendService {

    @HttpMethod(description = "增加推荐")
    public Boolean addRecomend(@NotNull @HttpParam(name="adminId",type = HttpParamType.ADMIN_ID,description = "管理员ID")Long adminId,
                               @NotNull @HttpParam(name="adminId",type = HttpParamType,description = "管理员ID")Long adminId,) throws ServerException;
}
