package com.iotechn.unimall.biz.config.pay;

import com.dobbinsoft.fw.core.util.SessionUtil;
import com.dobbinsoft.fw.pay.callback.PayHttpCallbackServlet;
import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.handler.MatrixPayCallbackHandler;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayServiceImpl;
import com.iotechn.unimall.biz.pay.OrderPayCallbackHandler;
import com.iotechn.unimall.biz.pay.VipOrderPayCallbackHandler;
import com.iotechn.unimall.data.dto.admin.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import com.iotechn.unimall.data.properties.UnimallAliAppProperties;
import com.iotechn.unimall.data.properties.UnimallWxAppProperties;
import com.iotechn.unimall.data.properties.UnimallWxPayProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Configuration
public class PayConfig {

    @Autowired
    private UnimallWxPayProperties unimallWxPayProperties;

    @Autowired
    private UnimallAliAppProperties unimallAliAppProperties;

    @Autowired
    private UnimallWxAppProperties unimallWxAppProperties;

    @Autowired
    private SessionUtil<UserDTO, AdminDTO> sessionUtil;

    private static final Logger logger = LoggerFactory.getLogger(PayConfig.class);

    private abstract class AbstractPayProperties implements PayProperties {
        @Override
        public String getWxAppId() {
            return null;
        }

        @Override
        public String getWxMchId() {
            return null;
        }

        @Override
        public String getWxMchKey() {
            return null;
        }

        @Override
        public String getWxNotifyUrl() {
            return null;
        }

        @Override
        public byte[] getWxCert() {
            return new byte[0];
        }

        @Override
        public String getAliGateway() {
            return unimallAliAppProperties.getAliGateway();
        }

        @Override
        public String getAliNotifyUrl() {
            return unimallAliAppProperties.getAppNotifyUrl();
        }
    }

    @Bean
    public MatrixPayService matrixPayService() {
        MatrixPayServiceImpl matrixPayService = new MatrixPayServiceImpl(new PayDynamicPropertiesImpl());
        List<PayProperties> payProperties = new ArrayList<>();
        if (!ObjectUtils.isEmpty(unimallAliAppProperties.getMiniAppId())) {
            payProperties.add(new AbstractPayProperties() {
                @Override
                public String getAliAppId() {
                    return unimallAliAppProperties.getMiniAppId();
                }

                @Override
                public String getAliMchPrivateKey() {
                    return unimallAliAppProperties.getMiniAppPrivateKey2();
                }

                @Override
                public String getAliAliPublicKey() {
                    return unimallAliAppProperties.getMiniAppPublicKey1();
                }
            });
        }
        if (!ObjectUtils.isEmpty(unimallAliAppProperties.getAppId())) {
            payProperties.add(new AbstractPayProperties() {
                @Override
                public String getAliAppId() {
                    return unimallAliAppProperties.getAppId();
                }

                @Override
                public String getAliMchPrivateKey() {
                    return unimallAliAppProperties.getAppPrivateKey2();
                }

                @Override
                public String getAliAliPublicKey() {
                    return unimallAliAppProperties.getAppPublicKey1();
                }
            });
        }
        if (!ObjectUtils.isEmpty(unimallAliAppProperties.getWapAppId())) {
            payProperties.add(new AbstractPayProperties() {
                @Override
                public String getAliAppId() {
                    return unimallAliAppProperties.getWapAppId();
                }

                @Override
                public String getAliMchPrivateKey() {
                    return unimallAliAppProperties.getWapAppPrivateKey2();
                }

                @Override
                public String getAliAliPublicKey() {
                    return unimallAliAppProperties.getWapAppPublicKey1();
                }
            });
        }
        /**
         * 先将支付宝client加载到内存
         */
//        matrixPayService.configWarmUp(payProperties);
        return matrixPayService;
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
        Map<String, MatrixPayCallbackHandler> urlHandlerMap = new HashMap<>();
        urlHandlerMap.put("/cb/pay", orderPayCallbackHandler());
        urlHandlerMap.put("/cb/pay/vip", vipOrderPayCallbackHandler());
        return new ServletRegistrationBean(new PayHttpCallbackServlet(matrixPayService(), urlHandlerMap), urlHandlerMap.keySet().toArray(new String[]{}));
    }


    /**
     * MatrixPay 获取外部配置的接口实现类
     */
    public class PayDynamicPropertiesImpl implements PayProperties {

        @Override
        public String getWxAppId() {
            // 根据用户会话来决定，使用哪个APPID
            UserDTO user = sessionUtil.getUser();
            if (user == null) {
                return null;
            }
            Integer platform = user.getPlatform();
            if (PayPlatformType.MP.getCode() == platform) {
                return unimallWxAppProperties.getMiniAppId();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return unimallWxAppProperties.getAppId();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return unimallWxAppProperties.getH5AppId();
            }
            return null;
        }

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
        public byte[] getWxCert() {
            String keyContent = unimallWxPayProperties.getKeyContent();
            if (!ObjectUtils.isEmpty(keyContent)) {
                return Base64.getDecoder().decode(unimallWxPayProperties.getKeyContent());
            }
            return new byte[0];
        }

        @Override
        public String getAliGateway() {
            return unimallAliAppProperties.getAliGateway();
        }

        @Override
        public String getAliAppId() {
            // 根据用户会话来决定，使用哪个APPID
            UserDTO user = sessionUtil.getUser();
            if (user == null) {
                return null;
            }
            Integer platform = user.getPlatform();
            if (PayPlatformType.MP.getCode() == platform) {
                return unimallAliAppProperties.getMiniAppId();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return unimallAliAppProperties.getAppId();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return unimallAliAppProperties.getWapAppId();
            }
            return null;
        }

        @Override
        public String getAliMchPrivateKey() {
            UserDTO user = sessionUtil.getUser();
            if (user == null) {
                return null;
            }
            Integer platform = user.getPlatform();
            if (PayPlatformType.MP.getCode() == platform) {
                return unimallAliAppProperties.getMiniAppPrivateKey2();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return unimallAliAppProperties.getAppPrivateKey2();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return unimallAliAppProperties.getWapAppPrivateKey2();
            }
            return null;
        }

        @Override
        public String getAliAliPublicKey() {
            UserDTO user = sessionUtil.getUser();
            if (user == null) {
                return null;
            }
            Integer platform = user.getPlatform();
            if (PayPlatformType.MP.getCode() == platform) {
                return unimallAliAppProperties.getMiniAppPublicKey1();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return unimallAliAppProperties.getAppPublicKey1();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return unimallAliAppProperties.getWapAppPublicKey1();
            }
            return null;
        }

        @Override
        public String getAliNotifyUrl() {
            return unimallAliAppProperties.getAppNotifyUrl();
        }


    }

}
