package com.Re.annotation;

import java.lang.annotation.*;

/**
 * 提供依赖注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyQualifier {
    String value();
}
