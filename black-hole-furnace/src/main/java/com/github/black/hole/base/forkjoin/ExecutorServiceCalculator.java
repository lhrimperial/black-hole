package com.github.black.hole.base.forkjoin;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author hairen.long
 * @date 2020/9/22
 */
public class ExecutorServiceCalculator implements Calculator {

    private int parallism;
    private ExecutorService pool;
    private static ThreadFactory calculateThreadFactory =
            new ThreadFactoryBuilder().setNameFormat("ExecutorServiceCalculator-pool").build();

    public ExecutorServiceCalculator() {
        // CPU的核心数 默认就用cpu核心数了
        parallism = Runtime.getRuntime().availableProcessors();
        pool =
                new ThreadPoolExecutor(
                        parallism,
                        parallism,
                        10,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(),
                        calculateThreadFactory,
                        new ThreadPoolExecutor.AbortPolicy());
    }

    /** 处理计算任务的线程 */
    private static class SumTask implements Callable<Long> {
        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        public Long call() {
            long total = 0;
            for (int i = from; i <= to; i++) {
                total += numbers[i];
            }
            return total;
        }
    }

    @Override
    public long sumUp(long[] numbers) {
        List<Future<Long>> results = new ArrayList<>();
        // 把任务分解为 n 份，交给 n 个线程处理   4核心 就等分成4份呗
        // 然后把每一份都扔个一个SumTask线程 进行处理
        int part = numbers.length / parallism;
        for (int i = 0; i < parallism; i++) {
            // 开始位置
            int from = i * part;
            // 结束位置
            int to = (i == parallism - 1) ? numbers.length - 1 : (i + 1) * part - 1;

            // 扔给线程池计算
            results.add(pool.submit(new SumTask(numbers, from, to)));
        }

        // 把每个线程的结果相加，得到最终结果 get()方法 是阻塞的
        // 优化方案：可以采用CompletableFuture来优化  JDK1.8的新特性
        long total = 0L;
        for (Future<Long> f : results) {
            try {
                total += f.get();
            } catch (Exception ignore) {
            }
        }

        return total;
    }
}
