package com.github.black.hole.base.jdk.thread.state;

import static com.github.black.hole.base.jdk.thread.state.PrintUtils.print;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class ThreadStateNRRT {

    private static final Object lock = new Object();

    public static void main(String[] args) throws Exception {
        Thread thread1 =
                new Thread(
                        () -> {
                            synchronized (lock) {
                                System.out.println("thread1 entry lock");
                                print(
                                        "thread1 yield before",
                                        Thread.currentThread().getName(),
                                        Thread.currentThread().getState());
                                Thread.yield();
                                print(
                                        "thread1 yield after",
                                        Thread.currentThread().getName(),
                                        Thread.currentThread().getState());
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        "thread1");

        Thread thread2 =
                new Thread(
                        () -> {
                            synchronized (lock) {
                                System.out.println("thread2 entry lock");
                            }
                        },
                        "thread2");

        thread1.start();
        Thread.sleep(20);
        thread2.start();
        print("main", thread1.getName(), thread1.getState());
        print("main", thread2.getName(), thread2.getState());

        thread1.join();
        thread2.join();
    }
}
