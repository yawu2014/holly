package com.holly.test.leetcode;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ArrayTests {

    @Test
    public void getResult() throws InterruptedException {
        String board[] = new String[]{
                "   X O  O ",
                " X X    O ",
                "X  X    O ",
                "X    OX O ",
                "X   XO  O ",
                "X  X O  O ",
                "O  X O  O ",
                "     O  OX",
                "     O  O ",
                "   X XXXO "};
        System.out.println(board.length);
        System.out.println("res:"+tictactoe(board));
        Thread.sleep(1000000);
    }

    public String tictactoe(String[] board) {
        int size = board.length;
        char[][] chess = new char[size][size];
        for(int i=0;i < size; i++){
            String rowStr = board[i];
            for(int j=0; j<size;j++){
                chess[i][j] = rowStr.charAt(j);
            }
        }
        char corner1Char = chess[0][0];
        boolean corner1Success = true;
        char corner2Char = chess[0][size-1];
        boolean corner2Success = true;
        boolean hasCapcity = false;
        for(int i=0;i<size;i++){
            boolean rowSuccess = true;
            boolean columnSuccess = true;
            char rowStartChar = chess[i][0];
            
            char columnStartChar = chess[0][i];
            for(int j = 0;j<size;j++){
                if(chess[i][j] != rowStartChar){
                    rowSuccess = false;
                }
                if(chess[j][i] != columnStartChar){
                    columnSuccess = false;
                }
                if(chess[i][j] == ' '){
                    hasCapcity = true;
                }
            }
            if(rowSuccess){
                return String.valueOf(rowStartChar);
            }
            if(columnSuccess){
                return String.valueOf(columnStartChar);
            }
            if(chess[i][i] != corner1Char){
                corner1Success = false;
            }
            if(chess[i][size-1-i] != corner2Char){
                corner2Success = false;
            }
        }

        if(corner1Success){
            return String.valueOf(corner1Char);
        }
        if(corner2Success){
            return String.valueOf(corner2Char);
        }
        if(!hasCapcity){
            return "Draw";
        }
        return "Pending";
    }

    @Test
    public void findMedianSortedArrays() throws InterruptedException {
//        int[] nums1=new int[]{1,2,3,4,5,6,7,8,9,10},nums2 = new int[]{1,3,4,9};
        int[] nums1=new int[]{1,3},nums2 = new int[]{2};
        double res = getMean(nums1, nums2);
        System.out.println("res1:" + res);

        int size = nums1.length + nums2.length;
        if(size % 2 == 1){
            int k = size/2+1;
            int mean = getElement(nums1, nums2, k);
            System.out.println(mean);
        }else {
            int k1 = size/2, k2 = size/2 + 1;
            int mean = getElement(nums1, nums2, k1) + getElement(nums1,nums2, k2);
            System.out.println(mean / 2.0d);
        }
//        Thread.sleep(10000000);
    }
    private int getElement(int[] nums1, int[] nums2, int k){
        int index1 = 0, index2 = 0;
        while(true){
            if(index1 == nums1.length){
                return nums2[index2 + k -1];
            }
            if(index2 == nums2.length){
                return nums1[index1 + k -1];
            }
            if(k == 1){
                return Math.min(nums1[index1], nums2[index2]);
            }
            int t1 = Math.min(index1 + k/2 -1 , nums1.length - 1);
            int t2 = Math.min(index2 + k/2 -1 , nums2.length - 1);
            if(nums1[t1] >= nums2[t2]){
                k = (k - (t2 - index2 + 1));
                index2 = t2 + 1;
            }else if(nums1[t1] < nums2[t2]){
                k = (k - (t1 - index1 + 1));
                index1 = t1 + 1;
            }
        }
    }
    public double getMean(int[] nums1, int[] nums2){
        if(nums1.length > nums2.length){
            return getMean(nums2, nums1);
        }
        int end = nums1.length, start = 0;
        int m = nums1.length;
        int n = nums2.length;
        int totalLength = m+n;
        while(true){
            int i = (start + end) / 2 ;
            int j = (totalLength + 1) /2 - i;
            if(i>0 && j !=n && nums1[i-1] > nums2[j]){
                end = end - 1;
            } else if (j>0 && i != m && nums2[j-1] > nums1[i]){
                start = start + 1;
            } else {
                int maxLeft = 0;
                if(i == 0){
                    maxLeft = nums2[j - 1];
                }else if(j == 0){
                    maxLeft = nums1[i - 1];
                }else {
                    maxLeft = Math.max(nums1[i-1], nums2[j-1]);
                }

                if(totalLength % 2 == 1){
                    return maxLeft;
                } else {
                    int minRight = 0;
                    if(i == m){
                        minRight = nums2[j];
                    } else if (j == n){
                        minRight = nums2[i];
                    }else {
                        minRight = Math.min(nums1[i], nums2[j]);
                    }
                    return (maxLeft + minRight)/2.0d;

                }
            }
        }
    }
    @Test
    public void testString(){
        String str = "tattarrattat";
        System.out.println(longestPalindrome(str));
    }
    public String longestPalindrome(String s) {
        if(s.length() == 0){
            return "";
        }
        String result = s.substring(0,1);
        int count = 0;
        for(int i=0;i<s.length();i++){
            int counter1 = 0;
            for(int j=0;j<=i;j++){
                if(i-j < 0 || i+j>(s.length()-1) || s.charAt(i-j) != s.charAt(i+j)){
                    break;
                }
                counter1++;
            }
            int counter2 = 0;
            if(i+1<s.length() && s.charAt(i) == s.charAt(i+1)){
                counter2++;
                for(int j=1;j<=i;j++){
                    if(i-j <0 || i+1+j > (s.length()-1) || s.charAt(i-j) != s.charAt(i+1+j)){
                        break;
                    }
                    counter2++;
                }
            }
            if(counter1 > 0){
                counter1--;
            }
            if(counter1 > count){
                result = s.substring(i-counter1, i+counter1 + 1);
                count = counter1;
            }
            if(counter2 > count){
                result = s.substring(i-counter2+1, i+counter2+1);
            }

        }

        return result;
    }
    @Test
    public void testConvert(){
        System.out.println(convert2("LEETCODEISHIRING", 3));
    }
    public String convert(String s, int numRows) {
        char[][] cacheRows = new char[numRows][s.length()];
        int[] cacheRowCnt = new int[numRows];
        for(int si = 0;si < s.length();si++){
            int index = si % (numRows * 2 - 2);
            if(index < numRows){
                cacheRows[index][cacheRowCnt[index]] = s.charAt(si);
                cacheRowCnt[index]++;
            } else {
                int sIndex = 2 * numRows - 1 - index;
                cacheRows[sIndex][cacheRowCnt[sIndex]] = s.charAt(si);
                cacheRowCnt[sIndex]++;
            }
        }
        char[] cResult = new char[s.length()];
        int cRows = 0;
        for(int i=0;i<numRows; i++){
            System.arraycopy(cacheRows[i], 0, cResult, cRows, cacheRowCnt[i]);
            cRows += cacheRowCnt[i];
        }
        /*String result = "";
        for(int i=0;i<numRows; i++){
            char[] cTMp = new char[cacheRowCnt[i]];
            System.arraycopy(cacheRows[i], 0, cTMp, 0, cacheRowCnt[i]);
            result += new String(cTMp);
        }*/
        return new String(cResult);
    }
    public String convert2(String s, int numRows) {
        if(s.length() < numRows || numRows < 2){
            return s;
        }
        List<StringBuilder> cacheRows = new ArrayList<>();
        for(int i=0; i<numRows; i++){
            cacheRows.add(new StringBuilder());
        }
        for(int si = 0;si < s.length();si++){
            int index = si % (numRows * 2 - 2);
            if(index < numRows){
                cacheRows.get(index).append(s.charAt(si));
            } else {
                int sIndex = 2 * numRows - 2 - index;
                cacheRows.get(sIndex).append(s.charAt(si));
            }
        }
        String result = "";
        for(int i=0; i<numRows; i++){
            result += cacheRows.get(i).toString();
        }
        /*String result = "";
        for(int i=0;i<numRows; i++){
            char[] cTMp = new char[cacheRowCnt[i]];
            System.arraycopy(cacheRows[i], 0, cTMp, 0, cacheRowCnt[i]);
            result += new String(cTMp);
        }*/
        return result;
    }
    @Test
    public void testArray(){
        List<List<Integer>> ret = new ArrayList<>();
        ret.add(new ArrayList<>());
        System.out.println(ret);
        int a = 0;
        testArg(a++);
    }
    private void testArg(int a){
        System.out.println(a);
    }

    @Test
    public void testMatchSearchTree(){
        int[] postorder = new int[]{4, 8, 6, 12, 16, 14, 10};
        System.out.println(matchSearchTree2(postorder));
        System.out.println(matchSearchTree(postorder, 0, postorder.length - 1));
    }
    private boolean matchSearchTree(int[] postorder, int start, int end){
        if(start >= end){
            return true;
        }
        int i = start;
        int value = postorder[end];
        while(postorder[i] < value) i++;
        int m = i;
        while(postorder[i] > value) i++;
        return end == i && matchSearchTree(postorder, i, m-1) && matchSearchTree(postorder, m, end -1);
    }

    private boolean matchSearchTree2(int[] postorder){
        Stack<Integer> stack = new Stack<>();
        int root = Integer.MAX_VALUE;
        for(int i= postorder.length - 1;i>=0;i--){
            if(postorder[i] > root) return false;
            while(!stack.isEmpty() && stack.peek() > postorder[i]){
                root = stack.pop();
            }
            stack.add(postorder[i]);
        }
        return true;
    }
    @Test
    public void testNear(){
        int[] array = new int[]{1,1,1,1};
        System.out.println(threeSumClosest(array, 100));
        System.out.println("s".substring(1));
    }
    public int threeSumClosest(int[] nums, int target) {
        int size = nums.length;

        Arrays.sort(nums);
        int ret = 10^4;
        for(int i=0;i<size;i++){
            if(i > 0 && nums[i] == nums[i-1]){
                continue;
            }
            int j = i + 1;
            int z = size - 1;
            while(j < z){
                int value = nums[i]+nums[j]+nums[z];
                if(value == target){
                    return target;
                }
                if(Math.abs(value - target) < Math.abs(ret - target)){
                    ret = value;
                }
                if(value > target){
                    int z0 = z -1 ;
                    while(j < z0 && nums[z] == nums[z0]){
                        z0 = z0-1;
                    }
                    z = z0;
                } else {
                    int j0 = j+1;
                    while(j0 < z && nums[j0] == nums[j]){
                        j0++;
                    }
                    j = j0;
                }
            }

        }
        return ret;
    }
    @Test
    public void getnerateParenthis(){
        int n = 3;
        List<String> ans = new ArrayList<>();
        backtrack(ans, new StringBuilder(), 0, 0, n);
        ans.forEach(item -> System.out.println(item));
        System.out.println("================");
        ans = generateBrace(n);
        ans.forEach(item -> System.out.println(item));
    }
    private void backtrack(List<String> ans, StringBuilder sb, int open, int close, int max){
        if(sb.length() == max * 2){
            ans.add(sb.toString());
            return;
        }
        if(open < max){
            sb.append("(");
            backtrack(ans, sb, open + 1, close, max);
            sb.deleteCharAt(sb.length() - 1);
        }
        if(close < open){
            sb.append(")");
            backtrack(ans, sb, open , close+1, max);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    private List<String> generateBrace(int n){
        ArrayList[] cache = new ArrayList[100];
        ArrayList<String> ans = new ArrayList<>();
        if(n == 0){
            ans.add("");
        } else {
            for(int c = 0; c< n; c++){
                for(String left : generateBrace(c)){
                    for(String right: generateBrace(n -c -1))
                    ans.add("(" + left + ")" + right);
                }
            }
        }
        cache[n] = ans;
        return ans;
    }

    void printListNodeValue(ListNode head){
        while(head != null){
            System.out.println(head);
            head = head.next;
        }
    }
    @Test
    public void recruit(){
        int[] valueArray = new int[]{1,2,3,4,5};
        ListNode first = new ListNode(-1);
        ListNode tmp = first;
        for(int val:valueArray){
            first.next =  new ListNode(val);
            first = first.next;
        }
        printListNodeValue(tmp.next);
        System.out.println("================");
        System.out.println(reverseListNode(tmp.next));
    }
    private ListNode reverseListNode(ListNode head){
        if(head == null || head.next == null){
            return head;
        }

        ListNode secondNode = reverseListNode(head.next);
        return null;
    }



    @Test
    public void testNextBigger(){
        int[] testValue = new int[]{1, 5, 1};
        nextPermutation(testValue);
    }
    public void nextPermutation(int[] nums) {
        int i=nums.length - 2;
        while(i>=0 && nums[i +1] <= nums[i]){
            i--;
        }
        if(i >= 0){
            int j = nums.length - 1;
            while(j > i && nums[j] <= nums[i]){
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i+1);
    }
    private void swap(int[] nums,int i, int j){
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    private void reverse(int[] nums, int i){
        int j = nums.length -1;
        while(i<j){
            swap(nums, i, j);
            i++;
            j--;
        }
    }
}
