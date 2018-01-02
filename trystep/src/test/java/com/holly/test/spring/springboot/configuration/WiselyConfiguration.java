package com.holly.test.spring.springboot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 14:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Configuration
@ComponentScan("com.holly.test.spring.springboot")
public @interface WiselyConfiguration {
    String[] name() default {};
}
