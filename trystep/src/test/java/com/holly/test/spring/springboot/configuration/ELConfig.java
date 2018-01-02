package com.holly.test.spring.springboot.configuration;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 10:47
 */
@Configuration
@ComponentScan("com.holly.test.spring.springboot")
@PropertySource("classpath:test.properties")
public class ELConfig {
    @Value("normal")
    private String normal;
    @Value("#{systemProperties['os.name']}")
    private String osName;
    @Value("#{ T(java.lang.Math).random() * 100.0}")
    private double randomNumber;
    @Value("#{demoMethodService.othername}")
    private String word;
    @Value("classpath:test.properties")
    private Resource file;
    @Value("http://www.baidu.com")
    private Resource url;
    @Value("${book.name}")
    private String bookName;

    @Autowired
    private Environment environment;

    /**
     * 此处的对象是PropertySourcesPlaceholderConfigurer
     * @return
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig(){
        return new PropertySourcesPlaceholderConfigurer();
    }
    public void outputResource(){
        try{
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(randomNumber);
            System.out.println(word);
            System.out.println(IOUtils.toString(file.getInputStream(), Charset.defaultCharset()));
            System.out.println(IOUtils.toString(url.getInputStream(), Charset.defaultCharset()));
            System.out.println(bookName);
            System.out.println(environment.getProperty("book.author"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
