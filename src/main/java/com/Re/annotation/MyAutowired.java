package com.Re.annotation;

import java.lang.annotation.*;

/**
 * 自动注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAutowired {
    String value() default "";
}
