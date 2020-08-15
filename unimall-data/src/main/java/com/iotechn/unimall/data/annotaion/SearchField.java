package com.iotechn.unimall.data.annotaion;

import java.lang.annotation.*;

/**
 * 用于描述需要pojo需要同步到搜索引擎的字段
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SearchField {

    String value();

}
