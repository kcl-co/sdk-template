package com.share.co.kcl.sdk.template.annotation;

import com.share.co.kcl.sdk.template.enums.ParamsType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredAppId {

    /**
     * AppId的字段类型
     */
    ParamsType type();

    /**
     * AppId的字段名
     */
    String name() default "appId";
}
