package com.github.black.hole.basic.proxy;

import java.lang.reflect.Proxy;

/**
 * @author hairen.long
 * @date 2021/8/14
 */
public class ProxyMain {

    public static void main(String[] args) {
        System.getProperties().setProperty("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");
        ProxyInvocationHandler handler = new ProxyInvocationHandler(new ProxyInterfaceImpl());
        ProxyInterface proxy =
                (ProxyInterface)
                        Proxy.newProxyInstance(
                                ProxyMain.class.getClassLoader(),
                                handler.getTarget().getClass().getInterfaces(),
                                handler);
        String response = proxy.sayHello("andy");
        System.out.println(response);
    }
}
