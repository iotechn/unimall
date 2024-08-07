package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.SkuDO;
import com.dobbinsoft.unimall.data.dto.product.SkuDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
public interface SkuMapper extends IMapper<SkuDO> {

    public SkuDTO getSkuDTOById(Long skuId);

    public Integer decSkuStock(@Param("skuId") Long skuId, @Param("stock") Integer stock);

    public Integer returnSkuStock(@Param("skuId") Long skuId, @Param("stock") Integer stock);

    /**
     * 删除SPUID
     * @param spuId
     * @return
     */
    public List<Long> getSkuIds(@Param("spuId") Long spuId);

    List<Long> selectSkuIdsBySpuIds(@Param("ids") List<Long> ids);

    public Integer getStockForUpdate(Long skuId);

    public List<SkuDTO> getSkuDTOListByIds(@Param("skuIds") List<Long> skuIds);

}
