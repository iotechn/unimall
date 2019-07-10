package com.iotechn.unimall.app.api.freight;

import com.iotechn.unimall.biz.service.freight.FreightBizService;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.dto.order.OrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午7:50
 */
@Service
public class FreightTemplateServiceImpl implements FreightTemplateService {

    @Autowired
    private FreightBizService freightBizService;

    @Override
    public Integer getFreightMoney(Long userId, OrderRequestDTO orderRequestDTO) throws ServiceException {
        return freightBizService.getFreightMoney(orderRequestDTO);
    }
}
