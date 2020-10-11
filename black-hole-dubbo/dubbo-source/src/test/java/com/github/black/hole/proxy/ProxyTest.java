package com.github.black.hole.proxy;

import com.github.black.hole.source.proxy.jdk.HelloServiceDynamicProxy;
import com.github.black.hole.source.proxy.jdk.HelloServiceStaticProxy;
import com.github.black.hole.source.proxy.jdk.ProxyGeneratorUtils;
import com.github.black.hole.source.service.HelloService;
import com.github.black.hole.source.service.impl.HelloServiceImpl;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class ProxyTest {

    @Test
    public void generateProxyClass() {
        String path = "\\extra\\proxy\\$Proxy11.class";
        ProxyGeneratorUtils.writeProxyClassToHardDisk(path);
    }

    @Test
    public void dynamicProxyTest1() {
        HelloServiceDynamicProxy helloProxyHandler = new HelloServiceDynamicProxy(new HelloServiceImpl());
        HelloService proxy = (HelloService) helloProxyHandler.getProxy();
        proxy.sayHello("json");
        proxy.sayBye("json");
    }

    @Test
    public void dynamicProxyTest() throws Exception {
        //生成$Proxy0的class文件
        //System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        /**
         * 第一种
         */
        //获取动态代理类
        Class proxyClass = Proxy.getProxyClass(HelloService.class.getClassLoader(), HelloService.class);
        //获取代理类的构造函数，并传入参数类型InvocationHandler.class
        Constructor constructor = proxyClass.getConstructor(InvocationHandler.class);
        //通过构造方法创建动态代理对象，传入自定义InvocationHandler实例
        HelloService helloProxy =
                (HelloService) constructor.newInstance(new HelloServiceDynamicProxy(new HelloServiceImpl()));
        //通过代理对象调用目标方法
        helloProxy.sayHello("andy");
        helloProxy.sayBye("andy");
    }

    @Test
    public void staticProxyTest() {
        HelloService target = new HelloServiceImpl();
        HelloService proxy = new HelloServiceStaticProxy(target);

        proxy.sayHello("andy");
        proxy.sayBye("andy");

    }
}
