package com.holly.test;

import org.junit.Test;

import java.util.concurrent.atomic.*;

public class AtomicTest {
    @Test
    public void testAtomicInteger(){
        AtomicInteger ai = new AtomicInteger(5);
        AtomicLong al = new AtomicLong();
        AtomicBoolean ab = new AtomicBoolean();
    }
    @Test
    public void testAtomicArrayInteger(){
        int[] resArr = new int[]{1,2};
        AtomicIntegerArray aia = new AtomicIntegerArray(resArr);
        aia.addAndGet(1,2);
        aia.getAndAdd(1,3);
        System.out.println(aia.get(1));
        System.out.println(resArr[1]);

        AtomicLongArray ala = new AtomicLongArray(null);
        AtomicReferenceArray ara = new AtomicReferenceArray(null);
    }
    @Test
    public void testAtmoicReference(){
        AtomicReference ar = new AtomicReference();

//        AtomicReferenceFieldUpdater arf = new AtomicReferenceFieldUpdater() ;
        AtomicMarkableReference amr = new AtomicMarkableReference(null,true);
    }
    /**
     * @Author liuyj
     * @Description 原则更新字段
     * @Date 2017/12/11_22:42
     */
    @Test
    public void testAtomicField(){
        AtomicIntegerFieldUpdater aifu;
        AtomicLongFieldUpdater alfu;
        AtomicStampedReference asr;
    }
}
