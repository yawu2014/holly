package com.holly.test.spring.config;

import com.holly.bean.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/15 9:37
 */

public class UserBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    protected Class getBeanClass(Element element){
        return User.class;
    }
    protected void doParser(Element ele, BeanDefinitionBuilder bean){
        String userName = ele.getAttribute("userName");
        String email = ele.getAttribute("email");
        if(StringUtils.hasText(userName)){
            bean.addPropertyValue("userName",userName);
        }
        if(StringUtils.hasText(email)){
            bean.addPropertyValue("email",email);
        }
    }
}
