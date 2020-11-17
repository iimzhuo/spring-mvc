package com.Re.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MyComponent {
    String value() default "";
}
