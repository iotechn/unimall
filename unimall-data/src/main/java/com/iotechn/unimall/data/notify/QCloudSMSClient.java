package com.iotechn.unimall.data.notify;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.iotechn.unimall.core.exception.ExceptionDefinition;
import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ThirdPartServiceException;
import com.iotechn.unimall.data.properties.UnimallSMSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by rize on 2019/7/1.
 */
public class QCloudSMSClient implements SMSClient,InitializingBean {

    private SmsSingleSender sender;

    @Autowired
    private UnimallSMSProperties properties;

    private static final Logger logger = LoggerFactory.getLogger(QCloudSMSClient.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        sender = new SmsSingleSender(properties.getQcloudAppId(), properties.getQcloudAppKey());
    }

    public SMSResult sendMsg(String phone, int templateId, String ...params) throws ServiceException {
        try {
            SmsSingleSenderResult smsSingleSenderResult = sender.sendWithParam("86", phone, templateId, params, properties.getQcloudSignature(), "", "");
            if (smsSingleSenderResult.result == 0) {
                SMSResult smsResult = new SMSResult();
                smsResult.setSucc(true);
                smsResult.setMsg("成功");
                return smsResult;
            } else {
                logger.info("[腾讯短信发送] 回复与预期不一致 result=" + smsSingleSenderResult.result + ";errMsg=" + smsSingleSenderResult.errMsg);
                throw new ThirdPartServiceException(smsSingleSenderResult.errMsg, smsSingleSenderResult.result);
            }
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            logger.error("[腾讯短信发送] 异常", e);
            throw new ThirdPartServiceException("腾讯云短信发送未知异常", ExceptionDefinition.THIRD_PART_SERVICE_EXCEPTION.getCode());
        }
    }


    @Override
    public SMSResult sendRegisterVerify(String phone, String verifyCode) throws ServiceException {
        return sendMsg(phone, properties.getQcloudRegisterTemplateId(), verifyCode);
    }

    @Override
    public SMSResult sendBindPhoneVerify(String phone, String verifyCode) throws ServiceException {
        return sendMsg(phone, properties.getQcloudBindPhoneTemplateId(), verifyCode);
    }

    @Override
    public SMSResult sendResetPasswordVerify(String phone, String verifyCode) throws ServiceException {
        return sendMsg(phone, properties.getQcloudResetPasswordTemplateId(), verifyCode);
    }

    @Override
    public SMSResult sendAdminLoginVerify(String phone, String verifyCode) throws ServiceException {
        return sendMsg(phone, properties.getQcloudAdminLoginTemplateId(),verifyCode);
    }
}
