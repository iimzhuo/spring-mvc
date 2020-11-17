package com.Re.annotation;

import java.lang.annotation.*;

/**
 * Service层注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyService {
    String value() default "";
}
