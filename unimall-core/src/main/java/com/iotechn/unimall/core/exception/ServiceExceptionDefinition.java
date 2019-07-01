package com.iotechn.unimall.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceExceptionDefinition {
    private int code;
    private String msg;
}