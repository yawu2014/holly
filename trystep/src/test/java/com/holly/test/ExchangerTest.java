package com.holly.test;

import org.junit.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/12 17:39
 */

public class ExchangerTest {
    private static final Exchanger<String> exgr = new Exchanger<String>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    @Test
    public void testexgr1() throws InterruptedException {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String A = ":Flow A";
                    String b = exgr.exchange(A); exgr.exchange(A);
                    System.out.println(Thread.currentThread()+":"+b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String B = ":Flow B";
                    String a = exgr.exchange(B);
                    System.out.println(Thread.currentThread()+":"+a);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        TimeUnit.SECONDS.sleep(1);
        threadPool.shutdown();
    }
}
