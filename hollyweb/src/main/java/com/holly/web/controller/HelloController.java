package com.holly.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 16:46
 */
@Controller
public class HelloController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
