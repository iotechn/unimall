package com.iotechn.unimall.data.notify;

import com.iotechn.unimall.core.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019/11/17
 * Time: 15:50
 */
public class MockSMSClient implements SMSClient {

    private static final Logger logger = LoggerFactory.getLogger(MockSMSClient.class);

    @Override
    public SMSResult sendRegisterVerify(String phone, String verifyCode) throws ServiceException {
        return send(phone, verifyCode);
    }

    @Override
    public SMSResult sendBindPhoneVerify(String phone, String verifyCode) throws ServiceException {
        return send(phone, verifyCode);
    }

    @Override
    public SMSResult sendResetPasswordVerify(String phone, String verifyCode) throws ServiceException {
        return send(phone, verifyCode);
    }

    @Override
    public SMSResult sendAdminLoginVerify(String phone, String verifyCode) throws ServiceException {
        return send(phone, verifyCode);
    }

    public SMSResult send(String phone, String verifyCode) {
        logger.info("[模拟短信发送] phone=" + phone + "; verifyCode=" + verifyCode);
        SMSResult smsResult = new SMSResult();
        smsResult.setSucc(true);
        smsResult.setMsg("OK");
        return smsResult;
    }
}
