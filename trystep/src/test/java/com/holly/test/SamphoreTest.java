package com.holly.test;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/12 17:16
 */

public class SamphoreTest {
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPoll = Executors.newFixedThreadPool(THREAD_COUNT);
    private AtomicInteger ai = new AtomicInteger(0);
    private static Semaphore s = new Semaphore(10);
    private CountDownLatch c = new CountDownLatch(20);
    @Test
    public void testS(){
        for(int i=0;i<THREAD_COUNT;i++){
            threadPoll.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println("saveData"+ai.getAndIncrement()+"---inqueue"+s.getQueueLength()+"__"+s.availablePermits());
                        TimeUnit.SECONDS.sleep(2);
                        s.release();
                        c.countDown();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        try {
            c.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPoll.shutdown();
    }
}
