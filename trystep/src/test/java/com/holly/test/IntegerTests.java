package com.holly.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @Author liuyujian02
 * @Description
 * @Date create at 2018/3/4 下午9:30
 * @Modified by
 */
public class IntegerTests {
    public static void main(String[] args) {
        Integer a = new Integer(123);
        Integer b = new Integer(123);
        System.out.println(a == b);
    }
    @Test
    public void testBigDecimal(){
        BigDecimal bd = new BigDecimal("1");
        BigDecimal db2 = new BigDecimal("3");
        System.out.println(bd.divide(db2,10,BigDecimal.ROUND_DOWN));
    }

    @Test
    public void testRound(){
        BigDecimal bg = new BigDecimal("10.1111111");
        System.out.println(bg.setScale(3,BigDecimal.ROUND_HALF_UP).toString());
        System.out.println(bg.toString());
    }
}
