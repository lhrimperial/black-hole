package com.github.black.hole.base.jdk.thread.state;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.github.black.hole.base.jdk.thread.state.PrintUtils.print;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class ThreadStateNRTWRT {

    /** 锁 */
    private static final Object lockO = new Object();

    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        objectTimedWait();
        //        lockCondition();
    }

    private static void lockCondition() {
        // 展示线程
        Thread showThread =
                new Thread(
                        () -> {
                            // 等待
                            lock.lock();
                            try {
                                try {
                                    condition.await(1, TimeUnit.MILLISECONDS);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } finally {
                                lock.unlock();
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
        // 循环读取展示线程状态，直到读到展示线程状态为TIMED_WAITING。
        while (true) {
            if (Thread.State.TIMED_WAITING == showThread.getState()) {
                print(showThread.getName(), showThread.getState());
                break;
            }
        }
        try {
            showThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 线程执行完毕打印状态。
        print(showThread.getName(), showThread.getState());
    }

    private static void objectTimedWait() {
        Thread showThread =
                new Thread(
                        () -> {
                            // 等待
                            synchronized (lockO) {
                                try {
                                    lockO.wait(1);
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
        // 循环读取展示线程状态，直到读到展示线程状态为TIMED_WAITING。
        while (true) {
            // TIMED_WAITING
            if (showThread.getState() == Thread.State.TIMED_WAITING) {
                print(showThread.getName(), showThread.getState());
                break;
            }
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
