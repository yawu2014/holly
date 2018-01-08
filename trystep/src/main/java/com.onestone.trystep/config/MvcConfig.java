package com.onestone.trystep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/3 9:35
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    /**
     * 自定义解析资源文件匹配
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor());
    }

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new RequestInterceptor();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("helloFreeMarker").setViewName("/helloFreeMak");
    }
    @Bean
    public MvcMessageConverter mvcMessageConverter(){
        return new MvcMessageConverter();
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mvcMessageConverter());
    }
}
