package com.holly.test;

import org.junit.Test;

/**
 * @Author liuyujian02
 * @Description
 * @Date create at 2018/4/19 下午7:24
 * @Modified by
 */
public class StringTest {
    @Test
    public void test2(){
        test1("11");
    }
    private void test1(Object obj){
        System.out.println(obj.toString());
        System.out.println(String.valueOf(obj));
        System.out.println((String) obj);
    }
}
