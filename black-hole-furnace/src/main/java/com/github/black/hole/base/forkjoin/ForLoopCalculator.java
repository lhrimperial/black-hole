package com.github.black.hole.base.forkjoin;

/**
 * @author hairen.long
 * @date 2020/9/22
 */
public class ForLoopCalculator implements Calculator {

    @Override
    public long sumUp(long[] numbers) {
        long total = 0;
        for (long i : numbers) {
            total += i;
        }
        return total;
    }
}
