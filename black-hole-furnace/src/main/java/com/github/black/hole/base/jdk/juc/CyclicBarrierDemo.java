package com.github.black.hole.base.jdk.juc;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hairen.long
 * @date 2020/10/8
 */
public class CyclicBarrierDemo {

    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        ExecutorService service = Executors.newFixedThreadPool(3);

        service.submit(new Runner(cyclicBarrier, "zhangsan"));
        service.submit(new Runner(cyclicBarrier, "lisi"));
        service.submit(new Runner(cyclicBarrier, "wangwu"));

        service.shutdown();
    }

    public static class Runner implements Runnable {

        private CyclicBarrier cyclicBarrier;
        private String name;

        public Runner(CyclicBarrier cyclicBarrier, String name) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000*(new Random().nextInt(8)));
                System.out.println(name + " 准备好了");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":"+ name+" go....");
        }
    }
}
