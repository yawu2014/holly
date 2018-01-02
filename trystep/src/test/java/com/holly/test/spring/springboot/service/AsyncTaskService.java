package com.holly.test.spring.springboot.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/2 14:24
 */
@Service
public class AsyncTaskService {
    @Async
    public void executeAsyncTask(Integer i){
        System.out.println("async task :"+i+":"+Thread.currentThread().getName());
    }
    @Async
    public void executeAsyncTaskPlus(Integer i){
        System.out.println("async task plus:"+i+":"+Thread.currentThread().getName());
    }
}
