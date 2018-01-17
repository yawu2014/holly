package com.holly.test.alogrithm;

import org.junit.Test;

/**
 * @Author liuyj
 * @Description:
 * @date 2018/1/17 9:43
 */

public class SortTest {
    private int[] arr = new int[]{33,22,11,10,11,22,33,44,55};
    private void printOrder(int[] arr){
        for(int v: arr){
            System.out.print(v+":");
        }
    }
    /**
     * 插入排序
     */
    @Test
    public void insertSort(){
        int length = arr.length;
        for(int i=0;i<length;i++){
            for(int j=i;j<length;j++){
                if(arr[j]<arr[i]){
                    int value = arr[i];
                    arr[i] = arr[j];
                    arr[j] = value;
                }
            }
        }
        printOrder(arr);
    }
    @Test
    public void insertOrder2(){
        int length = arr.length;
        for(int i=1;i<length;i++){
            int value = arr[i];
            int pos = i-1;
            while(pos >= 0 && arr[pos]>value){
                arr[pos+1] = arr[pos];
                pos -= 1;
            }
            arr[pos+1] = value;
        }
        printOrder(arr);
    }

    /**
     * 测试中值排序
     */
    @Test
    public void testSort(){
//        patitionSort(arr,0,arr.length - 1);
        quickSort(arr,0,8);
        printOrder(arr);
    }
    public void quickSort(int[] arr,int left,int right){
        if(left < right){
            int p = partitionSort2(arr,left,right);
            partitionSort2(arr,left,p-1);
            partitionSort2(arr,p+1,right);
        }
    }
    private int partitionSort2(int[] arr,int left,int right){
        int piovt = (left+right)/2;
        int value = arr[piovt];
        arr[piovt] = arr[right];
        arr[right] = value;
        int store = left;
        for(int i=left;i<right;i++){
            if(arr[i] < value){
                int tmp = arr[i];
                arr[i] = arr[store];
                arr[store] = tmp;
                store++;
            }
        }
        arr[right] = arr[store];
        arr[store] = value;
        return store;
    }
    /**
     * 中值排序
     */
    public void patitionSort(int[] arr,int left,int right) {
        printOrder(arr);
        System.out.println("-----");
        if (left < right) {
            int medium = (left + right) / 2;
            int piovt = medium;
            int value = arr[right];
            arr[right] = arr[piovt];
            arr[piovt] = value;

            int i = left;
            int j = right - 1;
            while (j > i) {
                while (i <= right && arr[i] < arr[right]) {
                    i++;
                }
                while (j >=0 && arr[j] > arr[right]) {
                    j--;
                }
                if(j > 0) {
                    int tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
            //j+1有可能等于right
            if(i == j && arr[j] > arr[right]){
                int value2 = arr[j];
                arr[j] = arr[right];
                arr[right] = value2;
            }else {
                int value2 = arr[j + 1];
                arr[j + 1] = arr[right];
                arr[right] = value2;
                patitionSort(arr, left, j);
                patitionSort(arr, j + 2, right);
            }
        }
    }
    /**
     * 选择排序,每次选出最大的元素
     */
    @Test
    public void selectSort(){
        for(int i=arr.length - 1; i>-1; i--){
            int pos = selectMax(arr,0,i);//缩小范围,i确定i位置的数的大小
            int tmp = arr[i];
            arr[i] = arr[pos];
            arr[pos] = tmp;
        }
        printOrder(arr);
    }

    /**
     * 选出该序列中最大的值
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private int selectMax(int[] arr,int left,int right){
        int max = left;
        if(left < right){
            for(int i=left;i<=right;i++){
                if(arr[i]>arr[max]){
                    max = i;
                }
            }
        }
        return max;
    }

    /**
     * 堆排序
     */
    public void heapSort(){
        int length = arr.length;
        int mid = length/2;
        for(int i=mid; i>-1; i--){
            heapify(arr,i,length);
        }
        for(int k=length-1;k>-1;k--){
            int tmp = arr[0];
            arr[0] = arr[k];
            arr[k] = tmp;
            heapify(arr,0,k);
        }
    }
    private void heapify(int arr[],int idx,int max){
        int left = idx *2 +1;
        int right = idx*2 +1;
        int largest = idx;
        if(left < max && arr[left] > arr[idx]){
            largest = left;
        }else{
            largest = idx;
        }
        if(right < max && arr[right] > arr[largest]){
            largest = right;
        }
        if(largest != idx){
            int tmp = arr[largest];
            arr[largest] = arr[idx];
            arr[idx] = tmp;
        }
    }
}
