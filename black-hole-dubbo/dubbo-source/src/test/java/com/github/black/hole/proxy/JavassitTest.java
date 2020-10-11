package com.github.black.hole.proxy;

import com.github.black.hole.source.proxy.javassist.JavassitClassGenerate;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class JavassitTest {

    @Test
    public void javassistTest() throws Exception{
        Class clazz = JavassitClassGenerate.gen();
        Object obj = clazz.newInstance();
        Method sayHello = clazz.getMethod("sayHello");
        sayHello.invoke(obj);
    }
}
