package com.holly.test.testDate;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author liuyujian02
 * @Description
 * @Date create at 2018/4/20 下午12:07
 * @Modified by
 */
public class testDate {
    @Test
    public void timestamp2dateFormat(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date(1524196449639L)));
    }
}
