package com.github.black.hole.base.algorithm.practice.chapter6;

/**
 * @author hairen.long
 * @date 2020/11/11
 */
public class PalindromeLinkedList {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1};
        Node head = buildLinked(arr);
        boolean result = palindrome(head);
        System.out.println("result = " + result);
    }

    public static boolean palindrome(Node head) {
        if (head == null) {
            return false;
        }
        if (head.next == null) {
            return true;
        }
        Node p = head;
        Node q = head;
        while (q.next != null && q.next.next != null) {
            p = p.next;
            q = q.next.next;
        }
        print(head);
        System.out.println("p=" + p.data);
        System.out.println("q=" + q.data);
        Node rightLinked = null;
        Node leftLined = null;
        boolean result = false;
        if (q.next == null) {
            // 奇数 中点不用比较
            rightLinked = p.next;
            leftLined = inverseLinkList(head, p).next;
            result = compareLinked(leftLined, rightLinked);
        } else {
            // 偶数
            rightLinked = p;
            leftLined = inverseLinkList(head, p);
            result = compareLinked(leftLined, rightLinked);
        }
        return result;
    }

    private static boolean compareLinked(Node left, Node right) {
        System.out.println("left:");
        print(left);
        System.out.println("right:");
        print(right);
        Node l = left;
        Node r = right;
        boolean flag = true;
        while (l != null && r != null) {
            if (l.data == r.data) {
                l = l.next;
                r = r.next;
                continue;
            } else {
                flag = false;
                break;
            }
        }
        return flag;
    }

    private static Node buildLinked(int[] arr) {
        if (arr.length > 0) {
            Node head = new Node(arr[0]);
            Node curr = head;
            for (int i = 1, len = arr.length; i < len; i++) {
                curr.next = new Node(arr[i]);
                curr = curr.next;
            }
            return head;
        }
        return null;
    }

    private static void print(Node head) {
        while (head != null) {
            System.out.print(head.data + "\t");
            head = head.next;
        }
        System.out.println();
    }

    public static Node inverseLinkList(Node head, Node tail) {
        System.out.println("source:");
        print(head);
        Node pre = null;
        Node curr = head;
        while (curr != tail) {
            Node node = curr.next;
            curr.next = pre;

            pre = curr;
            curr = node;
        }
        System.out.println("inverse:");
        print(curr);
        return curr;
    }

    private static class Node {
        private int data;
        private Node next;

        public Node(int data) {
            this.data = data;
        }
    }
}
