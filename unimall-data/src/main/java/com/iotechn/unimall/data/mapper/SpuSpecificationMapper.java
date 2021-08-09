package com.iotechn.unimall.data.mapper;

import com.iotechn.unimall.data.domain.SpuSpecificationDO;

import java.util.List;

/**
 * Description:
 * User: rize
 * Date: 2020/8/1
 * Time: 17:35
 */
public interface SpuSpecificationMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<SpuSpecificationDO> {

    public Integer batchInsert(List<SpuSpecificationDO> list);

}
