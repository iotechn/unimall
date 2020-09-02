package com.iotechn.unimall.data.annotation;

import java.lang.annotation.*;

/**
 * 用于描述pojo需要同步到搜索引擎的类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SearchTable {

    String value();

}
