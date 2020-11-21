package com.holly.test.leetcode;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

    /**
     * 字符串计算
     */
    @Test
    public void testCalc(){
        String testValue = "3+2*2";
        System.out.println(calculate(testValue));
    }

    public int calculate(String s) {
        Stack<Integer> stack = new Stack<Integer>();
        int i=0, ans=0, n = s.length();
        while(i < n){
            char value = s.charAt(i);
            if(' ' == value){
                i++;
                continue;
            }
            if('+' == value || '-' ==value ||'*' ==value || '/' ==value){
                i++;
                while(i < n && s.charAt(i) == ' '){
                    i = i+1;
                }
            }
            int operand = 0;
            while(i < n && Character.isDigit(s.charAt(i))){
                operand = operand * 10 + s.charAt(i) - '0';
                i++;
            }
            int tmp = operand;
            if('*' == value){
                tmp = stack.pop() * operand;
            } else if('/' == value){
                tmp = stack.pop() / operand;
            } else if('-' == value){
                tmp = -operand;
            }
            stack.add(tmp);
        }
        while(!stack.isEmpty()){
            ans += stack.pop();
        }
        return ans;
    }

    @Test
    public void subStr(){
        String testValue = "aabaaba";
        System.out.println(repeatedSubstringPattern(testValue));
    }
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        for(int i=1;2*i < n;i++){
            if(n % i ==0){
                boolean match = true;
                for(int j = i;j<n;j++){
                    if(s.charAt(j) != s.charAt(j-i)){
                        match = false;
                        break;
                    }

                }
                if(match){
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    public void testRepet(){
        String testValue = "aabcccccaaa";
        System.out.println(compressString(testValue));
    }
    public String compressString(String s) {
        StringBuilder sb =new StringBuilder();
        int i=0;
        while(i<s.length()){
            char value = s.charAt(i);
            int cnt = 1;
            i++;
            while(i < s.length() && s.charAt(i) == value){
                cnt++;
                i++;
            }

            sb.append(value).append(cnt);

        }
        if(sb.length() > s.length()){
            return s;
        } else {
            return sb.toString();
        }
    }
    @Test
    public void testCache(){
        LRUCache cache = new LRUCache(2);
        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));
        cache.put(3, 3);    // 该操作会使得关键字 2 作废
        System.out.println(cache.get(2));
        cache.put(4, 4);    // 该操作会使得关键字 1 作废
        System.out.println(cache.get(1));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
    }
    class LRUCache {
        private HashMap<Integer, Node> nodeMap;
        private int capacity;
        private Node head;
        private Node tail;
        public LRUCache(int capacity) {
            nodeMap = new HashMap<>();
            head = new Node();
            tail = new Node();
            head.next = tail;
            head.pre = null;
            tail.pre = head;
            tail.next = null;
            this.capacity = capacity;
        }

        public int get(int key) {
            Node node = nodeMap.get(key);
            if(node == null){
                return -1;
            }
            removeNode(node);
            addNode(node);
            return node.val;
        }

        public void put(int key, int value) {
            Node node = nodeMap.get(key);
            if(node == null){
                if(nodeMap.size() == capacity){
                    Node last = tail.pre;
                    removeNode(last);
                    nodeMap.remove(last.key);
                }
                node = new Node();
                node.val = value;
                node.key = key;
                addNode(node);
            }else {
                node.val = value;
                removeNode(node);
                addNode(node);
            }
            nodeMap.put(key, node);
        }

        private void removeNode(Node node){
            Node pre = node.pre;
            node.next.pre = pre;
            pre.next = node.next;
            node.pre = null;
            node.next = null;
        }
        private void addNode(Node node){
            Node next = head.next;
            node.next = next;
            head.next = node;
            node.pre = head;
            next.pre = node;
        }

        class Node{
            int key;
            int val;
            Node pre;
            Node next;
        }
    }

    @Test
    public void testString(){
        System.out.println(longestPalindrome("bb"));
    }
    public String longestPalindrome(String s) {
        if(s == null || s.length() == 0){
            return s;
        }

        int start = 0, end = 0;
        for(int i=0;i<s.length();i++){
            int ans1 = calc(s, i, i);
            int ans2 = calc(s, i, i+1);
            int ans  = Math.max(ans1, ans2);
            if(ans > (end - start)){
                start = i - (ans - 1) /2;
                end = i+ ans/2;
            }
        }
        return s.substring(start, end+1);
    }
    private int calc(String s, int left, int right){
        int l = left, r= right;
        while(l >= 0 && r<s.length() && s.charAt(l) == s.charAt(r)){
            l--;
            r++;
        }
        return r - l - 1;
    }
    static InheritableThreadLocal<Integer> tl = new InheritableThreadLocal<>();
    @Test
    public void testThreadLocal(){

        Thread.currentThread();
        tl.set(1);
        new Thread(() -> {
            System.out.println("sub" + tl.get());
        }).start();
        System.out.println(tl.get());
    }
    @Test
    public void testDeRepeat(){
        int[] testValue = new int[]{1,1,2};
        permuteUnique(testValue);
    }

    boolean[] vis;

    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        List<Integer> perm = new ArrayList<Integer>();
        vis = new boolean[nums.length];
        Arrays.sort(nums);
        backtrack(nums, ans, 0, perm);
        return ans;
    }

    public void backtrack(int[] nums, List<List<Integer>> ans, int idx, List<Integer> perm) {
        if (idx == nums.length) {
            ans.add(new ArrayList<Integer>(perm));
            return;
        }
        for (int i = 0; i < nums.length; ++i) {
            if (vis[i] || (i > 0 && nums[i] == nums[i - 1] && !vis[i - 1])) {
                continue;
            }
            perm.add(nums[i]);
            vis[i] = true;
            backtrack(nums, ans, idx + 1, perm);
            vis[i] = false;
            perm.remove(idx);
        }
    }
    ExecutorService threadPool = Executors.newSingleThreadExecutor();
    @Test
    public void testFuture(){
        Future future = threadPool.submit(() -> {});
        future.cancel(true);
        int[] nums = new int[10];
        System.out.println(nums.length);
    }
}
