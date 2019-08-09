package com.iotechn.unimall.core.notify;

import com.iotechn.unimall.core.exception.ServiceException;

/**
 * Created by rize on 2019/7/1.
 */
public interface SMSClient {

    public SMSResult sendRegisterVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendBindPhoneVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendResetPasswordVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendAdminLoginVerify(String phone,String verifyCode) throws ServiceException;


}
