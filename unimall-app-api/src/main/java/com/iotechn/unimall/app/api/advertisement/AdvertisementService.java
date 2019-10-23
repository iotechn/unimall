package com.iotechn.unimall.app.api.advertisement;

import com.iotechn.unimall.core.annotation.HttpMethod;
import com.iotechn.unimall.core.annotation.HttpOpenApi;
import com.iotechn.unimall.core.annotation.HttpParam;
import com.iotechn.unimall.core.annotation.HttpParamType;
import com.iotechn.unimall.core.annotation.param.NotNull;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.domain.AdvertisementDO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:22
 */

@HttpOpenApi(group = "advertisement",description = "广告推销")
public interface AdvertisementService {

    @HttpMethod(description = "取得活跃广告")
    public List<AdvertisementDO> getActiveAd(
            @HttpParam(name = "adType",type = HttpParamType.COMMON,description = "广告类型")Integer adType)throws ServiceException;

}
