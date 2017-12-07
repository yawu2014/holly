package com.holly.test;

import java.util.concurrent.TimeUnit;

/**
 * @Author liuyj
 * @Description
 * @create 2017-12-06 14:11
 */
public class WaitNotify {
    static boolean flag = true;
    static Object lock = new Object();

    public static void main(String[] args) throws Exception{
        Thread waitThread = new Thread(new Wait(),"Waiting Thread");
        waitThread.start();
        TimeUnit.SECONDS.sleep(1);

        Thread notify = new Thread(new Notify(),"Notify Thread");
        notify.start();
    }
    static class Wait implements Runnable{
        @Override
        public void run() {
//            System.out.println("XXXXXXXXXXXXXXX");//wait返回后是往下执行
            synchronized (lock){
                while(flag){
                    try{
                        System.out.println(Thread.currentThread()+" flag is true");;
                        lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread()+" flag is false");
            }
        }
    }
    static class Notify implements Runnable{
        @Override
        public void run() {
            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock");
                lock.notifyAll();
                flag = false;
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (lock){
                System.out.println(Thread.currentThread()+" hold lock agin");
            }
        }
    }
}
