package com.iotechn.unimall.biz.config.pay;

import cn.hutool.core.codec.Base64;
import com.dobbinsoft.fw.core.util.SessionUtil;
import com.dobbinsoft.fw.pay.callback.PayHttpCallbackServlet;
import com.dobbinsoft.fw.pay.config.PayProperties;
import com.dobbinsoft.fw.pay.enums.PayPlatformType;
import com.dobbinsoft.fw.pay.handler.MatrixPayCallbackHandler;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayService;
import com.dobbinsoft.fw.pay.service.pay.MatrixPayServiceImpl;
import com.dobbinsoft.fw.support.properties.FwAliAppProperties;
import com.dobbinsoft.fw.support.properties.FwWxAppProperties;
import com.dobbinsoft.fw.support.properties.FwWxPayProperties;
import com.iotechn.unimall.biz.pay.OrderPayCallbackHandler;
import com.iotechn.unimall.biz.pay.VipOrderPayCallbackHandler;
import com.iotechn.unimall.data.dto.AdminDTO;
import com.iotechn.unimall.data.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class PayConfig {

    @Autowired
    private FwWxPayProperties fwWxPayProperties;

    @Autowired
    private FwAliAppProperties fwAliAppProperties;

    @Autowired
    private FwWxAppProperties fwWxAppProperties;

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
            return fwAliAppProperties.getAliGateway();
        }

        @Override
        public String getAliNotifyUrl() {
            return fwAliAppProperties.getAppNotifyUrl();
        }
    }

    @Bean
    public MatrixPayService matrixPayService() {
        MatrixPayServiceImpl matrixPayService = new MatrixPayServiceImpl(new PayDynamicPropertiesImpl());
        List<PayProperties> payProperties = new ArrayList<>();
        if (!ObjectUtils.isEmpty(fwAliAppProperties.getMiniAppId())) {
            payProperties.add(new AbstractPayProperties() {
                @Override
                public String getAliAppId() {
                    return fwAliAppProperties.getMiniAppId();
                }

                @Override
                public String getAliMchPrivateKey() {
                    return fwAliAppProperties.getMiniAppPrivateKey2();
                }

                @Override
                public String getAliAliPublicKey() {
                    return fwAliAppProperties.getMiniAppPublicKey1();
                }
            });
        }
        if (!ObjectUtils.isEmpty(fwAliAppProperties.getAppId())) {
            payProperties.add(new AbstractPayProperties() {
                @Override
                public String getAliAppId() {
                    return fwAliAppProperties.getAppId();
                }

                @Override
                public String getAliMchPrivateKey() {
                    return fwAliAppProperties.getAppPrivateKey2();
                }

                @Override
                public String getAliAliPublicKey() {
                    return fwAliAppProperties.getAppPublicKey1();
                }
            });
        }
        if (!ObjectUtils.isEmpty(fwAliAppProperties.getWapAppId())) {
            payProperties.add(new AbstractPayProperties() {
                @Override
                public String getAliAppId() {
                    return fwAliAppProperties.getWapAppId();
                }

                @Override
                public String getAliMchPrivateKey() {
                    return fwAliAppProperties.getWapAppPrivateKey2();
                }

                @Override
                public String getAliAliPublicKey() {
                    return fwAliAppProperties.getWapAppPublicKey1();
                }
            });
        }
        /**
         * 先将支付宝client加载到内存
         */
        matrixPayService.configWarmUp(payProperties);
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
                return fwWxAppProperties.getMiniAppId();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return fwWxAppProperties.getAppId();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return fwWxAppProperties.getH5AppId();
            }
            return null;
        }

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
        public byte[] getWxCert() {
            String keyContent = fwWxPayProperties.getKeyContent();
            if (!ObjectUtils.isEmpty(keyContent)) {
                return Base64.decode(fwWxPayProperties.getKeyContent());
            }
            return new byte[0];
        }

        @Override
        public String getAliGateway() {
            return fwAliAppProperties.getAliGateway();
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
                return fwAliAppProperties.getMiniAppId();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return fwAliAppProperties.getAppId();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return fwAliAppProperties.getWapAppId();
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
                return fwAliAppProperties.getMiniAppPrivateKey2();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return fwAliAppProperties.getAppPrivateKey2();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return fwAliAppProperties.getWapAppPrivateKey2();
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
                return fwAliAppProperties.getMiniAppPublicKey1();
            } else if (PayPlatformType.APP.getCode() == platform) {
                return fwAliAppProperties.getAppPublicKey1();
            } else if (PayPlatformType.WAP.getCode() == platform) {
                return fwAliAppProperties.getWapAppPublicKey1();
            }
            return null;
        }

        @Override
        public String getAliNotifyUrl() {
            return fwAliAppProperties.getAppNotifyUrl();
        }


    }

}
