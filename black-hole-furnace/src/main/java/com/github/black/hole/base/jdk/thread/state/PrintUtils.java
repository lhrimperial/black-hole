package com.github.black.hole.base.jdk.thread.state;

/**
 * @author hairen.long
 * @date 2021/9/1
 */
public class PrintUtils {
    private static final String stringFormat = "%s:%s";
    private static final String stringPFormat = "%s %s:%s";

    public static void print(String position, String threadName, Thread.State state) {
        System.out.println(String.format(stringPFormat, position, threadName, state));
    }

    public static void print(String threadName, Thread.State state) {
        System.out.println(String.format(stringFormat, threadName, state));
    }

    public static void print(String threadName, String msg) {
        System.out.println(String.format(stringFormat, threadName, msg));
    }
}
