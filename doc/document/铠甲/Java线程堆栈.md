#### Java线程堆栈

##### 线程堆栈总述

线程堆栈也称线程调用堆栈，是虚拟机中线程（包括锁）状态的一个瞬间快照，即系统在某一个时刻所有线程的运行状态，包括每一个线程的调用堆栈，锁的持有情况。虽然不同的虚拟机打印出来的格式有些不同，但是线程堆栈的信息都包含：

- 线程名字，id，线程的数量等
- 线程的运行状态，锁的状态（锁被哪个线程持有，哪个线程在等待锁等）
- 调用堆栈（即函数的调用层次关系）调用堆栈包含完整的类名，所执行的方法，源代码的行数

借助堆栈信息可以帮助分析很多问题，如线程死锁，锁争用，死循环，识别耗时操作等等。在多线程场合下的稳定性问题分析和性能问题分析，线程堆栈分析湿最有效的方法，在多数情况下，无需对系统了解就可以进行相应的分析。由于线程堆栈是系统某个时刻的线程运行状况（即瞬间快照），对于历史痕迹无法追踪。只能结合日志分析。总的来说线程堆栈是多线程类应用程序非功能型问题定位的最有效手段，最善于分析如下类型问题：

- 系统无缘无故的cpu过高
- 系统挂起，无响应
- 系统运行越来越慢
- 性能瓶颈（如无法充分利用cpu等）
- 线程死锁，死循环等
- 由于线程数量太多导致的内存溢出（如无法创建线程等）

借助线程堆栈可以帮助我们缩小范围，找到突破口。线程堆栈分析很多时候不需要源代码，在很多场合都有优势。实际运行中，往往一次dump的信息，还不足以确认问题，建议产生三次dump信息，如果每次dump都指向同一个问题，我们才确定问题的典型性。



##### 解读线程堆栈

看一段代码

```java
public class ThreadStack {
    private Object lock1 = new Object();
    private Object lock2 =new Object();

    private void fun1(){
        synchronized (lock1) {
            fun2();
        }
    }

    private void fun2(){
        synchronized (lock2){
            while (true){
                System.out.println();
            }
        }
    }

    public static void main(String[] args){
        new ThreadStack().fun1();
    }
}
```

`jstack`查看堆栈信息

```
"main" #1 prio=5 os_prio=31 tid=0x00007fd676809800 nid=0x1803 runnable [0x000070000f7a1000]
   java.lang.Thread.State: RUNNABLE
	at java.io.FileOutputStream.writeBytes(Native Method)
	at java.io.FileOutputStream.write(FileOutputStream.java:326)
	at java.io.BufferedOutputStream.flushBuffer(BufferedOutputStream.java:82)
	at java.io.BufferedOutputStream.flush(BufferedOutputStream.java:140)
	- locked <0x0000000740036a00> (a java.io.BufferedOutputStream)
	at java.io.PrintStream.write(PrintStream.java:482)
	- locked <0x0000000740008648> (a java.io.PrintStream)
	at sun.nio.cs.StreamEncoder.writeBytes(StreamEncoder.java:221)
	at sun.nio.cs.StreamEncoder.implFlushBuffer(StreamEncoder.java:291)
	at sun.nio.cs.StreamEncoder.flushBuffer(StreamEncoder.java:104)
	- locked <0x0000000740008668> (a java.io.OutputStreamWriter)
	at java.io.OutputStreamWriter.flushBuffer(OutputStreamWriter.java:185)
	at java.io.PrintStream.newLine(PrintStream.java:546)
	- locked <0x0000000740008648> (a java.io.PrintStream)
	at java.io.PrintStream.println(PrintStream.java:696)
	at com.github.micro.rpc.example.thread.lock.ThreadStack.fun2(ThreadStack.java:22)
	- locked <0x0000000740008680> (a java.lang.Object)
	at com.github.micro.rpc.example.thread.lock.ThreadStack.fun1(ThreadStack.java:15)
	- locked <0x0000000740006a70> (a java.lang.Object)
	at com.github.micro.rpc.example.thread.lock.ThreadStack.main(ThreadStack.java:28)
```

从上面的main线程看，线程堆栈里面的最直观的信息是当前线程的调用上下文，即从哪个函数调用到哪个函数（从下往上看），正执行到哪一类的哪一行，借助这些信息，我们就对当前系统正在做什么一目了然。

另外，从main线程的堆栈中，有-locked<0x0000000740006a70>(a java.lang.Object) 语句，这表示该线程已经占用了锁，其中0x0000000740006a70表示锁ID，这个锁ID是系统自动生成的，我们只需要知道每次打印堆栈，同一个ID表示是同一个锁即可.

![Java线程-线程堆栈信息](/Users/longhairen/Documents/geek_time/线程/Java线程-线程堆栈信息.svg)

其中"系统线程Id号"所指的本地线程是指该java虚拟机所对应的虚拟机中的本地线程，我们知道java是解析型语言，执行的实体是java虚拟机，因此java代码是依附于java虚拟机的本地线程执行的，当启动一个线程时，是创建一个native本地线程，本地线程才是真实的线程实体，为了更加深入理解本地线程和java线程的关系，试用`pstack<java pid>` 获得java虚拟机本地线程的堆栈.



##### 锁的解读

与锁相关的重要信息如下：

- 当一个线程占有一个锁的时候，线程堆栈会打印一个－locked<0x22bffb60>
- 当一个线程正在等在其他线程释放该锁，线程堆栈会打印一个－waiting to lock<0x22bffb60>（parking to wait for）
- 当一个线程占有一个锁，但又执行在该锁的wait上，线程堆栈中首先打印locked,然后打印－waiting on <0x22c03c60>

在线程堆栈中与锁相关的三个最重要的特征字：locked,waiting to lock（parking to wait for）,waiting on 了解这些特征字，当一个或一些线程正在等待一个锁的时候，应该有一个线程占用了这个锁，即如果有一个线程正在等待一个锁，该锁必然被另一个线程占用，从线程堆栈中看，如果看到waiting to lock<0x22bffb60>,应该也应该有locked<0x22bffb60>,大多数情况下确实如此，但是有些情况下，会发现线程堆栈中可能根本没有locked<0x22bffb60>,而只有waiting to lock，这是什么原因呢，实际上，在一个线程释放锁和另一个线程被唤醒之间有一个时间窗，如果这个期间，恰好打印堆栈信息，那么只会找到waiting to ，但是找不到locked 该锁的线程，当然不同的JAVA虚拟机有不同的实现策略，不一定会立刻响应请求，也许会等待正在执行的线程执行完成。



##### CPU 100%定位

1. 步骤一、找到最耗CPU的进程 
   工具：top 
   方法：
   - 执行top -c ，显示进程运行信息列表
   - 键入P (大写p)，进程按照CPU使用率排序

2. 步骤二：找到最耗CPU的线程 
   工具：top 
   方法：
   - top -Hp 10765 ，显示一个进程的线程运行信息列表
   - 键入P (大写p)，线程按照CPU使用率排序

3. 步骤三：将线程PID转化为16进制 
   工具：printf 
   方法：printf “%x\n” 10804

4. 步骤四：查看堆栈，找到线程在干嘛 
   工具：ps/jstack/grep 
   方法：jstack 10765 | grep ‘0x2a34’ -C5 –color 
   打印进程堆栈 
   通过线程id，过滤得到线程堆栈







