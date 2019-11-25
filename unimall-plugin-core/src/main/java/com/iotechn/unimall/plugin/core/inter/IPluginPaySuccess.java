package com.iotechn.unimall.plugin.core.inter;

import com.iotechn.unimall.data.dto.order.OrderDTO;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/10/24
 * Time: 17:25
 */
public interface IPluginPaySuccess {

    public OrderDTO invoke(OrderDTO orderDTO, String prepayId);

}
