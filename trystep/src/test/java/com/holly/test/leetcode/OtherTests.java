package com.holly.test.leetcode;

import org.junit.Test;

import java.util.Arrays;

public class OtherTests {
    @Test
    public void testValue(){
        System.out.println(divide(2147483647, 3));
        System.out.println(Integer.MIN_VALUE);
    }

    public int divide(int dividend, int divisor) {
        if(dividend == Integer.MIN_VALUE){
            return Integer.MAX_VALUE;
        }
        if(divisor == 1){
            return dividend;
        }
        if(divisor == -1){
            if(dividend > Integer.MIN_VALUE){
                return -dividend;
            }
            return Integer.MAX_VALUE;
        }

        int ret = 0;
        boolean flag = false;
        if(dividend > 0 && divisor<0 || dividend<0 && divisor > 0){
            flag = true;
        }
        int a = dividend;
        if(dividend > 0){
            a = -dividend;
        }
        int b = divisor;
        if(divisor > 0){
            b = -divisor;
        }
        ret = div(a,b);
        if(flag){
            return -ret;
        }
        return ret;
    }
    private int div(int a, int b){
        if(a > b){
            return 0;
        }
        int count = 1;
        int tb = b;
        while((tb > Integer.MIN_VALUE /2 ) && (tb + tb) > a){
            tb = 2*tb;
            count = count + count;
        }
        return count +div(a-tb, b);
    }

    @Test
    public void testFindErr(){
        System.out.println(findErrorNum(new int[]{1, 2, 4, 3, 5, 6, 7}));
    }

    private int findErrorNum(int[] nums){
        if(nums.length< 4){
            return -1;
        }
        int[] tmp = new int[3];
        for(int i=1;i<4;i++){
            tmp[i-1] = nums[i] > nums[i-1]?1:0;
        }
        int sum = 0;
        sum = tmp[0] + tmp[1] + tmp[2];
        boolean asc = true;
        if(sum <= 1){
            asc = false;
        }
        for(int i=1;i< nums.length;i++){
            if(asc && nums[i] < nums[i-1]){
                return nums[i];
            }
            if(!asc && nums[i] > nums[i-1]){
                return nums[i];
            }
        }
        return 0;
    }
    @Test
    public void testNextPermutation(){
        int[] nums = new int[]{5,4,2,3,1};
        nextPermutation(nums);
        StringBuilder sb = new StringBuilder();
        for(int num:nums){
            sb.append(",").append(num);
        }
        System.out.println(sb.toString().substring(1));
    }
    private void nextPermutation(int[] nums){
        int i=nums.length - 2;
        while(i>=0 && nums[i+1] <= nums[i]){
            i--;
        }
        if(i>=0){
            int j = nums.length -1;
            while(j >= 0 && nums[j] <= nums[i]){
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i+1);
    }
    private void reverse(int[] nums, int start){
        int i = start, j = nums.length -1;
        while(i<j){
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    private void swap(int[] nums, int i,int j){
        int tmp = nums[j];
        nums[j] = nums[i];
        nums[i] = tmp;
    }

    private void printArray(int[] nums){
        StringBuilder sb = new StringBuilder();
        for(int num : nums){
            sb.append(",").append(num);
        }
        System.out.println(sb.substring(1));
    }
    @Test
    public void testSearchRange(){
        int[] nums = new int[]{1};
        int target = 1;
        Arrays.stream(searchRange(nums, target)).forEach(System.out::println);
        int[] result = new int[]{-1, -1};
        int left = searchRange2(nums, target , true);
        if(nums[left] != target || left == nums.length - 1){
            printArray(result);
        }
        int right = searchRange2(nums, target, false) - 1;
        result[0] = left;
        result[1] = right;
        printArray(result);
    }
    public int[] searchRange(int[] nums, int target) {
        int i=0,j = nums.length - 1, mid = (i+j) /2;
        boolean find = false;
        while(i < j){
            if(nums[mid] == target){
                find = true;
                break;
            } else if (nums[mid] > target){
                j = mid;
                mid = (i+j) / 2;
            } else if(nums[mid] <target){
                i = mid + 1;
                mid = (i+j) / 2;
            }
        }

        if(find){
            int max = mid+1, min= mid-1;
            while(min >= 0){
                if(nums[min] == target){
                    min--;
                } else {
                    min++;
                    break;
                }
            }
            while(max < nums.length){
                if(nums[max] == target){
                    max++;
                } else {
                    max--;
                    break;
                }
            }
            return new int[]{min, max};
        } else {
            return new int[]{-1, -1};
        }
    }


    private int searchRange2(int[] nums, int target, boolean left){
        int i =0,j=nums.length - 1;
        while(i<j){
            int mid = (i+j) /2 ;
            if(nums[mid] > target || (left && nums[mid] == target)){
                j = mid;
            } else {
                i = mid + 1;
            }
        }
        return i;
    }
    @Test
    public void testBinaryAdd(){
        System.out.println(addBinary("11", "1"));
    }
    public String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int aPos = a.length() - 1;
        int bPos = b.length() - 1;
        boolean mark = false;
        while(aPos >= 0 && bPos>=0){
            char aC = a.charAt(aPos--);
            char bC = b.charAt(bPos--);
            if('1' == aC && '1' == bC){
                if(mark){
                    sb.append(1);
                }else{
                    sb.append(0);
                }
                mark = true;
            } else if ('1' == aC || '1' == bC){
                if(mark){
                    sb.append(0);
                    mark = true;
                }else{
                    sb.append(1);
                }
            } else {
                if(mark){
                    sb.append(1);
                }else{
                    sb.append(0);
                }
                mark = false;
            }
        }
        if(aPos >=0){
            while(aPos>=0){
                sb.append(a.charAt(aPos--));
            }
        }
        if(bPos >=0){
            while(bPos>=0){
                sb.append(a.charAt(bPos--));
            }
        }
        return sb.reverse().toString();
    }
}
