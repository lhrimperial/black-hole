package com.github.black.hole.netty;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hairen.long
 * @date 2020/10/20
 */
public class AppTest {

    private static final AtomicInteger childIndex = new AtomicInteger();

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            System.out.println(getNext());
        }
    }


    public static Integer getNext(){
       return childIndex.getAndIncrement() & 15;
    }

    private static boolean isPowerOfTwo(int val) {

        return (val & -val) == val;
    }
}
