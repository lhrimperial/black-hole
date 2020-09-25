### Java线程

##### Java线程状态

Java 语言中定义了 6 种线程状态（源码Thread类中定义了State枚举），在任意一个时间点，一个线程有且只有其中一种状态，这 6 种状态是：

- 新建（NEW）：一个已经被实例化线程对象但未被启动（start）的线程处于这种状态。
- 可运行（RUNNABLE）：包括了操作系统线程状态中的 Running 和 Ready。因此，处于此状态的线程有可能正在执行，也有可能正在等待着 CPU 为它分配执行时间（CPU 时间片）。
- 阻塞（BLOCKED）：线程被阻塞了，在等待进入同步区域（synchronized）时，线程将进入这种状态。处于阻塞态的线程会不断请求资源（**由JVM操作**），一旦请求成功，就会进入就绪队列，等待执行。
- 无限期等待（WAITING）：线程处于无限期等待表示它需要等待并获得其他线程的指示后才能继续运行。处于这种状态的线程不会被分配 CPU 执行时间，直到当其他的一个线程完成了一个操作后显式地通知这个线程。
- 限期等待（TIMED_WAITING）：处于这种状态的线程也不会被分配 CPU 执行时间，不过无须等待被其他线程显式地唤醒，在一定时间之后它们会由操作系统自动唤醒，以结束限期等待状态。
- 结束（TERMINATED）：已终止的线程状态，线程已经结束执行。

>**注意：**
>
>1. 以上所指的线程状态均是 Java 线程在 JVM 中的状态，因此并不意味着此时对应于操作系统中的线程状态。jDK 中文档写着`A thread can be in only one state at a given point in time. These states are virtual machine states which do not reflect any operating system thread states`，意思是这些状态属于虚拟机状态，并不反映操作系统的线程状态。
>2. 在实践中，BLOCKED状态只出现在synchronized内置锁机制里，`A thread in the blocked state is waiting for a monitor lock`，此状态是等待一个monitor lock时的状态，所以它基本上是一个synchronized锁机制的专属状态。

##### 等待与阻塞的区别

blocked 是过去分词，意味着它是**被卡住**的，因为同步代码块在同一时刻只让一个线程执行，同时，JVM 会负责多个线程进入同步区的调度工作。因此，只要别的线程退出这段代码（放弃持有互斥锁），JVM 就会自动让其中一个blocked状态的线程进去，也就是说别的线程无需唤醒这些线程，由 JVM 自动来操作。

waiiting 是说线程自己调用 wait () 等函数，主动卡住自己，请 JVM 在满足某种条件后（比如另外的线程调用了 notify ()），把waiiting线程唤醒。这个唤醒的责任在于别的线程，唤醒的方法为显式地调用一些唤醒函数（比如，o.notifyAll () 或 o.notify ()）。

一句话概括，WAITTING 线程是自己现在不想要 CPU 时间片（因而暂停自己），但是 BLOCKED 线程是想要的，只是 BLOCKED 线程没有获得锁，所以它获取不到 CPU 时间片。

做这样的区分，是 JVM 出于管理的需要。如果别的线程运行出了 synchronized 这段代码，JVM 只需要去 blocked 队列，放一个线程出来。而某个线程调用了 notify () 后，JVM 只需要去 waitting 队列里取一个线程出来。

>P.S. 从 Linux 内核来看，线程BLOCKED、WAITING都是阻塞状态（Blocked），没区别，区别只在于 Java 的管理需要。在操作系统中，其中有三种状态：
>
>- 运行（Running）：在这一刻拥有CPU时间片。
>- 就绪（Ready）：可以被运行（runnable），只是暂时地被停止以让其他进程运行。
>- 阻塞（Blocked）：不能被运行直到一些依赖的外部事件发生。
>
>通常我们在系统级别说线程的阻塞（Blocked），例如在线程进行 I/O 操作时，因为 I/O 数据没有就绪因而需要等待，这种线程由 Linux 内核来唤醒（当 I/O 数据到来时，内核就把 Blocked 的线程放进可运行的线程队列，依次得到 CPU 时间）。而在系统级别说线程的 wait，是指线程等待一个内核 mutex 对象，另个线程 signal 这个 mutex 后，这个线程才可以运行。
>
>**注：**Java线程在等待IO数据时状态是RUNNABLE，上面提过虚拟机中的状态并不反映操作系统中的状态，等待IO数据时操作系统中的线程是阻塞状态的（后面有demo验证）。



##### 线程的生命周期

![Java线程](/Users/longhairen/Documents/geek_time/线程/Java线程.svg)

>几种让出CPU时间的方法
>
>- Object.wait () 方法会释放 CPU 执行权和占有的锁。
>- Thread.sleep (long) 方法仅释放 CPU 使用权，锁仍然占用。
>- Thread.yield () 方法仅释放 CPU 执行权，锁仍然占用，线程会被放入就绪队列，会在短时间内再次执行。

##### 线程状态转换

1. 新建（new）-就绪（ready）-运行中（running）

   新建一个线程对象之后，其他线程（如main线程）调用了该线程对象的start（）方法，该线程进入就绪状态，就绪状态的线程位于可运行的线程池中，等待线程调度器选中后可获取CPU时间。获得CPU时间的线程进入running状态并执行程序代码，Java中ready和running统称为runnable状态。

   >1. 不区分就绪（Ready）和运行中（Running）
   >
   >   为何 JVM 中没有去区分就绪（Ready）和运行中（Running）这两种状态，而是统一归为可运行（Runnable）呢？
   >
   >   现在的分时（time-sharing）多任务（multi-task）操作系统架构通常都是用所谓的时间分片（time quantum or time slice）方式进行抢占式（preemptive）轮转调度（round-robin）的。
   >
   >   这个时间分片通常是很小的，一个线程一次最多只能在 CPU 上运行比如 10-20ms 的时间（此时处于 Running 状态），也即大概只有 0.01 秒这一量级，时间片用完后就要被切换下来放入调度队列的末尾等待再次调度。（也即回到 Ready 状态）
   >
   >   通常，Java 的线程状态是服务于监控的，如果线程切换得是如此之快，那么区分 Ready 与 Running 就没什么太大意义了。
>
   >2. start () 和 run () 方法的区别
   >
   >   - START()
   >
   >     Thread 类的 start 方法被用来启动一个线程，并将其置为就绪状态。run 方法是这个线程真正要执行的方法。然而，调用 start 方法后，run 方法并不会立刻被调用，而是等待合适的时机时，线程调度器才会将这个线程置为运行（running）状态，最终 run 方法被调用。
   >
   >   - RUN()
   >
   >     run () 方法只是类的一个普通方法而已，如果直接调用 run 方法，程序中依然只有主线程这么一个线程（而不会创建一个新线程），其程序执行路径还是只有一条，顺序执行，等待 run 方法体执行完毕后才可继续执行下面的代码，这样就没有达到开启新线程的目的。
   >
   >   总结：调用 start 方法方可启动线程，而 run 方法只是一个普通方法的，因此在主线程中调用 run 方法，不会开启一个新线程，而只会在主线程中执行这个 run 方法中的方法体。

   ```java
   /**
    * NEW->RUNNABLE->TERMINATED
    */
   public class ThreadStateNRT {
       
       public static void main(String[] args) {
           Thread thread =
                   new Thread(
                           () ->
             											//RUNNABLE
                                   print(
                                           Thread.currentThread().getName(),
                                           Thread.currentThread().getState()),
                           "ThreadStateNRT");
         	//NEW
           print(thread.getName(), thread.getState());
           thread.start();
           // 等待线程执行完毕。
           try {
               thread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
         	//TERMINATED
           print(thread.getName(), thread.getState());
       }
   }
   
   //ThreadStateNRT:NEW
   //ThreadStateNRT:RUNNABLE
   //ThreadStateNRT:TERMINATED
   
   public class PrintUtils {
       private static final String stringFormat = "%s:%s";
       private static final String stringPFormat = "%s %s:%s";
   
       public static void print(String position,String threadName, Thread.State state) {
           System.out.println(String.format(stringPFormat,position, threadName, state));
       }
   
       public static void print(String threadName, Thread.State state) {
           System.out.println(String.format(stringFormat, threadName, state));
       }
   
       public static void print(String threadName, String msg) {
           System.out.println(String.format(stringFormat, threadName, msg));
       }
   }
   ```

2. 进入阻塞（Blocked）状态

   运行（Running）的线程进入了一个 synchronized 代码块或方法时，若该互斥锁被别的线程占用，则该线程会进入该互斥锁的阻塞队列中，且该线程进入阻塞状态；若该同步锁未被别的线程占用（该线程顺利获取到了锁），则线程状态没有变化。

   ```java
   /**
    * NEW->RUNNABLE->BLOCKED->RUNNABLE->TERMINATED
    */
   public class ThreadStateNRBRT {
       /** 锁 */
       private static final Object lock = new Object();
   
       public static void main(String[] args) {
           // 辅助线程，制造synchronized状态。
           Thread assistantThread =
                   new Thread(
                           () -> {
                               // 锁定一定时间
                               synchronized (lock) {
                                   try {
                                       Thread.sleep(100);
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               }
                           },"assistantThread");
           assistantThread.start();
   
           try {
               // 保证assistantThread先执行。
               Thread.sleep(10);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
   
           Thread showThread =
                   new Thread(
                           () -> {
                               synchronized (lock) {
                                   print(
                                           Thread.currentThread().getName(),
                                           Thread.currentThread().getState());
                               }
                           },"showThread");
           // NEW
           print(showThread.getName(), showThread.getState());
           // start
           showThread.start();
           // RUNNABLE
           print(showThread.getName(), showThread.getState());
           // 因为无法判断显示线程何时执行，所以循环直到显示线程执行。
           while (true) {
               if (showThread.getState() == Thread.State.BLOCKED) {
                   // BLOCKED
                   print(showThread.getName(), showThread.getState());
                   break;
               }
           }
           // 等待两个线程执行完毕。
           try {
               assistantThread.join();
               showThread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           // 线程执行完毕打印状态。
           print(showThread.getName(), showThread.getState());
       }
   }
   //showThread:NEW
   //showThread:RUNNABLE
   //showThread:BLOCKED
   //showThread:RUNNABLE
   //showThread:TERMINATED
   ```

   等待IO数据时JVM线程状态时RUNNABLE状态，而不是BLOCKED状态

   ```java
   public class ThreadStateIO {
   
       public static void main(String[] args) {
           Thread thread =
                   new Thread(
                           () -> {
                               Scanner scanner = new Scanner(System.in);
                               System.out.println("waiting enter sth: ");
                               String s = scanner.next();
                               System.out.println(Thread.currentThread().getName() + ": " + s);
                           },"IO-Thread");
   
           print(thread.getName(), thread.getState());
           thread.start();
           print(thread.getName(), thread.getState());
       }
   }
   //IO-Thread:NEW
   //IO-Thread:RUNNABLE
   //waiting enter sth: 
   ```

   通过jps、jstack 获取线程堆栈，其中IO-Thread堆栈信息如下

   ```
   "IO-Thread" #10 prio=5 os_prio=31 tid=0x00007fd7bd8cf000 nid=0x4303 runnable [0x00007000072c1000]
      java.lang.Thread.State: RUNNABLE
           at java.io.FileInputStream.readBytes(Native Method)
           at java.io.FileInputStream.read(FileInputStream.java:255)
           at java.io.BufferedInputStream.read1(BufferedInputStream.java:284)
           at java.io.BufferedInputStream.read(BufferedInputStream.java:345)
           - locked <0x00000007955a1af0> (a java.io.BufferedInputStream)
           at sun.nio.cs.StreamDecoder.readBytes(StreamDecoder.java:284)
           at sun.nio.cs.StreamDecoder.implRead(StreamDecoder.java:326)
           at sun.nio.cs.StreamDecoder.read(StreamDecoder.java:178)
           - locked <0x0000000795ac8bb0> (a java.io.InputStreamReader)
           at java.io.InputStreamReader.read(InputStreamReader.java:184)
           at java.io.Reader.read(Reader.java:100)
           at java.util.Scanner.readInput(Scanner.java:804)
           at java.util.Scanner.next(Scanner.java:1369)
           at com.github.micro.rpc.example.thread.state.ThreadStateIO.lambda$main$0(ThreadStateIO.java:20)
           at com.github.micro.rpc.example.thread.state.ThreadStateIO$$Lambda$1/885284298.run(Unknown Source)
           at java.lang.Thread.run(Thread.java:748)
   ```

   

3. 运行中（Running） - 就绪（Ready）

   Thread.yield () 的调用显式地使线程调度器（Thread Scheduler）暂停当前处于 Running 状态的线程，让出 CPU 时间片以让其他线程执行。yield方法只是让出CPU时间，并不会释放资源。

   ```java
   public class ThreadStateNRRT {
   
       private static final Object lock = new Object();
   
       public static void main(String[] args) throws Exception {
           Thread thread1 =
                   new Thread(
                           () -> {
                               synchronized (lock) {
                                   System.out.println("thread1 entry lock");
                                 	print(
                                           "thread1 yield before",
                                           Thread.currentThread().getName(),
                                           Thread.currentThread().getState());
                                   Thread.yield();
                                 	print(
                                           "thread1 yield after",
                                           Thread.currentThread().getName(),
                                           Thread.currentThread().getState());
                                   try {
                                       Thread.sleep(100);
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               }
                           },
                           "thread1");
   
           Thread thread2 =
                   new Thread(
                           () -> {
                               synchronized (lock) {
                                   System.out.println("thread2 entry lock");
                               }
                           },
                           "thread2");
   
           thread1.start();
           Thread.sleep(20);
           thread2.start();
           print("main", thread1.getName(), thread1.getState());
           print("main", thread2.getName(), thread2.getState());
   
           thread1.join();
           thread2.join();
       }
   }
   
   //thread1 entry lock
   //thread1 yield before thread1:RUNNABLE
   //thread1 yield after thread1:RUNNABLE
   //main thread1:TIMED_WAITING
   //main thread2:BLOCKED
   //thread2 entry lock
   ```

4. 进入限期等待（Timed Waiting）状态

   TIMED_WAITING（有等待时间的等待状态）。限期等待状态是线程自己主动发起的，线程主动调用自己的以下方法，会使其进行限期等待（Timed Waiting）：

   - Thread.sleep (sleeptime)，且带有时间；
   - Object.wait (timeout)，且带有时间；
   - Thread.join (timeout)，且带有时间（join进入也是一个wait）；
   - LockSupport.parkNanos ()，且带有时间；
   - LockSupport.parkUntil ()，且带有时间；

   ```java
   /**
    * NEW->RUNNABLE->TIMED_WAITING->RUNNABLE->TERMINATED
    */
   public class ThreadStateNRTWRT {
       /** 锁 */
       private static final Object lockO = new Object();
   
       private static Lock lock = new ReentrantLock();
       private static Condition condition = lock.newCondition();
   
       public static void main(String[] args) {
           objectTimedWait();
           //        lockCondition();
       }
   
       private static void lockCondition() {
           // 展示线程
           Thread showThread =
                   new Thread(
                           () -> {
                               // 等待
                               lock.lock();
                               try {
                                   try {
                                       condition.await(1, TimeUnit.MILLISECONDS);
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               } finally {
                                   lock.unlock();
                               }
                               // RUNNABLE
                               print(
                                       Thread.currentThread().getName(),
                                       Thread.currentThread().getState());
                           },
                           "showThread");
           // NEW
           print(showThread.getName(), showThread.getState());
           showThread.start();
           // RUNNABLE
           print(showThread.getName(), showThread.getState());
           // 循环读取展示线程状态，直到读到展示线程状态为TIMED_WAITING。
           while (true) {
               if (Thread.State.TIMED_WAITING == showThread.getState()) {
                   print(showThread.getName(), showThread.getState());
                   break;
               }
           }
           try {
               showThread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           // 线程执行完毕打印状态。
           print(showThread.getName(), showThread.getState());
       }
   
       private static void objectTimedWait() {
           Thread showThread =
                   new Thread(
                           () -> {
                               // 等待
                               synchronized (lockO) {
                                   try {
                                       lockO.wait(1);
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               }
                               // RUNNABLE
                               print(
                                       Thread.currentThread().getName(),
                                       Thread.currentThread().getState());
                           },
                           "showThread");
           // NEW
           print(showThread.getName(), showThread.getState());
           showThread.start();
           // RUNNABLE
           print(showThread.getName(), showThread.getState());
           // 循环读取展示线程状态，直到读到展示线程状态为TIMED_WAITING。
           while (true) {
               // TIMED_WAITING
               if (showThread.getState() == Thread.State.TIMED_WAITING) {
                   print(showThread.getName(), showThread.getState());
                   break;
               }
           }
   
           try {
               showThread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           // 线程执行完毕打印状态。
           print(showThread.getName(), showThread.getState());
       }
   }
   //showThread:NEW
   //showThread:RUNNABLE
   //showThread:TIMED_WAITING
   //showThread:RUNNABLE
   //showThread:TERMINATED
   ```

5. 进入无限期等待（Waiting）状态

   处于无限期等待（Waiting）的线程限期等待状态是线程自己主动发起的，可运行状态的线程调用以下方法，会进入无限期等待状态：

   - Object.wait () 方法，并且没有使用 timeout 参数；
   - Thread.join () 方法，没有使用 timeout 参数；
   - LockSupport.park()；
   - Conditon.await () 方法。

   ```java
   public class ThreadStateNRWRT {
       /** 锁 */
       private static final Object lock = new Object();
   
       private static Lock reentrantLock = new ReentrantLock();
       private static Condition condition = reentrantLock.newCondition();
   
       public static void main(String[] args) {
           objectWait();
           //        lockCondition();
       }
   
       private static void lockCondition() {
           // 展示线程
           Thread showThread =
                   new Thread(
                           () -> {
                               // 等待
                               reentrantLock.lock();
                               try {
                                   try {
                                       condition.await();
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               } finally {
                                   reentrantLock.unlock();
                               }
                               // RUNNABLE
                               print(
                                       Thread.currentThread().getName(),
                                       Thread.currentThread().getState());
                           },
                           "showThread");
           // NEW
           print(showThread.getName(), showThread.getState());
           showThread.start();
           // RUNNABLE
           print(showThread.getName(), showThread.getState());
           // 循环读取展示线程状态，直到读到展示线程状态为WAITING，才让辅助线程唤醒等待线程。
           while (true) {
               if (showThread.getState() == Thread.State.WAITING) {
                   // WAITING
                   print(showThread.getName(), showThread.getState());
                   break;
               }
           }
   
           // 唤醒showThread
           reentrantLock.lock();
           try {
               condition.signal();
           } finally {
               reentrantLock.unlock();
           }
   
           try {
               showThread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           // 线程执行完毕打印状态。
           print(showThread.getName(), showThread.getState());
       }
   
       private static void objectWait() {
           // 展示线程
           Thread showThread =
                   new Thread(
                           () -> {
                               // 等待
                               synchronized (lock) {
                                   try {
                                       lock.wait();
                                   } catch (InterruptedException e) {
                                       e.printStackTrace();
                                   }
                               }
                               // RUNNABLE
                               print(
                                       Thread.currentThread().getName(),
                                       Thread.currentThread().getState());
                           },
                           "showThread");
           // NEW
           print(showThread.getName(), showThread.getState());
           showThread.start();
           // RUNNABLE
           print(showThread.getName(), showThread.getState());
           // 循环读取展示线程状态，直到读到展示线程状态为WAITING，才让辅助线程唤醒等待线程。
           while (true) {
               if (showThread.getState() == Thread.State.WAITING) {
                   print(showThread.getName(), showThread.getState());
                   break;
               }
           }
           // main线程唤醒showThread
           synchronized (lock) {
               lock.notify();
           }
   
           try {
               showThread.join();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           // 线程执行完毕打印状态。
           print(showThread.getName(), showThread.getState());
       }
   }
   //showThread:NEW
   //showThread:RUNNABLE
   //showThread:WAITING
   //showThread:RUNNABLE
   //showThread:TERMINATED
   ```
