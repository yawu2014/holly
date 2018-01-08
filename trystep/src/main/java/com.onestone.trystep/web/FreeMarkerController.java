package com.onestone.trystep.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Author liuyujian
 * @Description:
 * @date ${date} ${time}
 */
@Controller
public class FreeMarkerController {
    private static final Logger logger = LoggerFactory.getLogger("FreeMarkerController");
    @RequestMapping("helloFreeMarker")
    public String helloFreeMarker(Map<String,Object> map){
        map.put("hello"," from "+getClass().getCanonicalName()+" hello FreeMaker");
        return "/helloFreeMak";
    }
    @RequestMapping("advice")
    public String getSomething(@ModelAttribute("extra") String extraMsg, String like){
        logger.error("solve msg:"+extraMsg);
        throw new IllegalArgumentException("Error:msg"+extraMsg);
    }
}
