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
    @Test
    public void testDeleteNode(){
        int[] testValue = new int[]{1,2,3,4,5,6};
        ListNode node = initNodeList(testValue);
        printListNode(node);
        deleteNode(node);
        printListNode(node);
    }

    public void deleteNode(ListNode node, int cnt) {
        int n = 0;
        ListNode first = node;
        while(first != null){
            n++;
            first = first.next;
        }
        int pos = n / 2;
        ListNode ans = new ListNode(-1);
        ListNode cur = node;
        ans.next = node;
        int i = 1;
        while(i < pos){
            ans = ans.next;
            cur = cur.next;
            i++;
        }
        ans.next = cur.next;
    }
    public void deleteNode(ListNode node) {
        int n = 0;
        ListNode first = node;
        while(first != null){
            n++;
            first = first.next;
        }
        int pos = n / 2;
        ListNode ans = new ListNode(-1);
        ListNode cur = node;
        ans.next = node;
        int i = 1;
        while(i < pos){
            ans = ans.next;
            cur = cur.next;
            i++;
        }
        ans.next = cur.next;
    }

    private ListNode initNodeList(int[] testValue){
        ListNode first = new ListNode(-1);
        ListNode tmp = first;
        for(int value : testValue){
            ListNode node = new ListNode(value);
            first.next = node;
            first = first.next;
        }
        return tmp.next;
    }

    /**
     * 使用归并排序链表
     */
    @Test
    public void testSort(){
        int[] testValue = new int[]{4,2,1,3};
        ListNode node = initNodeList(testValue);
        ListNode l = sortList(node);
        printListNode(l);
    }

    public ListNode sortList(ListNode head) {
        if(head == null||head.next == null){
            return head;
        }
        ListNode t = head;
        int size = 0;
        while(t!=null){
            size++;
            t = t.next;
        }
        ListNode d = new ListNode(0, head);
        for(int len=1;len<size;len<<=1){
            ListNode pre = d;ListNode cur = d.next;
            while(cur!=null){
                ListNode head1=cur;
                for(int i=1;i<len&&cur.next != null;i++){
                    cur = cur.next;
                }
                ListNode head2 = cur.next;
                cur.next = null;
                cur = head2;
                for(int i=1;i<len&&cur!=null &&cur.next!=null;i++){
                    cur = cur.next;
                }
                ListNode next = null;
                if(cur != null){
                    next = cur.next;
                    cur.next = null;
                }
                pre.next = merge(head1, head2);
                while(pre.next != null){
                    pre = pre.next;
                }
                cur = next;;
            }
        }
        return d.next;
    }
    private ListNode merge(ListNode<Integer> head1, ListNode<Integer> head2){
        ListNode<Integer> t = new ListNode(-1);
        ListNode<Integer> f= t,n1 = head1, n2 = head2;
        while(n1!=null && n2 != null){
            if(n1.val <= n2.val){
                t.next = n1;
                n1 = n1.next;
            } else {
                t.next = n2;
                n2 = n2.next;
            }
            t = t.next;
        }
        if(n1 != null){
            t.next = n1;
        }
        if(n2 != null){
            t.next = n2;
        }
        return f.next;
    }
}
