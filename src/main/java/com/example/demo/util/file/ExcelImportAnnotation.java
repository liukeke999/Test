package com.example.demo.util.file;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义到 对象中的属性的 标致它导入Excel'时的列位置
 */
//运行时注解
@Retention(RetentionPolicy.RUNTIME)
//属性注解
@Target(ElementType.FIELD)
public @interface ExcelImportAnnotation {
    /** 对应的列名称 */
    String name() default "";

    /** 列序号
     * 属性 上short=0  在excel导入时他就是第一列
     * */
    int sort();

    /** 字段类型对应的格式 */
    String format() default "";

}

