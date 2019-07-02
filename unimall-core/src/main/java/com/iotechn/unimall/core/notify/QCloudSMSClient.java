package com.iotechn.unimall.core.notify;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.iotechn.unimall.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by rize on 2019/7/1.
 */
public class QCloudSMSClient implements SMSClient,InitializingBean {

    private SmsSingleSender sender;

    @Value("${sms.qcloud.app-id}")
    private Integer appid;
    @Value("${sms.qcloud.app-key}")
    private String appkey;
    @Value("${sms.qcloud.register-template-id}")
    private Integer registerTemplateId;
    @Value("${sms.qcloud.bind-phone-template-id}")
    private Integer bindPhoneTemplateId;
    @Value("${sms.qcloud.reset-password-template-id}")
    private Integer resetPasswordTemplateId;

    private static final Logger logger = LoggerFactory.getLogger(QCloudSMSClient.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        sender = new SmsSingleSender(appid, appkey);
    }

    public SMSResult sendMsg(String phone, int templateId, String ...params) throws ServiceException {
        try {
            SmsSingleSenderResult smsSingleSenderResult = sender.sendWithParam("86", phone, templateId, params, "", "", "");
            logger.info("[]");
        } catch (Exception e) {
            logger.error("[腾讯短信发送] 异常", e);
            //TODO
        }
        return null;
    }


    @Override
    public SMSResult sendRegisterVerify(String phone, String verifyCode) throws ServiceException {
        return sendMsg(phone, registerTemplateId, verifyCode);
    }

    @Override
    public SMSResult sendBindPhoneVerify(String phone, String verifyCode) throws ServiceException {
        return sendMsg(phone, registerTemplateId, verifyCode);
    }

    @Override
    public SMSResult sendResetPasswordVerify(String phone, String verifyCode) throws ServiceException {
        return sendMsg(phone, registerTemplateId, verifyCode);
    }
}
