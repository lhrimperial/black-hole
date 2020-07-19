package com.github.black.hole.gateway.protocol;

import com.github.black.hole.gateway.entity.Datagram;

/**
 * @author hairen.long
 * @date 2020/7/19
 */
public interface ProtocolHandler<T> {

    /**
     * 处理请求
     *
     * @param context
     * @return
     */
    Datagram<String> handleRequest(T context);

    /**
     * 处理响应
     *
     * @param context
     */
    void handleResponse(T context);
}
