package com.iotechn.unimall.biz.util;

import com.dobbinsoft.fw.core.exception.BizServiceException;
import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.util.SessionUtil;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.support.properties.FwAliAppProperties;
import com.dobbinsoft.fw.support.properties.FwWxAppProperties;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaySelector {

    @Autowired
    private FwWxAppProperties fwWxAppProperties;

    @Autowired
    private FwAliAppProperties fwAliAppProperties;

    @Autowired
    private SessionUtil<UserDTO, AdminDTO> sessionUtil;

    public void packPayChannel(MatrixPayUnifiedOrderRequest orderRequest, int payPlatform, String payChannel) throws ServiceException {
// 设置支付请求基本信息
        orderRequest.setPayPlatform(PayPlatformType.getByCode(payPlatform));
        if (PayPlatformType.MP.getCode() == payPlatform) {
            if (PayChannelType.WX.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.WX);
                orderRequest.setAppid(fwWxAppProperties.getMiniAppId());
                orderRequest.setOpenid(sessionUtil.getUser().getWxMpOpenId());
            } else if (PayChannelType.ALI.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.ALI);
                orderRequest.setAppid(fwAliAppProperties.getMiniAppId());
                orderRequest.setOpenid(sessionUtil.getUser().getAliMpOpenId());
            }
        } else if (PayPlatformType.APP.getCode() == payPlatform) {
            if (PayChannelType.WX.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.WX);
                orderRequest.setAppid(fwWxAppProperties.getAppId());
                orderRequest.setOpenid(sessionUtil.getUser().getWxAppOpenId());
            } else if (PayChannelType.ALI.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.ALI);
                orderRequest.setAppid(fwAliAppProperties.getAppId());
            }
        } else if (PayPlatformType.WAP.getCode() == payPlatform) {
            if (PayChannelType.WX.getCode().equals(payChannel)) {
                orderRequest.setAppid(fwWxAppProperties.getH5AppId());
                orderRequest.setPayChannel(PayChannelType.WX);
                orderRequest.setOpenid(sessionUtil.getUser().getWxH5OpenId());
            } else if (PayChannelType.ALI.getCode().equals(payChannel)) {
                orderRequest.setAppid(fwAliAppProperties.getWapAppId());
                orderRequest.setPayChannel(PayChannelType.ALI);
            }
        } else {
            throw new BizServiceException(ExceptionDefinition.ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY);
        }
    }

}
