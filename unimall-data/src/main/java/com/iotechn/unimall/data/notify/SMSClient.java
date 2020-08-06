package com.iotechn.unimall.data.notify;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.data.notify.SMSResult;

/**
 * Created by rize on 2019/7/1.
 */
public interface SMSClient {

    public SMSResult sendRegisterVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendBindPhoneVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendResetPasswordVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendAdminLoginVerify(String phone,String verifyCode) throws ServiceException;


}
