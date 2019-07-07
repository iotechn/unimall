package com.iotechn.unimall.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.iotechn.unimall.data.domain.OrderSkuDO;
import com.iotechn.unimall.data.dto.order.OrderSkuDTO;

import java.util.List;

/**
 * Created by rize on 2019/7/6.
 */
public interface OrderSkuMapper extends BaseMapper<OrderSkuDO> {

    public List<OrderSkuDTO> getOrderSkuByOrderId(Long orderId);

}
