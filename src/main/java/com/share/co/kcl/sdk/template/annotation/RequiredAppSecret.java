package com.share.co.kcl.sdk.template.annotation;

import com.share.co.kcl.sdk.template.enums.ParamsType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredAppSecret {

    /**
     * AppSecret的字段类型
     */
    ParamsType type();

    /**
     * AppSecret的字段名
     */
    String name() default "appSecret";

}
