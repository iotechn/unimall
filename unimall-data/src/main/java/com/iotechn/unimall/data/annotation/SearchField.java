package com.iotechn.unimall.data.annotation;

import com.iotechn.unimall.data.enums.SearchEngineTokenizerType;

import java.lang.annotation.*;

/**
 * 用于描述需要pojo需要同步到搜索引擎的字段
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface SearchField {

    String value();

    SearchEngineTokenizerType tokenizer() default SearchEngineTokenizerType.NONE;

}
