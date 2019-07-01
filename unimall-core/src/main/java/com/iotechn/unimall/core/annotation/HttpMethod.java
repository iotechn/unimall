package com.iotechn.unimall.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-03-25
 * Time: 下午1:12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpMethod {
    String description();
    ResultType type() default ResultType.COMMONS;
    String retName() default "";
    int maxAge() default -1;
    String permission() default "";
    String permissionParentName() default "";
    String permissionName() default "";
}
