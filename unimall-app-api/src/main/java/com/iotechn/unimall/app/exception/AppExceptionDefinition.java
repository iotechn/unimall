package com.iotechn.unimall.app.exception;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ServiceExceptionDefinition;

/**
 * Created by rize on 2019/7/1.
 */
public class AppExceptionDefinition {

    public static final ServiceExceptionDefinition USER_UNKNOWN_EXCEPTION =
            new ServiceExceptionDefinition(11000, "用户系统未知异常");

    public static final ServiceExceptionDefinition USER_SEND_VERIFY_FAILED =
            new ServiceExceptionDefinition(11001, "发送验证码失败");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_EXIST =
            new ServiceExceptionDefinition(11002, "验证码未发送或已过期");

    public static final ServiceExceptionDefinition USER_VERIFY_CODE_NOT_CORRECT =
            new ServiceExceptionDefinition(11003, "验证码不正确");

    public static final ServiceExceptionDefinition USER_PHONE_HAS_EXISTED =
            new ServiceExceptionDefinition(11004, "手机已经被注册");

    public static final ServiceExceptionDefinition USER_PHONE_NOT_EXIST =
            new ServiceExceptionDefinition(11005, "手机尚未绑定账号");

    public static final ServiceExceptionDefinition USER_PHONE_OR_PASSWORD_NOT_CORRECT =
            new ServiceExceptionDefinition(11006, "手机号或密码错误!");


}
