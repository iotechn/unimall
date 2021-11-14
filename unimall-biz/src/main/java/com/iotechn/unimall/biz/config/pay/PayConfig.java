package com.iotechn.unimall.biz.config.pay;

import com.dobbinsoft.fw.pay.callback.PayHttpCallbackServlet;
import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.handler.PayCallbackHandler;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayServiceImpl;
import com.dobbinsoft.fw.support.properties.FwAliAppProperties;
import com.dobbinsoft.fw.support.properties.FwWxPayProperties;
import com.iotechn.unimall.biz.pay.OrderPayCallbackHandler;
import com.iotechn.unimall.biz.pay.VipOrderPayCallbackHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PayConfig {

    @Autowired
    private FwWxPayProperties fwWxPayProperties;

    @Autowired
    private FwAliAppProperties fwAliAppProperties;

    @Bean
    public MatrixPayService matrixPayService() {
        return new MatrixPayServiceImpl(new PayDynamicPropertiesImpl());
    }

    @Bean
    public OrderPayCallbackHandler orderPayCallbackHandler() {
        return new OrderPayCallbackHandler();
    }

    @Bean
    public VipOrderPayCallbackHandler vipOrderPayCallbackHandler() {
        return new VipOrderPayCallbackHandler();
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        Map<String, PayCallbackHandler> urlHandlerMap = new HashMap<>();
        urlHandlerMap.put("/cb/pay", orderPayCallbackHandler());
        urlHandlerMap.put("/cb/vip", vipOrderPayCallbackHandler());
        return new ServletRegistrationBean(new PayHttpCallbackServlet(matrixPayService(), urlHandlerMap), urlHandlerMap.keySet().toArray(new String[]{}));
    }


    public class PayDynamicPropertiesImpl implements PayProperties {

        @Override
        public String getWxMchId() {
            return fwWxPayProperties.getMchId();
        }

        @Override
        public String getWxMchKey() {
            return fwWxPayProperties.getMchKey();
        }

        @Override
        public String getWxNotifyUrl() {
            return fwWxPayProperties.getNotifyUrl();
        }

        @Override
        public String getWxKeyPath() {
            return fwWxPayProperties.getKeyPath();
        }

        @Override
        public String getAliGateway() {
            return fwAliAppProperties.getAliGateway();
        }

        @Override
        public String getAliMiniAppId() {
            return fwAliAppProperties.getMiniAppId();
        }

        @Override
        public String getAliMchMiniPrivateKey() {
            return fwAliAppProperties.getMiniAppPrivateKey2();
        }

        @Override
        public String getAliAliMiniPublicKey() {
            return fwAliAppProperties.getMiniAppPublicKey1();
        }

        @Override
        public String getAliMiniNotifyUrl() {
            return fwAliAppProperties.getMiniNotifyUrl();
        }

        @Override
        public String getAliAppAppId() {
            return fwAliAppProperties.getAppId();
        }

        @Override
        public String getAliMchAppPrivateKey() {
            return fwAliAppProperties.getAppPrivateKey2();
        }

        @Override
        public String getAliAliAppPublicKey() {
            return fwAliAppProperties.getAppPublicKey1();
        }

        @Override
        public String getAliAppNotifyUrl() {
            return fwAliAppProperties.getAppNotifyUrl();
        }

        // Unimall 暂无Web PC版
        @Override
        public String getAliWebAppId() {
            return null;
        }

        @Override
        public String getAliMchWebPrivateKey() {
            return null;
        }

        @Override
        public String getAliAliWebPublicKey() {
            return null;
        }

        @Override
        public String getAliWebNotifyUrl() {
            return null;
        }

    }

}
