package com.holly.test.spring.springboot.configuration;

import com.holly.test.spring.springboot.service.FunctionService;
import com.holly.test.spring.springboot.service.UserFunctionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 9:36
 */
@Configuration
@ComponentScan("com.holly.test.spring.springboot")
public class JavaConfig {
    @Bean
    public FunctionService functionService(){
        return new FunctionService();
    }
    @Bean
    public UserFunctionService userFunctionService(FunctionService functionService){
        UserFunctionService userFunctionService = new UserFunctionService();
        userFunctionService.setFunctionService(functionService);
        return userFunctionService;
    }
}
