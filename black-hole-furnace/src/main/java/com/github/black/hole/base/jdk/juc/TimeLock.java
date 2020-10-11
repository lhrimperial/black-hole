package com.github.black.hole.base.jdk.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hairen.long
 * @date 2020/10/8
 */
public class TimeLock implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args){
        TimeLock task = new TimeLock();
        new Thread(task).start();
        new Thread(task).start();
    }

    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName()+" get lock");
                Thread.sleep(6000);
            } else {
                System.out.println(Thread.currentThread().getName()+" get lock failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
