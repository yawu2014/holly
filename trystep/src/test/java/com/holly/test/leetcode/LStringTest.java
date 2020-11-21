package com.holly.test.leetcode;

import org.junit.Test;

import java.util.*;

public class LStringTest {
    @Test
    public void testStringBuilder(){
        String testValue = "abccdef";
        System.out.println(lengthOfLongestSubstring(testValue));
        System.out.println(lengthOfLongestSubstring2(testValue));
    }
    public int lengthOfLongestSubstring(String s) {
        List<String> sb = new ArrayList<>();
        int max = 0;
        for(int i=0;i<s.length();i++){
            String str = s.substring(i, i+1);
            if(!sb.contains(str)){
                sb.add(str);
            } else {
                if(sb.size() > max){
                    max = sb.size();
                }
                int start = sb.indexOf(str);
                if(start + 1 < sb.size()) {
                    sb = sb.subList(sb.indexOf(str) + 1, sb.size());
                } else {
                    sb = new ArrayList<>();
                }
                sb.add(str);
            }
        }
        if(sb.size() > max){
            max = sb.size();
        }
        return max;
    }

    public int lengthOfLongestSubstring2(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                occ.remove(s.charAt(i - 1));
            }
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    @Test
    public void generateParenthesis() {
        int n = 3;
        List<String> ret = new ArrayList<>();
        generate(ret, new StringBuilder(), 0,0,n);
        printList(ret);
    }
    private void generate(List<String> ans,StringBuilder str, int open, int close, int max){
        if(str.length() == 2*max){
            ans.add(str.toString());
            return;
        }
        if(open < max){
            str.append("(");
            generate(ans, str, open+1, close, max);
            str.deleteCharAt(str.length() -1);
        }
        if(close < open){
            str.append(")");
            generate(ans, str, open, close+1, max);
            str.deleteCharAt(str.length() -1);
        }
    }

    public static void printList(List list){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<list.size();i++){
            sb.append(list.get(i));
            if(i == list.size() -1){
                sb.append("]");
            }
            sb.append(",");
        }
        System.out.println(sb.toString());
        Comparator co = new Comparator<Integer>(){

            @Override
            public int compare(Integer o1, Integer o2) {
                return 0;
            }
        };
    }

    @Test
    public void testMatch(){
        System.out.println(strStr("mississippi", "issip"));
        List<Integer> ans = new ArrayList<>();
        ans.add(0, 1);
    }
    public int strStr(String haystack, String needle) {
        if(needle==null || needle.length() ==0){
            return 0;
        }
        for(int i=0;i<=haystack.length() - needle.length();i++){
            for(int j=0;j<needle.length();j++){
                if(haystack.charAt(i+j) != needle.charAt(j)){
                    break;
                } else {
                    if(j == needle.length() -1){
                        return i;
                    }
                }

            }
        }
        return -1;
    }

    @Test
    public void testMultiply(){
        System.out.println(multiply("3", "2"));
        System.out.println(multiply("123", "456"));
    }
    public String multiply(String num1, String num2) {
        String ans = "";
        if(isZero(num1) || isZero(num2)){
            return "0";
        }
        String pad="";
        for(int i=num2.length() - 1;i >=0;i--){
            int num = num2.charAt(i) - '0';
            String tmpValue = "";
            int scale  = 0;
            for(int j=num1.length()-1;j>=0;j--){
                int curValue = num*(num1.charAt(j) - '0')+scale;
                scale = curValue / 10;
                tmpValue = (curValue%10)+tmpValue;
            }
            if(scale >0){
                tmpValue = scale + tmpValue;
            }
            tmpValue+=pad;
            pad+="0";
            ans = add(ans, tmpValue);
        }
        return ans;
    }
    /**
     判断是否为0
     */
    private boolean isZero(String num){
        if("0".equals(num)){
            return true;
        }
        return false;
    }
    private String add(String num1, String num2){
        int i=1,j=1,scale=0,ii=num1.length(),jj=num2.length();
        String ans = "";
        while(ii-i>=0 && jj-j >= 0){
            int curValue = (num1.charAt(ii-i) - '0') + (num2.charAt(jj - j) - '0') + scale;
            scale = curValue / 10;
            ans = (curValue % 10) + ans;
            i++;
            j++;
        }
        while(ii - i >= 0){
            int curValue = (num1.charAt(ii-i) - '0') + scale;
            scale = curValue / 10;
            ans = (curValue%10) + ans;
            i++;
        }
        while(jj - j >= 0){
            int curValue = (num2.charAt(jj - j) - '0') + scale;
            scale = curValue / 10;
            ans = (curValue%10) + ans;
            j++;
        }
        if(scale > 0){
            return scale + ans;
        }
        return  ans;
    }
}
