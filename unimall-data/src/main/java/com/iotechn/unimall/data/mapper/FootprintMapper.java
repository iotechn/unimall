package com.iotechn.unimall.data.mapper;

import com.iotechn.unimall.data.domain.FootprintDO;
import com.iotechn.unimall.data.dto.FootprintDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-08
 * Time: 上午8:59
 */
public interface FootprintMapper extends IMapper<FootprintDO> {

    public List<FootprintDTO> getAllFootprint(@Param("userId")Long userId, @Param("offset")Integer offset, @Param("size")Integer size);

}
