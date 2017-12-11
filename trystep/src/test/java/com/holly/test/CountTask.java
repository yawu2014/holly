package com.holly.test;

import java.util.concurrent.*;

/**
 * @Author liuyj
 * @Description:
 * @date 2017/12/11 18:53
 */

public class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2;
    private int start;
    private int end;
    public CountTask(int start, int end){
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start)<THRESHOLD;
        if(canCompute){
            for(int i=0;i<end;i++){
                sum += i;
            }
        }else{
            int middle = (start+end)/2;
            CountTask leftTask = new CountTask(start,middle);
            CountTask rightTask = new CountTask(middle,end);
            leftTask.fork();
            rightTask.fork();

            int leftValue = leftTask.join();
            int rightValue = rightTask.join();
            sum = leftValue + rightValue;
        }
        return sum;
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask task = new CountTask(1,4);
        Future<Integer> result = forkJoinPool.submit(task);
        try{
            System.out.println(result.get());
        }catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
