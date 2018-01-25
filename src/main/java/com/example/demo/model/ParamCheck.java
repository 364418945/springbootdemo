package com.example.demo.model;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Repeatable(value=ParamChecks.class)
public @interface ParamCheck {
    // notNull ,notNullDepend,notNullBoth三个每次只能存在一种
    boolean notNull() default false;//设置值，指定必须非某个字段。设置空串，指定必填
//=========================================================================================
    String notNullDepend() default "";//当某字段满足条件时，当前字段不为空

    String[] notNullDependOn() default {};//当某字段满足条件时，当前字段不为空，某字段的取值，不填则默认为任何值时当前都不能为空
//===========================================================================================
    String[] notNullBoth() default {};
//============================================================================================
    int length() default 0;

    String[] method() default {};//对应的方法

    String[] valueIn() default {};//取值范围


}
