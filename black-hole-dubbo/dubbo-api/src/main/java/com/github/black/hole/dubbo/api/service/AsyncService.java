package com.github.black.hole.dubbo.api.service;

import java.util.concurrent.CompletableFuture;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
public interface AsyncService {

    /**
     * say hello
     *
     * @param name
     * @return
     */
    CompletableFuture<String> sayHello(String name);
}
