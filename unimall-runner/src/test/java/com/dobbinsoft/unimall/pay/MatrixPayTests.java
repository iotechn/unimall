package com.dobbinsoft.unimall.pay;

import com.dobbinsoft.fw.pay.enums.PayChannelType;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.model.request.MatrixPayUnifiedOrderRequest;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.support.utils.StringUtils;
import com.dobbinsoft.unimall.data.properties.UnimallWxAppProperties;
import com.dobbinsoft.unimall.data.properties.UnimallWxPayProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = com.dobbinsoft.unimall.runner.UnimallRunnerApplication.class)
public class MatrixPayTests {

    @Autowired
    private MatrixPayService matrixPayService;

    @Autowired
    private UnimallWxAppProperties unimallWxAppProperties;

    @Autowired
    private UnimallWxPayProperties unimallWxPayProperties;

    @Test
    public void testWxPrepay() {
        // 前端来决定支付方式
        MatrixPayUnifiedOrderRequest orderRequest = new MatrixPayUnifiedOrderRequest();
        orderRequest.setPayChannel(PayChannelType.WX);
        orderRequest.setAppid(unimallWxAppProperties.getMiniAppId());
        orderRequest.setOpenid("");
        orderRequest.setNotifyUrl(unimallWxPayProperties.getNotifyUrl() + "/vip");
        // 区分回调 直接通过 S 来判断
        orderRequest.setOutTradeNo(StringUtils.uuid());
        orderRequest.setOpenid("oKzU549rhtSDv3EYZaAfhlWTyhJg");
        orderRequest.setBody("vip_" + orderRequest.getOutTradeNo());
        orderRequest.setTotalFee(1);
        orderRequest.setSpbillCreateIp("111.10.243.142");
        orderRequest.setPayPlatform(PayPlatformType.MP);
        orderRequest.setPayChannel(PayChannelType.WX);
        Object order = matrixPayService.createOrder(orderRequest);
        return;
    }

}
