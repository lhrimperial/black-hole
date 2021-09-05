package com.github.black.hole.base.jdk.thread.state;

import static com.github.black.hole.base.jdk.thread.state.PrintUtils.print;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class ThreadStateNRBRT {
    /** 锁 */
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 辅助线程，制造synchronized状态。
        Thread assistantThread =
                new Thread(
                        () -> {
                            // 锁定一定时间
                            synchronized (lock) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        },"assistantThread");
        assistantThread.start();

        try {
            // 保证assistantThread先执行。
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread showThread =
                new Thread(
                        () -> {
                            synchronized (lock) {
                                print(
                                        Thread.currentThread().getName(),
                                        Thread.currentThread().getState());
                            }
                        },"showThread");
        // NEW
        print(showThread.getName(), showThread.getState());
        // start
        showThread.start();
        // RUNNABLE
        print(showThread.getName(), showThread.getState());
        // 因为无法判断显示线程何时执行，所以循环直到显示线程执行。
        while (true) {
            if (showThread.getState() == Thread.State.BLOCKED) {
                // BLOCKED
                print(showThread.getName(), showThread.getState());
                break;
            }
        }
        // 等待两个线程执行完毕。
        try {
            assistantThread.join();
            showThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 线程执行完毕打印状态。
        print(showThread.getName(), showThread.getState());
    }
}
