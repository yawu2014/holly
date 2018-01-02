package com.holly.test.spring.springboot.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 14:04
 */
@Service
public class DemoMethodService {
    @Value("xxxxx")
    private String othername;

    public void add(){
        System.out.println(this.othername+":"+this.getClass().getCanonicalName()+":add()");
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
    }
}
