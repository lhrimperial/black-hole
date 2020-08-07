package com.github.black.hole.netty.simulate;

/**
 * @author hairen.long
 * @date 2020/7/12
 */
public interface CalculateHandler<T> {

    /**
     * 计算
     *
     * @param handlerContext
     * @param object
     * @throws Exception
     */
    void calculate(CalculateHandlerContext handlerContext, T object) throws Exception;
}
