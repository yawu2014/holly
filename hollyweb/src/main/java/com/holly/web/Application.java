package com.holly.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 18:37
 */
@SpringBootApplication
@ServletComponentScan
public class Application extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger("application");

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
