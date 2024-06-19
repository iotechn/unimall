package com.dobbinsoft.unimall.runner.exception;

import com.dobbinsoft.fw.core.exception.ServiceException;
import com.dobbinsoft.fw.launcher.exception.OtherExceptionTransfer;
import com.dobbinsoft.fw.pay.exception.MatrixPayException;
import com.dobbinsoft.unimall.data.exception.ExceptionDefinition;
import org.springframework.stereotype.Component;

@Component
public class MatrixPayExceptionTransfer implements OtherExceptionTransfer<MatrixPayException> {
    @Override
    public ServiceException trans(MatrixPayException e) {
        return new ServiceException(e.getMessage(), ExceptionDefinition.MATRIX_PAY_EXCEPTION.getCode());
    }

    @Override
    public Class<?> getExceptionClass() {
        return MatrixPayException.class;
    }
}
