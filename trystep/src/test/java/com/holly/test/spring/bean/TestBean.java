package com.holly.test.spring.bean;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/20 13:36
 */

public class TestBean {
    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }
    public void test(){
        System.out.println(getTestStr());
    }
}
