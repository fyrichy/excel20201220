package com.fuyu.excel.annotation;

import java.lang.annotation.*;

/**
 * 权限过滤注解
 * value表示要检查的字段，比如为deptCode
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterAuth {

    String value() default "";
}
