package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.SkuDO;
import com.iotechn.unimall.data.dto.SkuDTO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by rize on 2019/7/2.
 */
public interface SkuMapper extends BaseMapper<SkuDO> {

    public SkuDTO getSkuDTOById(Long skuId);

    public Integer decSkuStock(@Param("skuId") Long skuId,@Param("stock") Integer stock);
}
