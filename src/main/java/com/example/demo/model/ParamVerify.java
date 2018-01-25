package com.example.demo.model;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamVerify {


    Class[] value() default {};

}
