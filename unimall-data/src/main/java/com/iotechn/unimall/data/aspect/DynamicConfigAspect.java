package com.iotechn.unimall.data.aspect;

import com.iotechn.unimall.data.annotaion.DynamicConfigProperties;
import com.iotechn.unimall.data.component.DynamicConfigComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Description: 动态配置GET方法重新定义其功能切面
 * User: rize
 * Date: 2020/8/6
 * Time: 14:31
 */
@Aspect
@Component
public class DynamicConfigAspect {

    @Autowired
    private DynamicConfigComponent dynamicConfigComponent;

    @Pointcut("execution(public * com.iotechn.unimall.data.properties.*.get*())")
    public void cachePointCut() {}

    @Around("cachePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        DynamicConfigProperties annotation = (DynamicConfigProperties)signature.getDeclaringType().getAnnotation(DynamicConfigProperties.class);
        String prefix = annotation.prefix();
        // 去对应的分组读取配置
        Class returnType = signature.getReturnType();
        if (returnType == String.class) {
            return dynamicConfigComponent.readString(prefix + getField(signature.getName()), null);
        } else if (returnType == Integer.class) {
            return dynamicConfigComponent.readInt(prefix + getField(signature.getName()), null);
        } else if (returnType == Long.class) {
            return dynamicConfigComponent.readLong(prefix + getField(signature.getName()), null);
        }
        return joinPoint.proceed();
    }

    private String getField(String name) {
        char[] dst = new char[name.length() - 3];
        name.getChars(3, name.length(), dst, 0);
        if ('A' <= dst[0] && 'Z' >= dst[0]) {
            dst[0] = (char) (dst[0] + 32);
        }
        return new String(dst);
    }

}
