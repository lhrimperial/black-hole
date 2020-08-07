package com.github.black.hole.netty.simulate;


/**
 * @author hairen.long
 * @date 2020/7/14
 */
public class DefaultCalculateHandlerContext extends AbstractCalculateHandlerContext {

    private CalculateHandler handler;

    public DefaultCalculateHandlerContext(
            CalculateHandlerPipeline pipeline, String name, CalculateHandler handler) {
        super(pipeline, name, false);
        this.handler = handler;
    }

    @Override
    public CalculateHandler handler() {
        return handler;
    }
}
