package com.github.black.hole.netty.simulate;

/**
 * @author hairen.long
 * @date 2020/8/7
 */
public abstract class AbstractCalculateHandler implements CalculateHandler {

    @Override
    public void calculate(CalculateHandlerContext handlerContext, Object object) throws Exception {
        if (object instanceof GradeCalculateContext) {
            calculate0((GradeCalculateContext) object);
            handlerContext.fireCalculate(object);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * calc
     *
     * @param context
     */
    protected abstract void calculate0(GradeCalculateContext context);
}
