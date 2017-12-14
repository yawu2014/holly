package com.holly.test;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/13 12:45
 */

public class Java8StreamTest {
    @Test
    public void testFilter(){
        List<Integer> lists = Lists.newArrayList();
        for(int i=0;i<10;i++){
            lists.add(i);
        }
        Integer res = lists.stream().filter(x->{return x == 11;}).findFirst().orElse(null);
        System.out.println(res);
    }
}
