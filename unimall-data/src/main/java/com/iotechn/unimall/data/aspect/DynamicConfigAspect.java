package com.iotechn.unimall.data.aspect;

import com.iotechn.unimall.core.util.ReflectUtil;
import com.iotechn.unimall.data.annotation.DynamicConfigProperties;
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

    /**
     * TODO 可对field定义一个注解，获取其默认值。并且添加JVM缓存功能，加快高频动态配置读取速度
     * 将DynamicConfigProperties对象Get方法拦截
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("cachePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        DynamicConfigProperties annotation = (DynamicConfigProperties)signature.getDeclaringType().getAnnotation(DynamicConfigProperties.class);
        String prefix = annotation.prefix();
        // 去对应的分组读取配置
        Class returnType = signature.getReturnType();
        if (returnType == String.class) {
            return dynamicConfigComponent.readString(prefix + ReflectUtil.getField(signature.getName()), null);
        } else if (returnType == Integer.class) {
            return dynamicConfigComponent.readInt(prefix + ReflectUtil.getField(signature.getName()), null);
        } else if (returnType == Long.class) {
            return dynamicConfigComponent.readLong(prefix + ReflectUtil.getField(signature.getName()), null);
        }
        return joinPoint.proceed();
    }


}
