package com.Re.annotation;

import java.lang.annotation.*;

/**
 * 扫描注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyComponentScan {
    String []value() default {};
}
