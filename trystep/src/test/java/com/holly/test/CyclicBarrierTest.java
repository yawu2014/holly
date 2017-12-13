package com.holly.test;

import org.junit.Test;
import sun.rmi.runtime.RuntimeUtil;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/12 16:52
 */

public class CyclicBarrierTest {
    static CyclicBarrier c = new CyclicBarrier(2);
    static CyclicBarrier c1 = new CyclicBarrier(3);
    static CyclicBarrier c2 = new CyclicBarrier(2, new Runnable() {
        @Override
        public void run() {
            System.out.println("c3");
        }
    });
    static CyclicBarrier c3 = new CyclicBarrier(2);
    @Test
    public void testC() throws BrokenBarrierException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread:"+Thread.currentThread());
            }
        }).start();
        c.await();
        System.out.println("Thread:main:"+Thread.currentThread());
    }

    @Test
    public void testC1() throws BrokenBarrierException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread:"+Thread.currentThread());
            }
        }).start();
        c1.await();
        System.out.println("Thread:main:"+Thread.currentThread());
    }

    @Test
    public void testC2() throws BrokenBarrierException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread:"+Thread.currentThread());
                    c2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        System.out.println("Thread:main:"+Thread.currentThread());
        c2.await();
        System.out.println("END");
    }
    @Test
    public void testC3(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Thread:"+Thread.currentThread());
                    c3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        thread.interrupt();
        System.out.println("Thread:main:"+Thread.currentThread());

        try {
            c3.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("END");
        System.out.println("c3 result:"+c3.isBroken());
    }
}
