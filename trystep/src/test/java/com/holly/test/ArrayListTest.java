package com.holly.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class ArrayListTest {
    @Test
    public void testSplittor(){
        List<String> arrs = new ArrayList<String>();
        for( int i=0;i<10;i++){
            char c = (char)('a'+i);
            arrs.add(String.valueOf(c));
        }
        Spliterator<String> a = arrs.spliterator();
        Spliterator<String> b = null;
        Spliterator<String> c = null;
        Spliterator<String> d = null;
        System.out.println("a:"+enmuSpliter(a));
//        System.out.println("b:"+enmuSpliter(b = a.trySplit()));
//        System.out.println("b:"+enmuSpliter(c = a.trySplit()));
//        System.out.println("b:"+enmuSpliter(d = a.trySplit()));
    }
    private <T> String enmuSpliter(Spliterator<T> st){
        if(st != null){
            st.forEachRemaining(str->{
                //System.out.println(str);
            });
        }
        return "is end";
    }
}
