package com.fuyu.excel.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {
    //标题
    String title() default "";
    //排序
    int sort() default 0;
}
