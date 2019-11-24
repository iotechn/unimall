package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.GroupShopDO;
import com.iotechn.unimall.data.dto.goods.GroupShopDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupShopMapper extends BaseMapper<GroupShopDO> {

    public List<GroupShopDTO> getGroupShopPage(@Param("offset") Integer offset,@Param("limit") Integer limit);

    public GroupShopDTO detail(Long groupShopId);

}
