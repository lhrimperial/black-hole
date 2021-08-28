package com.github.black.hole.basic.proxy;

/**
 * @author hairen.long
 * @date 2021/8/14
 */
public class ProxyInterfaceImpl implements ProxyInterface {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
