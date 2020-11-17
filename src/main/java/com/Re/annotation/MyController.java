package com.Re.annotation;

import java.lang.annotation.*;

/**
 * 控制器注解
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyController {
    String value()default "";
}
