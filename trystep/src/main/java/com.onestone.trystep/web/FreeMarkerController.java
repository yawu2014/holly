package com.onestone.trystep.web;

import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author liuyujian
 * @Description:
 * @date ${date} ${time}
 */
@Controller
public class FreeMarkerController {
    @RequestMapping("helloFreeMarker")
    public String helloFreeMarker(Map<String,Object> map){
        map.put("hello"," from "+getClass().getCanonicalName()+" hello FreeMaker");
        return "/helloFreeMak";
    }
}
