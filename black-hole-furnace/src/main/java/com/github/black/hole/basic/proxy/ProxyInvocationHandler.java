package com.github.black.hole.basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author hairen.long
 * @date 2021/8/14
 */
public class ProxyInvocationHandler implements InvocationHandler {
    private ProxyInterface target;

    public ProxyInvocationHandler(ProxyInterface target) {
        this.target = target;
    }

    public ProxyInterface getTarget(){
        return target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke");
        String response = (String) method.invoke(target, args);
        System.out.println("after invoke");
        return response;
    }
}
