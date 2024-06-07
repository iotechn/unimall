package com.dobbinsoft.unimall.data.mapper;

import com.dobbinsoft.fw.support.mapper.IMapper;
import com.dobbinsoft.unimall.data.domain.OrderDO;
import com.dobbinsoft.unimall.data.dto.order.OrderDTO;
import com.dobbinsoft.unimall.data.model.KVModel;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
public interface OrderMapper extends IMapper<OrderDO> {

    public List<OrderDTO> selectOrderPage(@Param("status") Integer status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);

    public Long countOrder(@Param("status") Integer status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);

    /**
     * 获取订单地区统计
     * @return
     */
    public List<KVModel<String, Long>> selectAreaStatistics();

    public List<KVModel<String, Long>> selectChannelStatistics();

    public List<KVModel<String, Long>> selectOrderCountStatistics(String gmtStart);

    public List<KVModel<String, Long>> selectOrderSumStatistics(String gmtStart);

    public List<OrderDO> selectExpireOrderNos(@Param("status") Integer status, @Param("time") LocalDateTime time);

}
