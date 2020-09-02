package com.iotechn.unimall.core.exception;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2019-01-31
 * Time: 下午8:07
 */
public abstract class ServiceException extends Exception implements Serializable {

    private int code;

    private Object attach;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ServiceException() {
    }

    public ServiceException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServiceException(ServiceExceptionDefinition definition) {
        super(definition.getMsg());
        this.code = definition.getCode();
    }

    public ServiceException attach(Object attach) {
        this.attach = attach;
        return this;
    }
}
