package com.iotechn.unimall.app.api.freight;

import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import com.iotechn.unimall.data.dto.order.OrderRequestSkuDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午7:48
 */
@Service
public class FreightTemplateBizService {

    public Integer getFreightMoney(OrderRequestDTO orderRequestDTO){
        List<OrderRequestSkuDTO> orderRequestSkuDTOList = orderRequestDTO.getSkuList();

        //根据模板Id,来存储这次订单中，该模板下的商品的总价格
        HashMap<Long,Integer> freightCategoty = new HashMap<Long,Integer>();

        //根据模板Id,来存储没有满包邮时，应该付的邮费
        HashMap<Long,Integer> freightMoney = new HashMap<Long,Integer>();

        for(OrderRequestSkuDTO orderRequestSkuDTO : orderRequestSkuDTOList ){

        }

    }
}
