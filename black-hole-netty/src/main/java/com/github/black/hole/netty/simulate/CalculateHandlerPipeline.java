package com.github.black.hole.netty.simulate;


/**
 * @author hairen.long
 * @date 2020/7/14
 */
public interface CalculateHandlerPipeline extends CalculateHandlerInvoker {

    /**
     * add handler
     *
     * @param handler
     * @return
     */
    CalculateHandlerPipeline addLast(CalculateHandler handler);

    /**
     * add handler
     *
     * @param name
     * @param handler
     * @return
     */
    CalculateHandlerPipeline addLast(String name, CalculateHandler handler);
}
