package com.github.black.hole.base.jdk.jvm;

/**
 * @author hairen.long
 * @date 2020/10/27
 */
public class ThreadStack {
    private Object lock1 = new Object();
    private Object lock2 =new Object();

    private void fun1(){
        synchronized (lock1) {
            fun2();
        }
    }

    private void fun2(){
        synchronized (lock2){
            while (true){
                System.out.println();
            }
        }
    }

    public static void main(String[] args){
        new ThreadStack().fun1();
    }
}
