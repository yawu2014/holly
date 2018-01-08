package com.onestone.trystep.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/3 9:53
 */
@ControllerAdvice
public class MvcControllerConfig {
    private static final Logger logger = LoggerFactory.getLogger("MVCControllerConfig");
    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception e, WebRequest request){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error",e.getMessage());
        logger.error("errorcontent:"+e.getMessage());
        return modelAndView;
    }
    @ModelAttribute
    public void addAttribute(Model model){
        logger.info("set Attribute");
        model.addAttribute("extra","extra message");
    }
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        logger.debug("set web data Binder");
        webDataBinder.setDisallowedFields("id");
    }
}
