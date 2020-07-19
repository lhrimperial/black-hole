package com.github.black.hole.design.behavior.chain;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public class Handler2 implements Handler {
    private Handler next;

    @Override
    public int handleRequest(int n) {
        if (n < 10) {
            return n * n;
        } else {
            if (next == null) {
                throw new NullPointerException("next 不能为空");
            }
            return next.handleRequest(n);
        }
    }

    @Override
    public void setNextHandler(Handler next) {
        this.next = next;
    }
}
