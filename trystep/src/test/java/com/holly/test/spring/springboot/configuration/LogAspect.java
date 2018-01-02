package com.holly.test.spring.springboot.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 9:51
 */
@Aspect
@Component
public class LogAspect {
    @Pointcut("@annotation(com.holly.test.spring.springboot.configuration.Action)")
    public void annotationPointcut(){}
    @After("annotationPointcut()")
    public void after(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Action action = method.getAnnotation(Action.class);
        System.out.println("annotationName:"+action.name());
    }
    @Before("execution(* com.holly.test.spring.springboot.service.DemoMethodService.*(..))")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("method interceptor"+method.getName());
    }
}
