package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.AdvertisementDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 下午8:38
 */
public interface AdvertisementMapper extends BaseMapper<AdvertisementDO> {

    public List<AdvertisementDO> getAdvertisementByTypeAndStatus(@Param("adType") Integer adType, @Param("status")Integer Status,@Param("offset")Integer offset,@Param("size")Integer size);

    public List<AdvertisementDO> getAllAdvertisement(@Param("offset")Integer offset,@Param("size")Integer size);
}
