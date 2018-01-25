package com.example.demo.model;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ParamChecks {

    ParamCheck[] value();


}
