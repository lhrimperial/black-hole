package com.github.black.hole.base.forkjoin;

/**
 * @author hairen.long
 * @date 2020/9/22
 */
public interface Calculator {
    /**
     * 把传进来的所有numbers 做求和处理
     *
     * @param numbers
     * @return 总和
     */
    long sumUp(long[] numbers);
}
