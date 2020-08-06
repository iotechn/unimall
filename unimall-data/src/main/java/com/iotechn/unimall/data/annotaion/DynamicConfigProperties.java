package com.iotechn.unimall.data.annotaion;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Documented
public @interface DynamicConfigProperties {

    public String prefix();

}
