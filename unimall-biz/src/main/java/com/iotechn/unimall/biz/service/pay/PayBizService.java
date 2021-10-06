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
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.enums.PlatformType;
import com.iotechn.unimall.data.exception.ExceptionDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class PayBizService extends BaseService<UserDTO, AdminDTO> {

    @Autowired
    private MatrixPayService matrixPayService;

    @Autowired
    private FwWxAppProperties fwWxAppProperties;

    @Autowired
    private FwAliAppProperties fwAliAppProperties;


    public Object commonPrepay(
            String orderNo,
            int actualPrice,
            int platform,
            String payChannel,
            String ip) throws ServiceException {
        // 前端来决定支付方式
        MatrixPayUnifiedOrderRequest orderRequest = new MatrixPayUnifiedOrderRequest();
        if (PlatformType.MP_WEIXIN.getCode().intValue() == platform) {
            orderRequest.setAppid(fwWxAppProperties.getMiniAppId());
            orderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);
            orderRequest.setPayChannel(PayChannelType.WX);
            orderRequest.setOpenid(sessionUtil.getUser().getWxMpOpenId());
            orderRequest.setPayPlatform(PayPlatformType.MP);
        } else if (PlatformType.APP.getCode().intValue() == platform) {
            if (ObjectUtil.isEmpty(payChannel)) {
                throw new AppServiceException(ExceptionDefinition.ORDER_APP_PAY_MUST_CHANNEL);
            }
            orderRequest.setPayPlatform(PayPlatformType.APP);
            if (PayChannelType.WX.getCode().equals(payChannel)) {
                orderRequest.setAppid(fwWxAppProperties.getAppId());
                orderRequest.setTradeType(WxPayConstants.TradeType.APP);
                orderRequest.setPayChannel(PayChannelType.WX);
                orderRequest.setOpenid(sessionUtil.getUser().getWxAppOpenId());
            } else if (PayChannelType.ALI.getCode().equals(payChannel)) {
                orderRequest.setAppid(fwAliAppProperties.getAppId());
                orderRequest.setPayChannel(PayChannelType.ALI);
            } else {
                // TODO 添加支付宝
                throw new AppServiceException(ExceptionDefinition.ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY);
            }
        } else if (PlatformType.H5.getCode().intValue() == platform) {
            orderRequest.setAppid(fwWxAppProperties.getH5AppId());
            orderRequest.setTradeType(WxPayConstants.TradeType.JSAPI);
            orderRequest.setPayChannel(PayChannelType.WX);
            orderRequest.setOpenid(sessionUtil.getUser().getWxH5OpenId());
        } else if (PlatformType.MP_ALI.getCode().intValue() == platform) {
            orderRequest.setAppid(fwAliAppProperties.getMiniAppId());
            orderRequest.setPayChannel(PayChannelType.ALI);
        } else {
            throw new AppServiceException(ExceptionDefinition.ORDER_LOGIN_TYPE_NOT_SUPPORT_WXPAY);
        }
        // 区分回调 直接通过 S 来判断
        orderRequest.setOutTradeNo(orderNo);
        orderRequest.setBody("buy_" + orderNo);
        orderRequest.setTotalFee(actualPrice);
        orderRequest.setSpbillCreateIp(ip);
        return matrixPayService.createOrder(orderRequest);
    }

}
