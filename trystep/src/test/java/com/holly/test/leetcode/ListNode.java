package com.holly.test.leetcode;

public class ListNode<T> {
    T val;
    ListNode next;
    ListNode(T x){val = x;}

    @Override
    public String toString() {
        return String.valueOf(val);
    }

    public static void printListNode(ListNode head){
        StringBuilder sb = new StringBuilder();
        while(head != null){
            sb.append(",").append(head.val);
            head = head.next;
        }
        System.out.println(sb.length() > 0 ? sb.toString().substring(1): sb.toString());
    }
}
