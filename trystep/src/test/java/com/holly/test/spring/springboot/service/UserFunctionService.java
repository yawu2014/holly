package com.holly.test.spring.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 使用JavaConfig进行注入配置
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 9:56
 */
public class UserFunctionService {
    FunctionService functionService;

    public String say(String msg){
        return functionService.sayHello(msg);
    }

    public FunctionService getFunctionService() {
        return functionService;
    }

    public void setFunctionService(FunctionService functionService) {
        this.functionService = functionService;
    }
}
