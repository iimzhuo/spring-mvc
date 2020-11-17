package com.Re.annotation;

import java.lang.annotation.*;

/**
 * 持久层注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyRepository {
    String value() default "";
}
