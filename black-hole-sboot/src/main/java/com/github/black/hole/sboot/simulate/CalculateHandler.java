package com.github.black.hole.sboot.simulate;

/**
 * @author hairen.long
 * @date 2020/7/12
 */
public interface CalculateHandler {

    /**
     * 计算
     *
     * @param handlerContext
     * @param object
     * @throws Exception
     */
    void calculate(CalculateHandlerContext handlerContext, Object object) throws Exception;
}
