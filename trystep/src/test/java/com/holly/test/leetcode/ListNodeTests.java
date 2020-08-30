package com.holly.test.leetcode;

import org.junit.Test;

import static com.holly.test.leetcode.ListNode.printListNode;

public class ListNodeTests {
    @Test
    public void testReserve(){
        int[] example = new int[]{1,2,3,4,5};

        ListNode pointer = new ListNode(-1);
        ListNode p = pointer;
        for(int i : example){
            pointer.next = new ListNode(i);
            pointer = pointer.next;
        }
        printListNode(p.next);
//        printListNode(reverse1(p.next));
        printListNode(reverse2(p.next));
    }

    private ListNode reverse1(ListNode head){
        if(head.next == null){
            return head;
        }
        ListNode p = reverse1(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    private ListNode reverse2(ListNode head){
        ListNode prev = null;
        ListNode curr = head;
        while(curr != null){
            ListNode nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        return prev;
    }
}
