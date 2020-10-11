package com.github.black.hole.source.proxy.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author hairen.long
 * @date 2020/10/11
 */

public class ClassPrintVisitor extends ClassVisitor {

    public ClassPrintVisitor() {
        super(Opcodes.ASM4);
    }

    @Override
    public void visit(int version, int access, String name,
                      String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + " {");
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc,
                                   String signature, Object value) {
        if (name.startsWith("is")) {
            System.out.println(" field name: " + name + desc);
        }
        return null;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name,
                                     String desc, String signature, String[] exceptions) {
        if (name.startsWith("is")) {
            System.out.println(" start with is method: " + name + desc);
        }
        return null;
    }

    @Override
    public void visitInnerClass(String name, String outerName,
                                String innerName, int access) {
        System.out.println(" inner class, name=" + name+",outerName="+outerName+",innerName=" + innerName);
    }

    @Override
    public void visitEnd() {
        System.out.println("}");
    }
}