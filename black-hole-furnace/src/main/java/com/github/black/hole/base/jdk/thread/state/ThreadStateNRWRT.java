package com.github.black.hole.base.jdk.thread.state;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.black.hole.base.jdk.thread.state.PrintUtils.print;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class ThreadStateNRWRT {
    /** 锁 */
    private static final Object lock = new Object();

    private static Lock reentrantLock = new ReentrantLock();
    private static Condition condition = reentrantLock.newCondition();

    public static void main(String[] args) {
        objectWait();
        //        lockCondition();
    }

    private static void lockCondition() {
        // 展示线程
        Thread showThread =
                new Thread(
                        () -> {
                            // 等待
                            reentrantLock.lock();
                            try {
                                try {
                                    condition.await();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } finally {
                                reentrantLock.unlock();
                            }
                            // RUNNABLE
                            print(
                                    Thread.currentThread().getName(),
                                    Thread.currentThread().getState());
                        },
                        "showThread");
        // NEW
        print(showThread.getName(), showThread.getState());
        showThread.start();
        // RUNNABLE
        print(showThread.getName(), showThread.getState());
        // 循环读取展示线程状态，直到读到展示线程状态为WAITING，才让辅助线程唤醒等待线程。
        while (true) {
            if (showThread.getState() == Thread.State.WAITING) {
                // WAITING
                print(showThread.getName(), showThread.getState());
                break;
            }
        }

        // 唤醒showThread
        reentrantLock.lock();
        try {
            condition.signal();
        } finally {
            reentrantLock.unlock();
        }

        try {
            showThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 线程执行完毕打印状态。
        print(showThread.getName(), showThread.getState());
    }

    private static void objectWait() {
        // 展示线程
        Thread showThread =
                new Thread(
                        () -> {
                            // 等待
                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            // RUNNABLE
                            print(
                                    Thread.currentThread().getName(),
                                    Thread.currentThread().getState());
                        },
                        "showThread");
        // NEW
        print(showThread.getName(), showThread.getState());
        showThread.start();
        // RUNNABLE
        print(showThread.getName(), showThread.getState());
        // 循环读取展示线程状态，直到读到展示线程状态为WAITING，才让辅助线程唤醒等待线程。
        while (true) {
            if (showThread.getState() == Thread.State.WAITING) {
                print(showThread.getName(), showThread.getState());
                break;
            }
        }
        // main线程唤醒showThread
        synchronized (lock) {
            lock.notify();
        }

        try {
            showThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 线程执行完毕打印状态。
        print(showThread.getName(), showThread.getState());
    }
}
