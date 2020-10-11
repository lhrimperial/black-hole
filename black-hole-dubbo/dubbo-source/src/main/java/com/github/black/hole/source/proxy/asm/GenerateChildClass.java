package com.github.black.hole.source.proxy.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class GenerateChildClass implements Opcodes {

    public static byte[] gen() {
        ClassWriter classWriter = new ClassWriter(0);
        classWriter.visit(Opcodes.V1_8, ACC_PUBLIC + ACC_ABSTRACT, "com.github.black.hole.source.proxy.asm" +
                        ".ChildClass",
                null, "java.lang.Object", new String[]{"com.github.black.hole.source.proxy.asm.ParentInter"});
        classWriter.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "zero", "I", null, new Integer(0)).visitEnd();
        classWriter.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo", "(Ljava.lang.Object;)I", null, null).visitEnd();
        classWriter.visitEnd();
        return classWriter.toByteArray();
    }
}
