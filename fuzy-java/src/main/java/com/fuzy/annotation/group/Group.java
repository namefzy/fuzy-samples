package com.fuzy.annotation.group;

import java.lang.annotation.*;

/**
 * @author fuzy
 * @version 1.0
 * @Description 注解用来作为分组功能
  
 * @date 2021/5/12 10:30
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Group {
}
