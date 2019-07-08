package com.iotechn.unimall.app.api.freight;

import com.iotechn.unimall.app.exception.AppExceptionDefinition;
import com.iotechn.unimall.app.exception.AppServiceException;
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
public class FreightTemplateSeviceImpl implements FreightTemplateService{

    @Autowired
    private  FreightTemplateBizService freightTemplateBizService;

    @Override
    public Integer getFreightMoney(Long userId, OrderRequestDTO orderRequestDTO) throws ServiceException {
        return freightTemplateBizService.getFreightMoney(orderRequestDTO);
    }
}
