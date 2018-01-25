package com.example.demo.aspect;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 切面编程
 */
@Aspect
@Component
public class HttpAspect {
    public static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.example.demo.HelloController.hello(..))")
    public void commonPointcut(){

    }

    @Pointcut("execution(public * com.example.demo.HelloController.hello1(..))")
    public void commonPointcut1(){

    }

    @Before("commonPointcut()")
    public  void log(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();

        logger.info("requestUrl {}",httpServletRequest.getRequestURL());
        logger.info("requestUri {}",httpServletRequest.getRequestURI());
//        System.out.println(1111);
    }

    @After("commonPointcut1()")
    public  void log1(){
//        System.out.println(2222);
    }


    @AfterReturning(pointcut="commonPointcut1()",returning = "object")
    public void log2(Object object){
        logger.info("response {}",object);
    }
}
