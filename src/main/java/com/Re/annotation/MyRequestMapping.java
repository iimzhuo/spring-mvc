package com.Re.annotation;

import java.lang.annotation.*;

/**
 * 处理器映射器注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface MyRequestMapping {
    String value();
}
