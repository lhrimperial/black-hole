package com.github.black.hole.proxy;

import com.github.black.hole.source.proxy.cglib.CglibMethodInterceptor;
import com.github.black.hole.source.service.HelloService;
import com.github.black.hole.source.service.impl.HelloServiceImpl;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class CgLibTest {

    @Test
    public void cgLigTest(){
        String projectPath = System.getProperty("user.dir");
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, projectPath+"\\extra\\proxy\\cglib");
        CglibMethodInterceptor cglibInterceptor = new CglibMethodInterceptor();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(cglibInterceptor);

        HelloService service = (HelloService) enhancer.create();
        service.sayHello("andy");
        service.sayBye("andy");
    }
}
