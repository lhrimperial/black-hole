package com.github.black.hole.design.behavior.chain;

/**
 * @author hairen.long
 * @date 2020/6/18
 */
public interface Handler {
    /**
     * handle
     *
     * @param n
     * @return
     */
    int handleRequest(int n);

    /**
     * set handle
     *
     * @param next
     */
    void setNextHandler(Handler next);
}
