package com.holly.test.spring.springboot.service;

import com.holly.test.spring.springboot.configuration.Action;
import org.springframework.stereotype.Service;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 14:03
 */
@Service
public class DemoAnnotationService {
    @Action(name="DemoApplication Inject add operation")
    public void add(){
        System.out.println(this.getClass().getSimpleName()+":add()");
    }
}
