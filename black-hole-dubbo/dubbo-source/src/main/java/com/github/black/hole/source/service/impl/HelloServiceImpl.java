package com.github.black.hole.source.service.impl;


import com.github.black.hole.source.service.HelloService;

/**
 * @author hairen.long
 * @date 2020/10/10
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }

    @Override
    public void sayBye(String name) {
        System.out.println("Bye " + name);
    }
}
