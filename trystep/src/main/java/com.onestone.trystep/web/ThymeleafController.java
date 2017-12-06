package com.onestone.trystep.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author liuyujian
 * @Description: 测试Thymeleaf使用
 * @date ${date} ${time}
 */
@Controller
public class ThymeleafController {
    @RequestMapping("   ")
    public String helloThymeleaf(Map<String,Object> map){
        map.put("hello","from "+getClass().getSimpleName()+" helloHtml");
        return "/helloHtml";
    }

}
