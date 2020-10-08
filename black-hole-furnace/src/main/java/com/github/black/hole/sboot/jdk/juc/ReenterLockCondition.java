package com.github.black.hole.sboot.jdk.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hairen.long
 * @date 2020/10/8
 */
public class ReenterLockCondition implements Runnable{

    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    public static void main(String[] args) throws Exception{
        ReenterLockCondition task = new ReenterLockCondition();
        new Thread(task).start();
        Thread.sleep(2000);
        lock.lock();
        try {
            condition.signal();
        }finally {
            lock.unlock();
        }
    }


    @Override
    public void run() {
        lock.lock();
        try {
            condition.await();
            System.out.println("thread going on");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
