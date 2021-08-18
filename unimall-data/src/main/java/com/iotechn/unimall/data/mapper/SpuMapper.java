package com.iotechn.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.fw.support.model.Page;
import com.iotechn.unimall.data.domain.SpuDO;
import com.iotechn.unimall.data.dto.product.SpuDTO;
import com.iotechn.unimall.data.dto.product.SpuItemDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/2.
 */
public interface SpuMapper extends IMapper<SpuDO> {

    /**
     * 从DB搜索 SPU
     * @param page
     * @param locationId
     * @param categoryId
     * @param title
     * @param orderBy
     * @param isAsc
     * @return
     */
    public Page<SpuItemDTO> searchSpu(Page page, @Param("locationId") Long locationId, @Param("categoryId") Long categoryId,
                                      @Param("title") String title, @Param("orderBy") String orderBy, @Param("isAsc") Boolean isAsc);

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

    /**
     * 通过ID获取商品
     * @param spuId
     * @return
     */
    public default SpuDTO getSpuById(Long spuId) {
        return null;
    }

}
