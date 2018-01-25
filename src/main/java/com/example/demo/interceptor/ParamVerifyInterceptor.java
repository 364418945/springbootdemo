package com.example.demo.interceptor;

import com.example.demo.model.ParamVerify;
import com.example.demo.model.RequestVerify;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 自动验证带ParamVerify注解的方法
 */
@Aspect
@Component
@Slf4j
public class ParamVerifyInterceptor {


    @Pointcut("@annotation(paramVerify)")
    public void paramVerifyPointcut(ParamVerify paramVerify){
    }

    @Before("paramVerifyPointcut(paramVerify)")
    public void doVerifyBefore(JoinPoint joinPoint, ParamVerify paramVerify){
        Class[] targetParameters = paramVerify.value();
        for (int i = 0; i < targetParameters.length; i++) {
            Class<?> targetPar = targetParameters[i];
            Object[] objects = joinPoint.getArgs();
            for (int i1 = 0; i1 < objects.length; i1++) {
                Object object = objects[i1];
                if(object.getClass() == targetPar){
                    //执行参数验证
                    new RequestVerify(object,joinPoint.getSignature().getName()).checkRequest();
                    break;
                }
            }
        }
    }
}
