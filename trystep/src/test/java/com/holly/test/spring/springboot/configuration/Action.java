package com.holly.test.spring.springboot.configuration;

import java.lang.annotation.*;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 9:47
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Action {
    String name();
}
