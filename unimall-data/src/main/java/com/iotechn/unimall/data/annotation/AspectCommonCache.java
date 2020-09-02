package com.iotechn.unimall.data.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2020/3/28
 * Time: 13:59
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AspectCommonCache {

    /**
     * 键值
     * @return
     */
    String value() default "";

    int[] argIndex() default {};

    /**
     * 需要反序列化为List泛型才需要传入。否则会自动格式化为 List<JSONObject> 是不影响
     * @return
     */
    Class arrayClass() default Object.class;

    /**
     * 小于0代表不过期
     * @return
     */
    int second() default 60 * 60 * 24;

}
