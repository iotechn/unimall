package com.iotechn.unimall.data.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.GroupShopSkuDO;
import com.iotechn.unimall.data.dto.goods.GroupShopSkuDTO;

import java.util.List;

/*
@PackageName:com.iotechn.unimall.data.mapper
@ClassName: GroupShopSkuMapper
@Description:
@author kbq
@date 19-11-13下午4:28
*/
public interface GroupShopSkuMapper extends BaseMapper<GroupShopSkuDO>{

    public List<GroupShopSkuDTO> getSkuList(Long groupShopId);

}
