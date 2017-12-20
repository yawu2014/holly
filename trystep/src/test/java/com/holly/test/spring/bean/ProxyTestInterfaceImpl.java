package com.holly.test.spring.bean;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/20 17:09
 */

public class ProxyTestInterfaceImpl implements ProxyTestInterface {
    @Override
    public void add() {
        System.out.println(ProxyTestInterfaceImpl.class.getSimpleName()+"add");
    }
}
