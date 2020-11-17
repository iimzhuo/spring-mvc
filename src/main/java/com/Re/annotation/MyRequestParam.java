package com.Re.annotation;

import java.lang.annotation.*;

/**
 * 映射参数注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface MyRequestParam {
    String value();
}
