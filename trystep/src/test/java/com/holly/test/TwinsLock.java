package com.holly.test;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);
    private static final class Sync extends AbstractQueuedSynchronizer{
        Sync(int count){
            if(count <= 0){
                throw new IllegalArgumentException("count must larger than zero");
            }
            setState(2);
        }

        @Override
        protected int tryAcquireShared(int reduceCount) {
            for(;;){
                int current = getState();
                int newCount = current - reduceCount;
                if(newCount < 0 || compareAndSetState(current,newCount)){
                    return newCount;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for(;;){
                int current = getState();
                int newCount = current + arg;
                if(compareAndSetState(current,newCount)){
                    return true;
                }
            }
        }
    }
    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Test
    public void test() throws InterruptedException {
        final Lock lock = new TwinsLock();
        class Worker extends Thread{
            @Override
            public void run() {
                while(true){
                    lock.lock();
                    try{
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("current Thread:"+Thread.currentThread());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        final class Worker2 extends Thread{
            @Override
            public void run() {
                while(true){
                    System.out.println("current Thread2:"+Thread.currentThread());
                }
            }
        }
        for(int i=0;i<10;i++){
            Worker w = new Worker();
//            w.setDaemon(true);
            w.start();
        }
        for(int i=0;i<10;i++){
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
