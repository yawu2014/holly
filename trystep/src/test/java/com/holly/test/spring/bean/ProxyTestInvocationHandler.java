package com.holly.test.spring.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author liuyj
 * @Description:
 * 创建代理步骤
 * 1.构造函数,将代理对象传入
 * 2.invoke,实现了代理的逻辑
 * 3.getProxy获取代理对象
 * @date 2017/12/20 17:10
 */
public class ProxyTestInvocationHandler implements InvocationHandler {
    private Object target;
    public ProxyTestInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        Object res = method.invoke(target,args);
        System.out.println("after invoke");
        return res;
    }
    public Object getProxy(){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),target.getClass().getInterfaces(),this);
    }
}
