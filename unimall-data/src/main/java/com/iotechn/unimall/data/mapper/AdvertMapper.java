package com.iotechn.unimall.data.mapper;

import com.iotechn.unimall.data.domain.AdvertDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:38
 */
public interface AdvertMapper extends IMapper<AdvertDO> {

    public List<AdvertDO> getAdvertByTypeAndStatus(@Param("adType") Integer adType, @Param("status")Integer Status, @Param("offset")Integer offset, @Param("size")Integer size);

    public List<AdvertDO> getAllAdvert(@Param("offset")Integer offset, @Param("size")Integer size);
}
