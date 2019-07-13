package com.iotechn.unimall.core.exception;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: kbq
 * Date: 2019-07-07
 * Time: 下午4:21
 */
public class AdminServiceException extends ServiceException {

    public AdminServiceException(ServiceExceptionDefinition definition) {
        super(definition);
    }

    public AdminServiceException(String message, int code) {
        super(message,code);
    }

}
