package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.OrderDO;
import com.iotechn.unimall.data.dto.order.OrderDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
public interface OrderMapper extends BaseMapper<OrderDO> {

    public List<OrderDTO> selectOrderPage(@Param("status") Integer status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);

    public Long countOrder(@Param("status") Integer status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);
}
