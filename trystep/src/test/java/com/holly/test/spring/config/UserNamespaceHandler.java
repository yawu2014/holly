package com.holly.test.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/15 9:42
 *
 * http\://www.holly.com/schema/user=com.holly.test.spring.config.UserNamespaceHandler    Spring.handlers
 * http\://www.holly.com/schema/user.xsd=META-INF/Spring-test.xsd                         Spring.schemas
 */
public class UserNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("user",new UserBeanDefinitionParser());
    }
}
