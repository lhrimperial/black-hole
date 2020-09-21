package com.github.black.hole.sboot.simulate;

/**
 * @author hairen.long
 * @date 2020/7/14
 */
public class DefaultCalculateHandlerPipeline implements CalculateHandlerPipeline {

    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    private static final String HEAD_NAME = generateName0(HeadContext.class);
    private static final String TAIL_NAME = generateName0(TailContext.class);

    private AbstractCalculateHandlerContext head;
    private AbstractCalculateHandlerContext tail;

    public DefaultCalculateHandlerPipeline() {
        tail = new TailContext(this);
        head = new HeadContext(this);

        head.next = tail;
        tail.prev = head;
    }

    @Override
    public CalculateHandlerPipeline fireCalculate(Object object) {
        AbstractCalculateHandlerContext.invokeCalculate(head, object);
        return this;
    }

    @Override
    public CalculateHandlerPipeline addLast(CalculateHandler handler) {
        String name = generateName0(handler.getClass());
        addLast0(newContext(name, handler));
        return this;
    }

    @Override
    public CalculateHandlerPipeline addLast(String name, CalculateHandler handler) {
        addLast0(newContext(name, handler));
        return this;
    }

    private AbstractCalculateHandlerContext newContext(String name, CalculateHandler handler) {
        return new DefaultCalculateHandlerContext(this, name, handler);
    }

    private void addLast0(AbstractCalculateHandlerContext newCtx) {
        AbstractCalculateHandlerContext prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
    }

    private static String generateName0(Class<?> handlerType) {
        return simpleClassName(handlerType) + "#0";
    }

    public static String simpleClassName(Class<?> clazz) {
        String className = checkNotNull(clazz, "clazz").getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if (lastDotIdx > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }

    public static <T> T checkNotNull(T arg, String text) {
        if (arg == null) {
            throw new NullPointerException(text);
        }
        return arg;
    }

    final class HeadContext extends AbstractCalculateHandlerContext implements CalculateHandler {

        HeadContext(CalculateHandlerPipeline pipeline) {
            super(pipeline, HEAD_NAME, false);
        }

        @Override
        public void calculate(CalculateHandlerContext context, Object object) throws Exception {
            context.fireCalculate(object);
        }

        @Override
        public CalculateHandler handler() {
            return this;
        }
    }

    final class TailContext extends AbstractCalculateHandlerContext implements CalculateHandler {

        TailContext(CalculateHandlerPipeline pipeline) {
            super(pipeline, TAIL_NAME, true);
        }

        @Override
        public void calculate(CalculateHandlerContext context, Object object) throws Exception {}

        @Override
        public CalculateHandler handler() {
            return this;
        }
    }
}
