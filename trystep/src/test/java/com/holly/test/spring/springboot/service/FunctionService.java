package com.holly.test.spring.springboot.service;

import com.holly.test.spring.springboot.configuration.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 使用JavaConfig进行注入配置
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 9:35
 */
public class FunctionService {
    private String word;

    public String sayHello(String word){
        return "Hello "+word+"!";
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
