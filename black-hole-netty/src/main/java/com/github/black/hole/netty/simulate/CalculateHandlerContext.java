package com.github.black.hole.netty.simulate;


/**
 * @author hairen.long
 * @date 2020/7/12
 */
public interface CalculateHandlerContext extends CalculateHandlerInvoker {

    /**
     * 获取处理类
     *
     * @return
     */
    CalculateHandler handler();

    /**
     * name
     *
     * @return
     */
    String handlerName();
}
