package com.Re.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@MyComponent
public @interface MyConfiguration {
}
