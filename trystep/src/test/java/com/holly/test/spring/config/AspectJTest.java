package com.holly.test.spring.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/20 13:37
 */
@Aspect
public class AspectJTest {
    @Pointcut("execution (* *.test(..))")
    public void test(){

    }
    @Before("test()")
    public void beforeTest(){
        System.out.println("before test");
    }
    @After("test()")
    public void afterTest(){
        System.out.println("after test");
    }
    @Around("test()")
    public Object aroundTest(ProceedingJoinPoint p){
        System.out.println("around before");
        Object o = null;
        try {
            p.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("around after");
        return o;
    }
}
