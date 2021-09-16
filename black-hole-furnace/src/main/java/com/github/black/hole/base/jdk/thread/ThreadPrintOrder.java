package com.github.black.hole.base.jdk.thread;

import com.github.black.hole.base.jdk.juc.ReenterLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hairen.long
 * @date 2021/9/8
 */
public class ThreadPrintOrder {

    private static int count;
    private static Object obj = new Object();

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        test3();
    }

    public static void test3() {
        new Thread(
                        () -> {
                            while (count < 100) {
                                lock.lock();
                                try {
                                    System.out.println(
                                            Thread.currentThread().getName() + ":" + count++);
                                    condition.signal();
                                    condition.await();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    lock.unlock();
                                }
                            }
                        },
                        "偶线程")
                .start();
        new Thread(
                        () -> {
                            while (count < 100) {
                                lock.lock();
                                try {
                                    System.out.println(
                                            Thread.currentThread().getName() + ":" + count++);
                                    condition.signal();
                                    condition.await();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    lock.unlock();
                                }
                            }
                        },
                        "奇线程")
                .start();
    }

    public static void test2() {
        new Thread(
                        () -> {
                            while (count < 100) {
                                synchronized (obj) {
                                    System.out.println(
                                            Thread.currentThread().getName() + ":" + count++);
                                    obj.notify();
                                    try {
                                        obj.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        "偶线程")
                .start();

        new Thread(
                        () -> {
                            while (count < 100) {
                                synchronized (obj) {
                                    System.out.println(
                                            Thread.currentThread().getName() + ":" + count++);
                                    obj.notify();
                                    try {
                                        obj.wait();
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        "奇线程")
                .start();
    }

    public static void test1() {
        new Thread(
                        () -> {
                            while (count < 100) {
                                synchronized (obj) {
                                    if ((count & 1) == 1) {
                                        System.out.println(
                                                Thread.currentThread().getName() + ":" + count++);
                                    }
                                }
                            }
                        },
                        "奇线程")
                .start();

        new Thread(
                        () -> {
                            while (count < 100) {
                                synchronized (obj) {
                                    if ((count & 1) == 0) {
                                        System.out.println(
                                                Thread.currentThread().getName() + ":" + count++);
                                    }
                                }
                            }
                        },
                        "偶线程")
                .start();
    }
}
