package com.github.black.hole.netty.simulate;

/**
 * @author hairen.long
 * @date 2020/7/14
 */
public interface CalculateHandlerInvoker {

    /**
     * fire calculate
     *
     * @param object
     * @return
     */
    <T> CalculateHandlerInvoker fireCalculate(T object);
}
