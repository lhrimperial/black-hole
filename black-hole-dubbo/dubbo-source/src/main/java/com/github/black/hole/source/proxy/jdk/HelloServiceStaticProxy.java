package com.github.black.hole.source.proxy.jdk;

import com.github.black.hole.source.service.HelloService;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class HelloServiceStaticProxy implements HelloService {

    private HelloService target;

    public HelloServiceStaticProxy(HelloService target) {
        this.target = target;
    }

    @Override
    public void sayHello(String name) {
        System.out.println("StaticProxy before sayHello......");
        target.sayHello(name);
    }

    @Override
    public void sayBye(String name) {
        System.out.println("StaticProxy before sayBye......");
        target.sayBye(name);
    }
}
