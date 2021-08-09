package com.iotechn.unimall.biz.config.pay;

import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.service.pay.PayService;
import com.dobbinsoft.fw.pay.service.pay.PayServiceImpl;
import com.iotechn.unimall.data.properties.UnimallAliPayProperties;
import com.iotechn.unimall.data.properties.UnimallWxPayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayConfig {

    @Autowired
    private UnimallWxPayProperties unimallWxPayProperties;

    @Autowired
    private UnimallAliPayProperties unimallAliPayProperties;

    @Bean
    public PayService payService() {
        return new PayServiceImpl(new PayDynamicPropertiesImpl());
    }

//    @Bean
//    public ConsignmentOrderPayCallbackHandler consignmentOrderPayCallbackHandler() {
//        ConsignmentOrderPayCallbackHandler payOrderCallbackHandler = new ConsignmentOrderPayCallbackHandler();
//        // 加入处理责任链
//        AbstractPayCallbackHandler.append(payOrderCallbackHandler, payOrderCallbackHandler.getChainName());
//        return payOrderCallbackHandler;
//    }

    public class PayDynamicPropertiesImpl implements PayProperties {

        @Override
        public String getWxMchId() {
            return unimallWxPayProperties.getMchId();
        }

        @Override
        public String getWxMchKey() {
            return unimallWxPayProperties.getMchKey();
        }

        @Override
        public String getWxNotifyUrl() {
            return unimallWxPayProperties.getNotifyUrl();
        }

        @Override
        public String getWxKeyPath() {
            return unimallWxPayProperties.getKeyPath();
        }

        @Override
        public String getAliGateway() {
            return unimallAliPayProperties.getGateway();
        }

        @Override
        public String getAliMchPrivateKey() {
            return unimallAliPayProperties.getMchPrivateKey();
        }

        @Override
        public String getAliAliPublicKey() {
            return unimallAliPayProperties.getAliPublicKey();
        }

        @Override
        public String getAliNotifyUrl() {
            return unimallAliPayProperties.getNotifyUrl();
        }

    }

}
