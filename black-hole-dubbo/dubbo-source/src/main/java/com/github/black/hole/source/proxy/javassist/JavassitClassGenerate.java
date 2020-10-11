package com.github.black.hole.source.proxy.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

import java.io.File;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class JavassitClassGenerate {

    public static Class gen() throws Exception {
        ClassPool classPool = ClassPool.getDefault();
        //创建类
        CtClass helloClass = classPool.makeClass("com.github.sources.proxy.javassist.JavassistHelloWorld");
        //定义方法
        CtMethod sayHelloMethod = CtNewMethod.make("public void sayHello(){}", helloClass);
        // 插入方法代码
        sayHelloMethod.insertBefore("System.out.print(\"Hello World， Javassist\");");
        helloClass.addMethod(sayHelloMethod);

        //保存生成的字节码
        File dir = new File(System.getProperty("user.dir")+"\\extra\\proxy\\javassist");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        helloClass.writeFile(dir.getCanonicalPath());

        return helloClass.toClass();
    }
}
