package com.holly.test.spring.springboot.configuration;

import com.holly.test.spring.springboot.service.LinuxListService;
import com.holly.test.spring.springboot.service.ListService;
import com.holly.test.spring.springboot.service.WindowsListService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 14:38
 */
@Configuration
public class ConditionConfig {
    @Bean
    @Conditional(WindowsCondition.class)
    public ListService windowsListService(){
        return new WindowsListService();
    }

    @Bean
    @Conditional(LinuxCondition.class)
    public ListService linuxListService(){
        return new LinuxListService();
    }
}
