package com.github.black.hole.proxy;

import com.github.black.hole.source.proxy.asm.ClassPrintVisitor;
import com.github.black.hole.source.proxy.asm.GenerateChildClass;
import org.junit.Test;
import org.objectweb.asm.ClassReader;

import java.io.FileOutputStream;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class AsmTest {

    @Test
    public void test() throws Exception {
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "\\extra\\proxy\\";
        FileOutputStream fos = new FileOutputStream(path + "ChildClass.class");
        byte[] bytes = GenerateChildClass.gen();
        fos.write(bytes);
        fos.flush();
        fos.close();
    }

    @Test
    public void visitClass() throws Exception {
        //将一个ClassPrintVisitor 对象传给ClassReader。ClassReader作为一个解析事件的producer
        // 并且由ClassPrintVisitor去消费（处理打印逻辑）。accept()方法就将Task 字节码进行解析，然后调用ClassPrintVisitor 的方法。
        ClassReader cr = new ClassReader("com.github.black.hole.source.proxy.asm.Task");
        ClassPrintVisitor cp = new ClassPrintVisitor();
        cr.accept(cp, 0);
    }
}
