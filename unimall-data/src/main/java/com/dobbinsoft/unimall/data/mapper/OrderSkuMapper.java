package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.OrderSkuDO;
import com.dobbinsoft.unimall.data.dto.order.OrderStatisticsDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
public interface OrderSkuMapper extends IMapper<OrderSkuDO> {

    public List<OrderStatisticsDTO> statistics(@Param("orderIds") List<Long> orderIds);

}
