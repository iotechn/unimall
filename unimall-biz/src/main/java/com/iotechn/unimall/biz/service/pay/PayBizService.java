package com.iotechn.unimall.biz.service.pay;

import cn.hutool.core.util.ObjectUtil;
import com.dobbinsoft.fw.core.exception.AppServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.properties.FwAliAppProperties;
import com.dobbinsoft.fw.support.properties.FwWxAppProperties;
import com.dobbinsoft.fw.support.service.BaseService;
import com.iotechn.unimall.biz.util.PaySelector;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayBizService extends BaseService<UserDTO, AdminDTO> {

    @Autowired
    private MatrixPayService matrixPayService;

    @Autowired
    private PaySelector paySelector;

    public Object commonPrepay(
            String orderNo,
            int actualPrice,
            int platform,
            String payChannel,
            String ip) throws ServiceException {
        // 前端来决定支付方式
        MatrixPayUnifiedOrderRequest orderRequest = new MatrixPayUnifiedOrderRequest();
        paySelector.packPayChannel(orderRequest, platform, payChannel);
        // 区分回调 直接通过 S 来判断
        orderRequest.setOutTradeNo(orderNo);
        orderRequest.setBody("buy_" + orderNo);
        orderRequest.setTotalFee(actualPrice);
        orderRequest.setSpbillCreateIp(ip);
        return matrixPayService.createOrder(orderRequest);
    }

}
