package com.onestone.trystep.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/3 11:50
 */
@Controller
public class SSEController {
    @RequestMapping(value="/push",produces = "text/event-stream")
    @ResponseBody
    public void push(){

    }
}
