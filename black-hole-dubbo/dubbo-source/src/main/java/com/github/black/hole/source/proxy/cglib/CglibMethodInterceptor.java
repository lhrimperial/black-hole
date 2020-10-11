package com.github.black.hole.source.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class CglibMethodInterceptor implements MethodInterceptor {

    /**
     * cglib 为目标类生产一个代理类，生产两个索引文件，一个是目标类的索引，一个是代理类的索引，共生产三个文件
     * 执行代理类方法时，如果代理类设置了拦截器，则则调用拦截器的intercept方法
     * MethodProxy 方法代理类有两个方法，invoke和invokeSuper，这两个方法中通过索引类来调用目标方法
     * invoke方法中使用的是目标类索引类，调用的还是代理类的目标方法，形成循环调用知道栈溢出
     * invokeSuper方法中使用的是代理类的索引类，调用的是代理类的CGLIB$sayHello$1方法，该方法中调用类父类的目标方法
     *
     * @param o
     * @param method
     * @param objects
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("++++++before " + methodProxy.getSuperName() + "++++++");
        System.out.println(method.getName());
        Object result = methodProxy.invoke(o, objects);
        System.out.println("++++++after " + methodProxy.getSuperName() + "++++++");

        return result;
    }
}
