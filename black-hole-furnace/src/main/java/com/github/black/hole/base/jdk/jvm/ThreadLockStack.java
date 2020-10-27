package com.github.black.hole.base.jdk.jvm;

/**
 * @author hairen.long
 * @date 2020/10/27
 */
public class ThreadLockStack {

    private static Object lock = new Object();

    public static void main(String[] args) throws Exception {
        new Thread(
                        () -> {
                            synchronized (lock) {
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        "线程1")
                .start();

        new Thread(
                        () -> {
                            synchronized (lock) {
                                try {
                                    Thread.sleep(1000000L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        "线程2")
                .start();

        new Thread(
                        () -> {
                            synchronized (lock) {
                                System.out.println("hello");
                            }
                        },
                        "线程3")
                .start();
    }
}
