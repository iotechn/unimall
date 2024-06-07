package com.dobbinsoft.unimall.biz.util;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.core.util.SessionUtil;
import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.unimall.data.dto.admin.AdminDTO;
import com.dobbinsoft.unimall.data.dto.UserDTO;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import com.dobbinsoft.unimall.data.properties.UnimallAliAppProperties;
import com.dobbinsoft.unimall.data.properties.UnimallWxAppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaySelector {

    @Autowired
    private UnimallWxAppProperties unimallWxAppProperties;

    @Autowired
    private UnimallAliAppProperties unimallAliAppProperties;

    @Autowired
    private SessionUtil<UserDTO, AdminDTO> sessionUtil;

    public void packPayChannel(MatrixPayUnifiedOrderRequest orderRequest, int payPlatform, String payChannel) throws ServiceException {
// 设置支付请求基本信息
        orderRequest.setPayPlatform(PayPlatformType.getByCode(payPlatform));
        if (PayPlatformType.MP.getCode() == payPlatform) {
            if (PayChannelType.WX.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.WX);
                orderRequest.setAppid(unimallWxAppProperties.getMiniAppId());
                orderRequest.setOpenid(sessionUtil.getUser().getWxMpOpenId());
            } else if (PayChannelType.ALI.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.ALI);
                orderRequest.setAppid(unimallAliAppProperties.getMiniAppId());
                orderRequest.setOpenid(sessionUtil.getUser().getAliMpOpenId());
            }
        } else if (PayPlatformType.APP.getCode() == payPlatform) {
            if (PayChannelType.WX.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.WX);
                orderRequest.setAppid(unimallWxAppProperties.getAppId());
                orderRequest.setOpenid(sessionUtil.getUser().getWxAppOpenId());
            } else if (PayChannelType.ALI.getCode().equals(payChannel)) {
                orderRequest.setPayChannel(PayChannelType.ALI);
                orderRequest.setAppid(unimallAliAppProperties.getAppId());
            }
        } else if (PayPlatformType.WAP.getCode() == payPlatform) {
            if (PayChannelType.WX.getCode().equals(payChannel)) {
                orderRequest.setAppid(unimallWxAppProperties.getH5AppId());
                orderRequest.setPayChannel(PayChannelType.WX);
                orderRequest.setOpenid(sessionUtil.getUser().getWxH5OpenId());
            } else if (PayChannelType.ALI.getCode().equals(payChannel)) {
                orderRequest.setAppid(unimallAliAppProperties.getWapAppId());
                orderRequest.setPayChannel(PayChannelType.ALI);
            }
        } else {
            throw new ServiceException(ExceptionDefinition.ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY);
        }
    }

}
