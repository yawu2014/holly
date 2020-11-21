package com.holly.test.leetcode;

public class ListNode<T> {
    public T val;
    ListNode next;
    ListNode(T x){val = x;}

    public ListNode(T val, ListNode next){
        this.val = val;
        this.next = next;
    }

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
