package com.share.co.kcl.sdk.template.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentType {

    String name() default "Content-Type";

    String value() default "";
}
