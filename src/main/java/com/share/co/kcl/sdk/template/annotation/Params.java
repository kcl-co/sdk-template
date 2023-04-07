package com.share.co.kcl.sdk.template.annotation;

import com.share.co.kcl.sdk.template.enums.ParamsType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Params {

    /**
     * 请求字段类型
     */
    ParamsType type();

    /**
     * 请求字段名，默认为变量名
     */
    String name() default "";

}
