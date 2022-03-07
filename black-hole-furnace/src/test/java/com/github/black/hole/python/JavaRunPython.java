package com.github.black.hole.python;

import org.python.util.PythonInterpreter;

public class JavaRunPython {

    public static void main(String[] args) {
        //首先调用python的解释器
        PythonInterpreter interpreter = new PythonInterpreter();
        //选择执行的的Python语句
        interpreter.exec("a='hello world'; ");
        interpreter.exec("print a;");
    }
}
