package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.SpuDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
public interface SpuMapper extends IMapper<SpuDO> {

    /**
     * 仅可传入叶子类目
     * @param categoryId
     * @return
     */
    public List<SpuDO> getSpuTitleByCategoryId(Long categoryId);

    /**
     * 增加Spu累计销量
     * @param spuId
     * @param delta
     * @return
     */
    public Integer incSales(@Param("spuId") Long spuId, @Param("delta") Integer delta);

    public List<SpuDO> getSpuTitleAll();

}
