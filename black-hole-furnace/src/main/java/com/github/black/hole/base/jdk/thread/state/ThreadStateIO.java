package com.github.black.hole.base.jdk.thread.state;


import java.util.Scanner;

import static com.github.black.hole.base.jdk.thread.state.PrintUtils.print;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class ThreadStateIO {
    public static void main(String[] args) {
        Thread thread =
                new Thread(
                        () -> {
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("waiting enter sth: ");
                            String s = scanner.next();
                            System.out.println(Thread.currentThread().getName() + ": " + s);
                        },"IO-Thread");

        print(thread.getName(), thread.getState());
        thread.start();
        print(thread.getName(), thread.getState());
    }
}
