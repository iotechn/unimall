package com.iotechn.unimall.core.exception;

import com.iotechn.unimall.core.exception.ServiceException;
import com.iotechn.unimall.core.exception.ServiceExceptionDefinition;

/**
 * Created by rize on 2019/7/1.
 */
public class AppServiceException extends ServiceException {

    public AppServiceException(ServiceExceptionDefinition definition) {
        super(definition);
    }

    public AppServiceException(String message, int code) {
        super(message,code);
    }
}
