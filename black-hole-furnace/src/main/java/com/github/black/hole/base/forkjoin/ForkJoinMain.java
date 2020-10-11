package com.github.black.hole.base.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * @author hairen.long
 * @date 2020/9/22
 */
public class ForkJoinMain {

    public static void main(String[] args) {
        forLoopWayTest();
        executorWayTest();
        forkJoinPoolTest();
        javaStreamTest();
    }

    public static void javaStreamTest() {
        Instant start = Instant.now();
        long result = LongStream.rangeClosed(0, 10000000L).parallel().reduce(0, Long::sum);
        Instant end = Instant.now();
        System.out.println("stream 耗时：" + Duration.between(start, end).toMillis() + "ms");
        // 打印结果
        System.out.println("stream 结果为：" + result);
    }

    public static void forkJoinPoolTest() {
        long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();

        Instant start = Instant.now();
        Calculator calculator = new ForkJoinCalculator();
        long result = calculator.sumUp(numbers);
        Instant end = Instant.now();
        System.out.println("forkjoin 耗时：" + Duration.between(start, end).toMillis() + "ms");
        // 打印结果
        System.out.println("forkjoin 结果为：" + result);
    }

    public static void executorWayTest() {
        long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();

        Instant start = Instant.now();
        Calculator calculator = new ExecutorServiceCalculator();
        long result = calculator.sumUp(numbers);
        Instant end = Instant.now();
        System.out.println("线程池耗时：" + Duration.between(start, end).toMillis() + "ms");
        // 打印结果
        System.out.println("线程池结果为：" + result);
    }

    public static void forLoopWayTest() {
        long[] numbers = LongStream.rangeClosed(1, 10000000).toArray();

        Instant start = Instant.now();
        Calculator calculator = new ForLoopCalculator();
        long result = calculator.sumUp(numbers);
        Instant end = Instant.now();
        System.out.println("for循环耗时：" + Duration.between(start, end).toMillis() + "ms");

        System.out.println("for循环结果为：" + result);
    }
}
