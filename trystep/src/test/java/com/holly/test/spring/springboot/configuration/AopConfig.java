package com.holly.test.spring.springboot.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 13:55
 */
@Configuration
@ComponentScan("com.holly.test.spring.springboot")
@EnableAspectJAutoProxy
public class AopConfig {
}
