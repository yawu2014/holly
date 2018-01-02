package com.holly.test.spring.springboot.service;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 13:47
 */
@Service
public class AwareService implements BeanNameAware,ResourceLoaderAware {
    private String beanName;
    private ResourceLoader resourceLoader;
    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    public void outputResult() throws IOException {
        System.out.println("beanName:"+this.beanName);
        Resource resource = resourceLoader.getResource("classpath:test.properties");
        System.out.println(IOUtils.toString(resource.getInputStream(), Charset.defaultCharset()));
    }
}
