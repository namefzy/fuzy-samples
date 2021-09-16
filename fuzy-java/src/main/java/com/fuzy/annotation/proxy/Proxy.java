package com.fuzy.annotation.proxy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author fuzy
 * @version 1.0
 * @Description
  
 * @date 2021/5/12 11:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface Proxy {
    String value() default "";
}
