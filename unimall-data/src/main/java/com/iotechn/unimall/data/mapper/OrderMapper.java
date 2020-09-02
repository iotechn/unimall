package com.iotechn.unimall.data.mapper;

import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import com.iotechn.unimall.data.model.KVModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    public List<OrderDO> selectExpireOrderNos(@Param("status") Integer status, @Param("time") Date time);

}
