package com.github.black.hole.netty.simulate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hairen.long
 * @date 2020/7/14
 */
public abstract class AbstractCalculateHandlerContext implements CalculateHandlerContext {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final boolean bound;
    private final String name;

    protected AbstractCalculateHandlerContext prev;
    protected AbstractCalculateHandlerContext next;

    private CalculateHandlerPipeline pipeline;

    public AbstractCalculateHandlerContext(
            CalculateHandlerPipeline pipeline, String name, boolean bound) {
        this.pipeline = pipeline;
        this.name = name;
        this.bound = bound;
    }

    public CalculateHandlerPipeline pipeline() {
        return pipeline;
    }

    @Override
    public String handlerName() {
        return name;
    }

    @Override
    public <T> AbstractCalculateHandlerContext fireCalculate(T object) {
        invokeCalculate(findContext(), object);
        return this;
    }

    private AbstractCalculateHandlerContext findContext() {
        AbstractCalculateHandlerContext ctx = this;
        ctx = !ctx.bound ? ctx.next : ctx;
        return ctx;
    }

    static <T> void invokeCalculate(final AbstractCalculateHandlerContext next, T object) {
        next.invokeCalculate(object);
    }

    private <T> void invokeCalculate(T object) {
        try {
            handler().calculate(this, object);
        } catch (Exception e) {
            logger.error("评级计算链异常", e);
        }
    }
}
