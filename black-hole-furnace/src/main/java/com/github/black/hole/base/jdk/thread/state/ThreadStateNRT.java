package com.github.black.hole.base.jdk.thread.state;

import static com.github.black.hole.base.jdk.thread.state.PrintUtils.print;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class ThreadStateNRT {

    public static void main(String[] args) {
        Thread thread =
                new Thread(
                        () ->
                                // RUNNABLE
                                print(
                                        "3",
                                        Thread.currentThread().getName(),
                                        Thread.currentThread().getState()),
                        "ThreadStateNRT");
        // NEW
        print("1", thread.getName(), thread.getState());
        thread.start();
        print("2", thread.getName(), thread.getState());
        // 等待线程执行完毕。
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // TERMINATED
        print("4" + thread.getName(), thread.getState());
    }
}
