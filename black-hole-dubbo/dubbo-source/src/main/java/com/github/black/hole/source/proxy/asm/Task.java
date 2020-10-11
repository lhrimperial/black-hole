package com.github.black.hole.source.proxy.asm;

/**
 * @author hairen.long
 * @date 2020/10/11
 */
public class Task implements ITask1, ITask2 {

    private int isTask = 0;

    private long tell = 0;

    public void isTask() {
        System.out.println("call isTask");
    }

    public void tellMe() {
        System.out.println("call tellMe");
    }

    class TaskInner {
        int inner;
    }

}
