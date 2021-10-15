package com.github.black.hole.algorithm;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hairen.long
 * @date 2021/1/21
 */
public class AlgorithmMain {

    /**
     * 判断奇偶数：奇数（x & 1 == 1）、偶数（x & 1 == 0）
     *
     * <p>清零最低位的1: y = x & (x - 1)
     *
     * <p>获取最低位的1: y = x & -x
     *
     * <p>清零：0 = x & ~x
     *
     * <p>将x最右边的n位清零：x & (~0 << n)
     *
     * <p>获取x的第n位值（0或者1）：(x >> n) & 1
     *
     * <p>将x的第n位置为1: x | (1 << n)
     *
     * <p>将x的第n位置为0: x & (~(1<<n))
     *
     * <p>将x的最高位至n位（含）清零：x & ((1<<n) - 1)
     *
     * <p>将第n位至0位（含）清零：x & (~((1 << (n + 1)) - 1))
     */
    public static void main(String[] args) {
        int x = 40;
        int digitBit = x & (-x);
        printf(digitBit);
        printf(digitBit - 1);
        int digit = Integer.bitCount(digitBit - 1);
        printf(digit);
        printf(x);
        int y = x ^ (1 << 2);
        printf(y);
        int z = x | (1 << 2);
        printf(z);

        TreeMap map = new TreeMap();
    }

    public static void source() {
        Map<String, String> map = new HashMap<>();
        map = new ConcurrentHashMap<>();
        map.put("1", "1");
        ThreadFactory calculateThreadFactory =
                new ThreadFactoryBuilder().setNameFormat("ExecutorServiceCalculator-pool").build();

        ThreadPoolExecutor pool =
                new ThreadPoolExecutor(
                        5,
                        10,
                        10,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(),
                        calculateThreadFactory,
                        new ThreadPoolExecutor.AbortPolicy());
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }

    private void test() {
        try (FileInputStream is = new FileInputStream(new File(""))) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
        }
    }

    public static void printf(int x) {
        System.out.println(Integer.toBinaryString(x));
    }
}
