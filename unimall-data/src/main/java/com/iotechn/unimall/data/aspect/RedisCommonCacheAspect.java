package com.iotechn.unimall.data.aspect;

import com.iotechn.unimall.data.annotation.AspectCommonCache;
import com.iotechn.unimall.data.component.CacheComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/28
 * Time: 14:09
 */
@Aspect
@Component
public class RedisCommonCacheAspect {

    @Autowired
    private CacheComponent cacheComponent;

    @Pointcut("@annotation(com.iotechn.unimall.data.annotation.AspectCommonCache)")
    public void cachePointCut() {}


    @Around("cachePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AspectCommonCache annotation = signature.getMethod().getAnnotation(AspectCommonCache.class);
        int[] ints = annotation.argIndex();
        Object[] args = joinPoint.getArgs();
        String key = annotation.value();
        for (int i = 0; i < ints.length; i++) {
            if (i != 0) {
                key = key + ":" + args[ints[i]];
            } else {
                key = key + args[ints[i]];
            }
        }

        // 走缓存
        if (annotation.arrayClass() != Object.class) {
            List objList = cacheComponent.getObjList(key, annotation.arrayClass());
            if (objList != null) {
                return objList;
            }
        } else {
            Object obj = cacheComponent.getObj(key, signature.getReturnType());
            if (obj != null) {
                return obj;
            }
        }

        // 走方法
        Object proceed = joinPoint.proceed();
        if (annotation.second() > 0) {
            cacheComponent.putObj(key, proceed, annotation.second());
        } else {
            cacheComponent.putObj(key, proceed);
        }
        return proceed;
    }

}
