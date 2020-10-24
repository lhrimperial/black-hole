#### ForkJoin

#####一、ForkJoin 分而治之

ForkJoinPool运用了Fork/Join原理，使用“分而治之”的思想，将大任务分拆成小任务分配给多个线程执行，最后合并得到最终结果。把任务递归的拆分为各个子任务，这样可以更好的利用系统资源，尽可能的使用所有可用的计算能力来提升应用性能。

![ForkJoinPool-fork-join](forkjoin.assets/ForkJoinPool-fork-join.svg)

最核心的思想可以这样描述：

```java
if(任务很小）{
    直接计算得到结果
}else{
    分拆成N个子任务
    调用子任务的fork()进行计算
    调用子任务的join()合并计算结果
}
```



#####二、ForkJoin 中重要概念

1. ForkJoinPool里有三个重要的角色：

- ForkJoinWorkerThread（下文简称worker）：包装Thread；
- WorkQueue：任务队列，双向；
- ForkJoinTask：worker执行的对象，实现了Future。两种类型，一种叫submission由外部提交，另一种就叫task由内部fork。

>ForkJoinPool使用数组保存所有WorkQueue，每个worker有属于自己的WorkQueue，但不是每个WorkQueue都有对应的worker。
>
>- 没有worker的WorkQueue：保存的是submission，来自外部提交，在WorkQueue[]的下标是偶数；
>- 属于worker的WorkQueue：保存的是task，来自fork出的任务，在WorkQueue[]的下标是奇数。

2.  WorkQueue

   一个双端队列，同时支持LIFO(last-in-first-out)的push和pop操作，和FIFO(first-in-first-out)的poll操作，分别操作top端和base端。worker操作自己的WorkQueue是LIFO操作(可选FIFO)，除此之外，worker会尝试steal其他WorkQueue里的任务，这个时候执行的是FIFO操作。

3. work-stealing（工作窃取）算法

   线程池内的所有工作线程都尝试找到并执行已经提交的任务，或者是被其他活动任务创建的子任务（如果不存在就阻塞等待）。

   ![ForkJoinPool-work-queue](forkjoin.assets/ForkJoinPool-work-queue.svg)

   - ForkJoinPool中维护着一个WorkQueue数组，里面存放的是ForkJoinTask任务
   - ForkJoinPool的每个工作线程ForkJoinWorkerThread都关联着一个WorkQueue数组中下标为奇数的WorkQueue
   - 由外部线程提交的任务会保存到下标为偶数的WorkQueue中
   - 每个工作线程在运行时会处理自己工作队列里的任务或者steal 其他工作队列的任务

4. ForkJoinTask子类

   - RecursiveTask：它是一种会返回结果的任务。
   - RecursiveAction：它是一种没有任何返回值的任务。
   - CountedCompleter： 在任务完成执行后会触发执行一个自定义的钩子函数。



##### 三、核心参数

1. ForkJoinPool 与 内部类 WorkQueue 共享的一些常量：

   ```java
   // Constants shared across ForkJoinPool and WorkQueue
   
   // 限定参数
   static final int SMASK = 0xffff;        //  低位掩码，也是最大索引位
   static final int MAX_CAP = 0x7fff;        //  工作线程最大容量
   static final int EVENMASK = 0xfffe;        //  偶数低位掩码
   static final int SQMASK = 0x007e;        //  workQueues 数组最多64个槽位
   
   // ctl 子域和 WorkQueue.scanState 的掩码和标志位
   static final int SCANNING = 1;             // 标记是否正在运行任务
   static final int INACTIVE = 1 << 31;       // 失活状态  负数
   static final int SS_SEQ = 1 << 16;       // 版本戳，防止ABA问题
   
   // ForkJoinPool.config 和 WorkQueue.config 的配置信息标记
   static final int MODE_MASK = 0xffff << 16;  // 模式掩码
   static final int LIFO_QUEUE = 0; //LIFO队列
   static final int FIFO_QUEUE = 1 << 16;//FIFO队列
   static final int SHARED_QUEUE = 1 << 31;       // 共享模式队列，负数
   ```

2. ForkJoinPool 中的相关常量和实例字段：

   ```java
   //  低位和高位掩码
   private static final long SP_MASK = 0xffffffffL;
   private static final long UC_MASK = ~SP_MASK;
   
   // 活跃线程数
   private static final int AC_SHIFT = 48;
   private static final long AC_UNIT = 0x0001L << AC_SHIFT; //活跃线程数增量
   private static final long AC_MASK = 0xffffL << AC_SHIFT; //活跃线程数掩码
   
   // 工作线程数
   private static final int TC_SHIFT = 32;
   private static final long TC_UNIT = 0x0001L << TC_SHIFT; //工作线程数增量
   private static final long TC_MASK = 0xffffL << TC_SHIFT; //掩码
   private static final long ADD_WORKER = 0x0001L << (TC_SHIFT + 15);  // 创建工作线程标志
   
   // 池状态
   private static final int RSLOCK = 1;
   private static final int RSIGNAL = 1 << 1;
   private static final int STARTED = 1 << 2;
   private static final int STOP = 1 << 29;
   private static final int TERMINATED = 1 << 30;
   private static final int SHUTDOWN = 1 << 31;
   
   // 实例字段
   volatile long ctl;                   // 主控制参数
   volatile int runState;               // 运行状态锁
   final int config;                    // 并行度|模式
   int indexSeed;                       // 用于生成工作线程索引
   volatile WorkQueue[] workQueues;     // 主对象注册信息，workQueue
   final ForkJoinWorkerThreadFactory factory;// 线程工厂
   final UncaughtExceptionHandler ueh;  // 每个工作线程的异常信息
   final String workerNamePrefix;       // 用于创建工作线程的名称
   volatile AtomicLong stealCounter;    // 偷取任务总数，也可作为同步监视器
   
   /** 静态初始化字段 */
   //线程工厂
   public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
   //启动或杀死线程的方法调用者的权限
   private static final RuntimePermission modifyThreadPermission;
   // 公共静态pool
   static final ForkJoinPool common;
   //并行度，对应内部common池
   static final int commonParallelism;
   //备用线程数，在tryCompensate中使用
   private static int commonMaxSpares;
   //创建workerNamePrefix(工作线程名称前缀)时的序号
   private static int poolNumberSequence;
   //线程阻塞等待新的任务的超时值(以纳秒为单位)，默认2秒
   private static final long IDLE_TIMEOUT = 2000L * 1000L * 1000L; // 2sec
   //空闲超时时间，防止timer未命中
   private static final long TIMEOUT_SLOP = 20L * 1000L * 1000L;  // 20ms
   //默认备用线程数
   private static final int DEFAULT_COMMON_MAX_SPARES = 256;
   //阻塞前自旋的次数，用在在awaitRunStateLock和awaitWork中
   private static final int SPINS  = 0;
   //indexSeed的增量
   private static final int SEED_INCREMENT = 0x9e3779b9;
   ```

**说明：** ForkJoinPool 的内部状态都是通过一个64位的 long 型 变量`ctl`来存储，它由四个16位的子域组成：

- AC：正在运行工作线程数减去目标并行度，高16位
- TC：总工作线程数减去目标并行度，中高16位
- SS：栈顶等待线程的版本计数和状态，中低16位
- ID： 栈顶 WorkQueue 在池中的索引（`poolIndex`），低16位

在后面的源码解析中，某些地方也提取了`ctl`的低32位（`sp=(int)ctl`）来检查工作线程状态，例如，当sp不为0时说明当前还有空闲工作线程。

3. ForkJoinPool.WorkQueue 中的相关属性：

   ```java
   //初始队列容量，2的幂
   static final int INITIAL_QUEUE_CAPACITY = 1 << 13;
   //最大队列容量
   static final int MAXIMUM_QUEUE_CAPACITY = 1 << 26; // 64M
   
   // 实例字段
   volatile int scanState;    // Woker状态, <0: inactive; odd:scanning
   int stackPred;             // 记录前一个栈顶的ctl
   int nsteals;               // 偷取任务数
   int hint;                  // 记录偷取者索引，初始为随机索引
   int config;                // 池索引和模式
   volatile int qlock;        // 1: locked, < 0: terminate; else 0
   volatile int base;         //下一个poll操作的索引（栈底/队列头）
   int top;                   //  下一个push操作的索引（栈顶/队列尾）
   ForkJoinTask<?>[] array;   // 任务数组
   final ForkJoinPool pool;   // the containing pool (may be null)
   final ForkJoinWorkerThread owner; // 当前工作队列的工作线程，共享模式下为null
   volatile Thread parker;    // 调用park阻塞期间为owner，其他情况为null
   volatile ForkJoinTask<?> currentJoin;  // 记录被join过来的任务
   volatile ForkJoinTask<?> currentSteal; // 记录从其他工作队列偷取过来的任务
   ```

4. ForkJoinTask中相关属性

   ```java
   /** 任务运行状态 */
   volatile int status; // 任务运行状态
   static final int DONE_MASK   = 0xf0000000;  // 任务完成状态标志位
   static final int NORMAL      = 0xf0000000;  // must be negative
   static final int CANCELLED   = 0xc0000000;  // must be < NORMAL
   static final int EXCEPTIONAL = 0x80000000;  // must be < CANCELLED
   static final int SIGNAL      = 0x00010000;  // must be >= 1 << 16 等待信号
   static final int SMASK       = 0x0000ffff;  //  低位掩码
   ```



##### 四、整体流程

1. 整个流程和重要方法归纳：

> 任务提交

- 提交任务入口：submit,execute,invoke
- 完整版提交任务：externalSubmit(包括初始化)
- 简单版提交任务：externalPush

> worker管理

- 激活或创建：signalWork
- 创建：tryAddWorker,createWorker
- 注册、撤销注册：registerWorker,deregisterWorker

> worker执行(runWorker三部曲)

- 获取：scan
- 执行：runTask
- 等待：awaitWork

> Fork

- 等同于提交任务

> Join(doJoin)

- 当前不是worker：externalAwaitDone
- 当前是worker：awaitJoin

> awaitJoin等待两种策略

- Helping：tryRemoveAndExec、helpStealer
- Compensating：tryCompensate

> 等待所有任务完成

- 静止：awaitQuiescence
- 终止：awaitTermination

> 关闭

- shutdown,shutdownNow
- tryTerminate

> 异常处理



2. 整个Fork/Join 框架的执行流程

![ForkJoinPool-执行流程](forkjoin.assets/ForkJoinPool-执行流程.svg)



##### 五、源码解析

###### ForkJoinPool 构造函数

```java
public ForkJoinPool(int parallelism,
                    ForkJoinWorkerThreadFactory factory,
                    UncaughtExceptionHandler handler,
                    boolean asyncMode) {
    this(checkParallelism(parallelism),
            checkFactory(factory),
            handler,
            asyncMode ? FIFO_QUEUE : LIFO_QUEUE,
            "ForkJoinPool-" + nextPoolId() + "-worker-");
    checkPermission();
}

private ForkJoinPool(int parallelism,
                         ForkJoinWorkerThreadFactory factory,
                         UncaughtExceptionHandler handler,
                         int mode,
                         String workerNamePrefix) {
        this.workerNamePrefix = workerNamePrefix;
        this.factory = factory;
        this.ueh = handler;
        this.config = (parallelism & SMASK) | mode;
        long np = (long)(-parallelism); // offset ctl counts
        this.ctl = ((np << AC_SHIFT) & AC_MASK) | ((np << TC_SHIFT) & TC_MASK);
    }
```

**说明：** 在 ForkJoinPool 中我们可以自定义四个参数：

- parallelism：并行度，默认为CPU数，最小为1
- factory：工作线程工厂；

- handler：处理工作线程运行任务时的异常情况类，默认为null；
- asyncMode：是否为异步模式，默认为 false。如果为`true`，表示子任务的执行遵循 FIFO 顺序并且任务不能被合并（join），这种模式适用于工作线程只运行事件类型的异步任务。

> 在多数场景使用时，如果没有太强的业务需求，我们一般直接使用 ForkJoinPool 中的common池，使用common pool的优点就是我们可以通过指定系统参数的方式定义“并行度、线程工厂和异常处理类”；并且它使用的是同步模式，也就是说可以支持任务合并（join）。



######外部任务提交

向 ForkJoinPool 提交任务有三种方式：

- `invoke()`会等待任务计算完毕并返回计算结果；
- `execute()`是直接向池提交一个任务来异步执行，无返回结果；
- `submit()`也是异步执行，但是会返回提交的任务，在适当的时候可通过`task.get()`获取执行结果。

>这三种提交方式都都是调用`externalPush()`方法来完成，所以接下来我们将从`externalPush()`方法开始逐步分析外部任务的执行过程。



1. externalPush(ForkJoinTask<?> task)

```java
//添加给定任务到submission队列中
final void externalPush(ForkJoinTask<?> task) {
        WorkQueue[] ws; WorkQueue q; int m;
        int r = ThreadLocalRandom.getProbe();//探针值，用于计算WorkQueue槽位索引
        int rs = runState;
  			//第一次提交任务，workQueue还没初始化调用完整版提交
        if ((ws = workQueues) != null && (m = (ws.length - 1)) >= 0 &&
            (q = ws[m & r & SQMASK]) != null && r != 0 && rs > 0 &&
            U.compareAndSwapInt(q, QLOCK, 0, 1)) {
          	//q = ws[m & r & SQMASK]计算获取偶数槽位的workQueue
          	//锁定workQueue
            ForkJoinTask<?>[] a; int am, n, s;
            if ((a = q.array) != null &&
                (am = a.length - 1) > (n = (s = q.top) - q.base)) {
                int j = ((am & s) << ASHIFT) + ABASE;//计算任务索引位置
                U.putOrderedObject(a, j, task);//任务入列
                U.putOrderedInt(q, QTOP, s + 1);//更新push slot
                U.putIntVolatile(q, QLOCK, 0);//解除锁定
                if (n <= 1)
                    signalWork(ws, q);//任务数小于1时尝试创建或激活一个工作线程
                return;
            }
            U.compareAndSwapInt(q, QLOCK, 1, 0);//解除锁定
        }
        externalSubmit(task);//初始化workQueues及相关属性并提交任务
    }
```

`externalPush`的执行流程很简单：首先找到一个随机偶数槽位的 workQueue，然后把任务放入这个 workQueue 的任务数组中，并更新`top`位。如果队列的剩余任务数小于1，则尝试创建或激活一个工作线程来运行任务（防止在`externalSubmit`初始化时发生异常导致工作线程创建失败）。

每个线程维护一个数（probe），只要这个数不变，每次提交任务都到同一个任务队列。



2. externalSubmit(ForkJoinTask<?> task)

   ```java
   //完整版任务提交
   private void externalSubmit(ForkJoinTask<?> task) {
       int r;    //初始化调用线程的探针值，用于计算WorkQueue索引
       if ((r = ThreadLocalRandom.getProbe()) == 0) {
           ThreadLocalRandom.localInit();
           r = ThreadLocalRandom.getProbe();
       }
       for (;;) {
           WorkQueue[] ws; WorkQueue q; int rs, m, k;
           boolean move = false;
   	      // 池已关闭
           if ((rs = runState) < 0) {
               tryTerminate(false, false);     // help terminate
               throw new RejectedExecutionException();
           }
         	//初始化workQueues
           else if ((rs & STARTED) == 0 ||     // initialize
                    ((ws = workQueues) == null || (m = ws.length - 1) < 0)) {
               int ns = 0;
               rs = lockRunState();//锁定runState
               try {
                 	//初始化
                   if ((rs & STARTED) == 0) {
                     	//初始化stealCounter
                       U.compareAndSwapObject(this, STEALCOUNTER, null,
                                              new AtomicLong());
                       // create workQueues array with size a power of two
                     	//创建workQueues，容量为2的幂次方
                       int p = config & SMASK; // ensure at least 2 slots
                       int n = (p > 1) ? p - 1 : 1;
                       n |= n >>> 1; n |= n >>> 2;  n |= n >>> 4;
                       n |= n >>> 8; n |= n >>> 16; n = (n + 1) << 1;
                       workQueues = new WorkQueue[n];
                       ns = STARTED;
                   }
               } finally {
                 	//解锁并更新runState
                   unlockRunState(rs, (rs & ~RSLOCK) | ns);
               }
           }
           else if ((q = ws[k = r & m & SQMASK]) != null) {//计算获取偶数槽位的workQueue
               if (q.qlock == 0 && U.compareAndSwapInt(q, QLOCK, 0, 1)) {//锁定 workQueue
                   ForkJoinTask<?>[] a = q.array;//当前workQueue的全部任务
                   int s = q.top;
                   boolean submitted = false; // initial submission or resizing
                   try {                      // locked version of push
                       if ((a != null && a.length > s + 1 - q.base) ||
                           (a = q.growArray()) != null) {//扩容
                           int j = (((a.length - 1) & s) << ASHIFT) + ABASE;
                           U.putOrderedObject(a, j, task);//放入给定任务
                           U.putOrderedInt(q, QTOP, s + 1);//修改push slot
                           submitted = true;
                       }
                   } finally {
                       U.compareAndSwapInt(q, QLOCK, 1, 0);//解除锁定
                   }
   	              //任务提交成功，创建或激活工作线程
                   if (submitted) {
                       signalWork(ws, q);//创建或激活一个工作线程来运行任务
                       return;
                   }
               }
               move = true;                   // move on failure
           }
           else if (((rs = runState) & RSLOCK) == 0) { // 初始化WorkQueue
               q = new WorkQueue(this, null);
               q.hint = r;
               q.config = k | SHARED_QUEUE;
               q.scanState = INACTIVE;
               rs = lockRunState();           // publish index
               if (rs > 0 &&  (ws = workQueues) != null &&
                   k < ws.length && ws[k] == null)
                   ws[k] = q;                 // 更新索引k位值的workQueue
               unlockRunState(rs, rs & ~RSLOCK);
           }
           else
               move = true;                   // move if busy
           if (move)
               r = ThreadLocalRandom.advanceProbe(r);//重新获取线程探针值
       }
   }
   ```

**说明**： `externalSubmit`是`externalPush`的完整版本，主要用于第一次提交任务时初始化`workQueues`及相关属性，并且提交给定任务到队列中。具体执行步骤如下：

1. 如果池为终止状态(`runState<0`)，调用`tryTerminate`来终止线程池，并抛出任务拒绝异常；
2. 如果尚未初始化，就为 ForkJoinPool 执行初始化操作：初始化`stealCounter`、创建`workerQueues`，然后继续自旋；
3. 初始化完成后，执行在`externalPush`中相同的操作：获取 workQueue，放入指定任务。任务提交成功后调用`signalWork`方法创建或激活线程；
4. 如果在步骤3中获取到的 workQueue 为`null`，会在这一步中创建一个 workQueue，创建成功继续自旋执行第三步操作；
5. 如果非上述情况，或者有线程争用资源导致获取锁失败，就重新获取线程探针值继续自旋。



3. signalWork(WorkQueue[] ws, WorkQueue q)

```java
final void signalWork(WorkQueue[] ws, WorkQueue q) {
    long c; int sp, i; WorkQueue v; Thread p;
    while ((c = ctl) < 0L) {                       // too few active
        if ((sp = (int)c) == 0) {                  // no idle workers
            if ((c & ADD_WORKER) != 0L)            // too few workers
                tryAddWorker(c);//工作线程太少，添加新的工作线程
            break;
        }
        if (ws == null)                            // unstarted/terminated
            break;
        if (ws.length <= (i = sp & SMASK))         // terminated
            break;
        if ((v = ws[i]) == null)                   // terminating
            break;
      	//计算ctl，加上版本戳SS_SEQ避免ABA问题
        int vs = (sp + SS_SEQ) & ~INACTIVE;        // next scanState
        int d = sp - v.scanState;                  // screen CAS
	      //计算活跃线程数（高32位）并更新为下一个栈顶的scanState（低32位）
        long nc = (UC_MASK & (c + AC_UNIT)) | (SP_MASK & v.stackPred);
        if (d == 0 && U.compareAndSwapLong(this, CTL, c, nc)) {
            v.scanState = vs;                      // activate v
            if ((p = v.parker) != null)
                U.unpark(p);//唤醒阻塞线程
            break;
        }
        if (q != null && q.base == q.top)          // no more work
            break;
    }
}
```

**说明**：新建或唤醒一个工作线程，在`externalPush`、`externalSubmit`、`workQueue.push`、`scan`中调用。如果还有空闲线程，则尝试唤醒索引到的 WorkQueue 的`parker`线程；如果工作线程过少（`(ctl & ADD_WORKER) != 0L`），则调用`tryAddWorker`添加一个新的工作线程。



4. tryAddWorker(long c)

```java
private void tryAddWorker(long c) {
    boolean add = false;
    do {
        long nc = ((AC_MASK & (c + AC_UNIT)) |
                   (TC_MASK & (c + TC_UNIT)));
        if (ctl == c) {
            int rs, stop;                 // check if terminating
            if ((stop = (rs = lockRunState()) & STOP) == 0)
                add = U.compareAndSwapLong(this, CTL, c, nc);
            unlockRunState(rs, rs & ~RSLOCK);//释放锁
            if (stop != 0)
                break;
            if (add) {
                createWorker();//创建工作线程
                break;
            }
        }
    } while (((c = ctl) & ADD_WORKER) != 0L && (int)c == 0);
}
```

**说明**：尝试添加一个新的工作线程，首先更新`ctl`中的工作线程数，然后调用`createWorker()`创建工作线程。



5. createWorker()

```java
private boolean createWorker() {
    ForkJoinWorkerThreadFactory fac = factory;
    Throwable ex = null;
    ForkJoinWorkerThread wt = null;
    try {
        if (fac != null && (wt = fac.newThread(this)) != null) {
            wt.start();
            return true;
        }
    } catch (Throwable rex) {
        ex = rex;
    }
    deregisterWorker(wt, ex);//线程创建失败处理 取消注册
    return false;
}
```

**说明**：`createWorker`首先通过线程工厂创一个新的`ForkJoinWorkerThread`，然后启动这个工作线程（`wt.start()`）。如果期间发生异常，调用`deregisterWorker`处理线程创建失败的逻辑（`deregisterWorker`在后面再详细说明）。

ForkJoinWorkerThread 的构造函数如下：

```java
protected ForkJoinWorkerThread(ForkJoinPool pool) {
    // Use a placeholder until a useful name can be set in registerWorker
    super("aForkJoinWorkerThread");//Thread
    this.pool = pool;
    this.workQueue = pool.registerWorker(this);
}
```



6. registerWorker(ForkJoinWorkerThread wt)

```java
final WorkQueue registerWorker(ForkJoinWorkerThread wt) {
    UncaughtExceptionHandler handler;
	  //设置为守护线程
    wt.setDaemon(true);                           // configure thread
    if ((handler = ueh) != null)
        wt.setUncaughtExceptionHandler(handler);
  	//构造新的WorkQueue，task queue 包含forkjoinpool和worker
    WorkQueue w = new WorkQueue(this, wt);
    int i = 0;                                    // assign a pool index
    int mode = config & MODE_MASK;
    int rs = lockRunState();
    try {
        WorkQueue[] ws; int n;                    // skip if no array
        if ((ws = workQueues) != null && (n = ws.length) > 0) {
	          //生成新建WorkQueue的索引
            int s = indexSeed += SEED_INCREMENT;  // unlikely to collide
            int m = n - 1;
            i = ((s << 1) | 1) & m;               // Worker任务放在奇数索引位 
            if (ws[i] != null) {                  // collision 已存在，重新计算索引位
                int probes = 0;                   // step by approx half n
                int step = (n <= 4) ? 2 : ((n >>> 1) & EVENMASK) + 2;
              	//查找可用的索引位
                while (ws[i = (i + step) & m] != null) {
                    if (++probes >= n) {//所有索引位都被占用，对workQueues进行扩容
                        workQueues = ws = Arrays.copyOf(ws, n <<= 1);//扩容
                        m = n - 1;
                        probes = 0;
                    }
                }
            }
            w.hint = s;                           // use as random seed
            w.config = i | mode;
            w.scanState = i;                      // publication fence
            ws[i] = w;
        }
    } finally {
        unlockRunState(rs, rs & ~RSLOCK);
    }
    wt.setName(workerNamePrefix.concat(Integer.toString(i >>> 1)));
    return w;
}
```

**说明**：`registerWorker`是 ForkJoinWorkerThread 构造器的回调函数，用于创建和记录工作线程的 WorkQueue。在此为工作线程创建的 WorkQueue 是放在奇数索引位的（代码行：`i = ((s << 1) | 1) & m;`）。



###### 子任务提交

子任务的提交相对比较简单，由任务的`fork()`方法完成。通过上面的流程图可以看到任务被分割（fork）之后调用了`ForkJoinPool.WorkQueue.push()`方法直接把任务放到队列中等待被执行。

1. ForkJoinTask.fork()

```java
public final ForkJoinTask<V> fork() {
    Thread t;
    if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread)
        ((ForkJoinWorkerThread)t).workQueue.push(this);
    else
        ForkJoinPool.common.externalPush(this);
    return this;
}
```

**说明**：如果当前线程是 Worker 线程，说明当前任务是fork分割的子任务，通过`ForkJoinPool.workQueue.push()`方法直接把任务放到自己的等待队列中；否则调用`ForkJoinPool.externalPush()`提交到一个随机的等待队列中（外部任务）。

2. WorkQueue.push(ForkJoinTask<?> task)

```java
final void push(ForkJoinTask<?> task) {
    ForkJoinTask<?>[] a; ForkJoinPool p;
    int b = base, s = top, n;
    if ((a = array) != null) {    // ignore if queue removed
        int m = a.length - 1;     // fenced write for task visibility
        U.putOrderedObject(a, ((m & s) << ASHIFT) + ABASE, task);
        U.putOrderedInt(this, QTOP, s + 1);
        if ((n = s - b) <= 1) {
            //首次提交，创建或唤醒一个工作线程
            if ((p = pool) != null)
                p.signalWork(p.workQueues, this);
        }
        else if (n >= m)
            growArray();
    }
}
```

**说明**：首先把任务放入等待队列并更新`top`位；如果当前 WorkQueue 为新建的等待队列（`top-base<=1`），则调用`signalWork`方法为当前 WorkQueue 新建或唤醒一个工作线程；如果 WorkQueue 中的任务数组容量过小，则调用`growArray()`方法对其进行两倍扩容，`growArray()`方法源码如下：

```java
final ForkJoinTask<?>[] growArray() {
    ForkJoinTask<?>[] oldA = array;//获取内部任务列表
    int size = oldA != null ? oldA.length << 1 : INITIAL_QUEUE_CAPACITY;
    if (size > MAXIMUM_QUEUE_CAPACITY)
        throw new RejectedExecutionException("Queue capacity exceeded");
    int oldMask, t, b;
	  //新建一个两倍容量的任务数组
    ForkJoinTask<?>[] a = array = new ForkJoinTask<?>[size];
    if (oldA != null && (oldMask = oldA.length - 1) >= 0 &&
        (t = top) - (b = base) > 0) {
        int mask = size - 1;
	      //从老数组中拿出数据，放到新的数组中
        do { // emulate poll from old array, push to new array
            ForkJoinTask<?> x;
            int oldj = ((b & oldMask) << ASHIFT) + ABASE;
            int j    = ((b &    mask) << ASHIFT) + ABASE;
            x = (ForkJoinTask<?>)U.getObjectVolatile(oldA, oldj);
            if (x != null &&
                U.compareAndSwapObject(oldA, oldj, x, null))
                U.putObjectVolatile(a, j, x);
        } while (++b != t);
    }
    return a;
}
```



###### 执行任务

ForkJoinWorkerThread 的`run()`方法正式开始执行任务。

1. ForkJoinWorkerThread.run()

```java
public void run() {
    if (workQueue.array == null) { // only run once
        Throwable exception = null;
        try {
            onStart();//钩子方法，可自定义扩展
            pool.runWorker(workQueue);
        } catch (Throwable ex) {
            exception = ex;
        } finally {
            try {
             	 //钩子方法，可自定义扩展
                onTermination(exception);
            } catch (Throwable ex) {
                if (exception == null)
                    exception = ex;
            } finally {
              	//处理异常
                pool.deregisterWorker(this, exception);
            }
        }
    }
}
```

**说明**：在工作线程运行前后会调用自定义钩子函数（`onStart`和`onTermination`），任务的运行则是调用了`ForkJoinPool.runWorker()`。如果全部任务执行完毕或者期间遭遇异常，则通过`ForkJoinPool.deregisterWorker`关闭工作线程并处理异常信息

2. ForkJoinPool.runWorker(WorkQueue w)

```java
final void runWorker(WorkQueue w) {
    w.growArray();                   // allocate queue
    int seed = w.hint;               // initially holds randomization hint
    int r = (seed == 0) ? 1 : seed;  // avoid 0 for xorShift
    for (ForkJoinTask<?> t;;) {
        if ((t = scan(w, r)) != null)//扫描任务执行
            w.runTask(t);
        else if (!awaitWork(w, r))
            break;
        r ^= r << 13; r ^= r >>> 17; r ^= r << 5; // xorshift
    }
}
```

**说明**：`runWorker`是 ForkJoinWorkerThread 的主运行方法，用来依次执行当前工作线程中的任务。函数流程很简单：调用`scan`方法依次获取任务，然后调用`WorkQueue .runTask`运行任务；如果未扫描到任务，则调用`awaitWork`等待，直到工作线程/线程池终止或等待超时。

3. ForkJoinPool.scan(WorkQueue w, int r)

```java
private ForkJoinTask<?> scan(WorkQueue w, int r) {
    WorkQueue[] ws; int m;
    if ((ws = workQueues) != null && (m = ws.length - 1) > 0 && w != null) {
        int ss = w.scanState;                     // initially non-negative
        //初始扫描起点，自旋扫描
        for (int origin = r & m, k = origin, oldSum = 0, checkSum = 0;;) {
            WorkQueue q; ForkJoinTask<?>[] a; ForkJoinTask<?> t;
            int b, n; long c;
            if ((q = ws[k]) != null) {//获取workQueue
                if ((n = (b = q.base) - q.top) < 0 &&
                        (a = q.array) != null) {      // non-empty
                    //计算偏移量
                    long i = (((a.length - 1) & b) << ASHIFT) + ABASE;
                    if ((t = ((ForkJoinTask<?>)
                            U.getObjectVolatile(a, i))) != null &&//取base位置任务
                            q.base == b) {
                        if (ss >= 0) {
                            if (U.compareAndSwapObject(a, i, t, null)) {
                                q.base = b + 1;//更新base位
                                if (n < -1)       // signal others 有多余的任务
                                    signalWork(ws, q);//创建或唤醒工作线程来运行任务
                                return t;
                            }
                        }
                        else if (oldSum == 0 &&   // try to activate 尝试激活工作线程
                                w.scanState < 0)
                            tryRelease(c = ctl, ws[m & (int)c], AC_UNIT);//唤醒栈顶工作线程
                    }
                    //base位置任务为空或base位置偏移，随机移位重新扫描
                    if (ss < 0)                   // refresh
                        ss = w.scanState;
                    r ^= r << 1; r ^= r >>> 3; r ^= r << 10;
                    origin = k = r & m;           // move and rescan
                    oldSum = checkSum = 0;
                    continue;
                }
                checkSum += b;//队列任务为空，记录base位
            }
            //更新索引k 继续向后查找
            if ((k = (k + 1) & m) == origin) {    // continue until stable
                //运行到这里说明已经扫描了全部的 workQueues，但并未扫描到任务
                if ((ss >= 0 || (ss == (ss = w.scanState))) &&
                        oldSum == (oldSum = checkSum)) {
                    if (ss < 0 || w.qlock < 0)    // already inactive
                        break;// 已经被灭活或终止,跳出循环
                    //对当前WorkQueue进行灭活操作
                    int ns = ss | INACTIVE;       // try to inactivate
                    //计算ctl为INACTIVE状态并减少活跃线程数
                    long nc = ((SP_MASK & ns) |
                            (UC_MASK & ((c = ctl) - AC_UNIT)));
                    w.stackPred = (int)c;         // hold prev stack top
                    U.putInt(w, QSCANSTATE, ns);//修改scanState为inactive状态
                    if (U.compareAndSwapLong(this, CTL, c, nc))//更新scanState为灭活状态
                        ss = ns;
                    else
                        w.scanState = ss;         // back out
                }
                checkSum = 0;//重置checkSum，继续循环
            }
        }
    }
    return null;
}
```

**说明**：扫描并尝试偷取一个任务。使用`w.hint`进行随机索引 WorkQueue，也就是说并不一定会执行当前 WorkQueue 中的任务，而是偷取别的Worker的任务来执行。

函数的大概执行流程如下：

1. 取随机位置的一个 WorkQueue；
2. 获取`base`位的 ForkJoinTask，成功取到后更新`base`位并返回任务；如果取到的 WorkQueue 中任务数大于1，则调用`signalWork`创建或唤醒其他工作线程；
3. 如果当前队列的工作线程处于不活跃状态（`INACTIVE`），则调用`tryRelease`尝试唤醒栈顶工作线程来执行。
4. 如果`base`位任务为空或发生偏移，则对索引位进行随机移位，然后重新扫描；
5. 如果扫描整个`workQueues`之后没有获取到任务，则设置当前工作线程为`INACTIVE`状态；然后重置`checkSum`，再次扫描一圈之后如果还没有任务则跳出循环返回`null`。
6. tryRelease`源码如下：

```java
private boolean tryRelease(long c, WorkQueue v, long inc) {
    int sp = (int)c, vs = (sp + SS_SEQ) & ~INACTIVE; Thread p;
  	//ctl低32位等于scanState，说明可以唤醒parker线程
    if (v != null && v.scanState == sp) {          // v is at top of stack
      	 //计算活跃线程数（高32位）并更新为下一个栈顶的scanState（低32位）
        long nc = (UC_MASK & (c + inc)) | (SP_MASK & v.stackPred);
        if (U.compareAndSwapLong(this, CTL, c, nc)) {
            v.scanState = vs;
            if ((p = v.parker) != null)
                U.unpark(p);//唤醒线程
            return true;
        }
    }
    return false;
}
```



4. ForkJoinPool.awaitWork(WorkQueue w, int r)

```java
private boolean awaitWork(WorkQueue w, int r) {
    if (w == null || w.qlock < 0)                 // w is terminating
        return false;
    for (int pred = w.stackPred, spins = SPINS, ss;;) {
        if ((ss = w.scanState) >= 0)//正在扫描，跳出循环
            break;
        else if (spins > 0) {
            r ^= r << 6; r ^= r >>> 21; r ^= r << 7;
            if (r >= 0 && --spins == 0) {         // randomize spins
                WorkQueue v; WorkQueue[] ws; int s, j; AtomicLong sc;
                if (pred != 0 && (ws = workQueues) != null &&
                    (j = pred & SMASK) < ws.length &&
                    (v = ws[j]) != null &&        // see if pred parking
                    (v.parker == null || v.scanState >= 0))
                    spins = SPINS;                // continue spinning
            }
        }
        else if (w.qlock < 0) //当前workQueue已经终止，返回false recheck after spins
            return false;
        else if (!Thread.interrupted()) {//判断线程是否被中断，并清除中断状态
            long c, prevctl, parkTime, deadline;
            int ac = (int)((c = ctl) >> AC_SHIFT) + (config & SMASK);//活跃线程数
            if ((ac <= 0 && tryTerminate(false, false)) ||
                (runState & STOP) != 0)  //无active线程，尝试终止 pool terminating
                return false;
            if (ac <= 0 && ss == (int)c) {        // is last waiter
              	//计算活跃线程数（高32位）并更新为下一个栈顶的scanState（低32位）
                prevctl = (UC_MASK & (c + AC_UNIT)) | (SP_MASK & pred);
                int t = (short)(c >>> TC_SHIFT);  // shrink excess spares
                if (t > 2 && U.compareAndSwapLong(this, CTL, c, prevctl))//总线程过量
                    return false;                 // else use timed wait
              	//计算空闲超时时间
                parkTime = IDLE_TIMEOUT * ((t >= 0) ? 1 : 1 - t);
                deadline = System.nanoTime() + parkTime - TIMEOUT_SLOP;
            }
            else
                prevctl = parkTime = deadline = 0L;
            Thread wt = Thread.currentThread();
            U.putObject(wt, PARKBLOCKER, this);   // emulate LockSupport
          	//设置parker，准备阻塞
            w.parker = wt;
            if (w.scanState < 0 && ctl == c)      // recheck before park
                U.park(false, parkTime);//阻塞指定的时间
            U.putOrderedObject(w, QPARKER, null);
            U.putObject(wt, PARKBLOCKER, null);
            if (w.scanState >= 0)//正在扫描，说明等到任务，跳出循环
                break;
            if (parkTime != 0L && ctl == c &&
                deadline - System.nanoTime() <= 0L &&
                U.compareAndSwapLong(this, CTL, c, prevctl))//未等到任务，更新ctl，返回false
                return false;                     // shrink pool
        }
    }
    return true;
}
```

**说明**：回到`runWorker`方法，如果`scan`方法未扫描到任务，会调用`awaitWork`等待获取任务。

- 如果工作线程或线程池已经终止则直接返回`false`；
- 如果当前无 active 线程，尝试终止线程池并返回`false`；
- 如果终止失败并且当前是最后一个等待的 Worker，就阻塞指定的时间（`IDLE_TIMEOUT`）；等到届期或被唤醒后如果发现自己是`scanning`（`scanState >= 0`）状态，说明已经等到任务，跳出等待返回`true`继续 scan，否则的更新`ctl`并返回`false`。

5. WorkQueue.runTask(ForkJoinTask<?> task)

```java
final void runTask(ForkJoinTask<?> task) {
    if (task != null) {
        scanState &= ~SCANNING; // mark as busy
        (currentSteal = task).doExec();//更新currentSteal并执行任务
        U.putOrderedObject(this, QCURRENTSTEAL, null); // release for GC
        execLocalTasks();//依次执行本地任务
        ForkJoinWorkerThread thread = owner;
        if (++nsteals < 0)      // collect on overflow
            transferStealCount(pool);//增加偷取任务数
        scanState |= SCANNING;
        if (thread != null)
            thread.afterTopLevelExec();//执行钩子函数
    }
}
```

**说明**：在`scan`方法扫描到任务之后，调用`WorkQueue.runTask()`来执行获取到的任务，大概流程如下：

5.1、标记`scanState`为正在执行状态；

5.2、更新`currentSteal`为当前获取到的任务并执行它，任务的执行调用了`ForkJoinTask.doExec()`方法，源码如下：

```java
final int doExec() {
    int s; boolean completed;
    if ((s = status) >= 0) {
        try {
            completed = exec();
        } catch (Throwable rex) {
            return setExceptionalCompletion(rex);
        }
        if (completed)
            s = setCompletion(NORMAL);
    }
    return s;
}

#RecursiveTask
protected final boolean exec() {
        result = compute();//执行我们定义的方法
        return true;
    }
```

5.3、调用`execLocalTasks`依次执行当前WorkerQueue中的任务

```java
//执行并移除所有本地任务
final void execLocalTasks() {
    int b = base, m, s;
    ForkJoinTask<?>[] a = array;
    if (b - (s = top - 1) <= 0 && a != null &&
        (m = a.length - 1) >= 0) {
        if ((config & FIFO_QUEUE) == 0) {//FIFO模式
            for (ForkJoinTask<?> t;;) {
                if ((t = (ForkJoinTask<?>)U.getAndSetObject
                     (a, ((m & s) << ASHIFT) + ABASE, null)) == null)
                    break;
                U.putOrderedInt(this, QTOP, s);
                t.doExec();//执行
                if (base - (s = top - 1) > 0)
                    break;
            }
        }
        else
            pollAndExecAll();//LIFO模式执行，取base任务
    }
}

final void pollAndExecAll() {
            for (ForkJoinTask<?> t; (t = poll()) != null;)
                t.doExec();
        }
```

5.4、更新偷取任务数；

5.5、还原`scanState`并执行钩子函数。



6. ForkJoinPool.deregisterWorker(ForkJoinWorkerThread wt, Throwable ex)

```java
final void deregisterWorker(ForkJoinWorkerThread wt, Throwable ex) {
    WorkQueue w = null;
  	//1.移除workQueue
    if (wt != null && (w = wt.workQueue) != null) {//获取ForkJoinWorkerThread的等待队列
        WorkQueue[] ws;                           // remove index from array
        int idx = w.config & SMASK;//计算workQueue索引
        int rs = lockRunState();//获取runState锁和当前池运行状态
        if ((ws = workQueues) != null && ws.length > idx && ws[idx] == w)
            ws[idx] = null;//移除workQueue
        unlockRunState(rs, rs & ~RSLOCK);//解除runState锁	
    }
  	//2.减少CTL数
    long c;                                       // decrement counts
    do {} while (!U.compareAndSwapLong
                 (this, CTL, c = ctl, ((AC_MASK & (c - AC_UNIT)) |
                                       (TC_MASK & (c - TC_UNIT)) |
                                       (SP_MASK & c))));
  	//3.处理被移除workQueue内部相关参数
    if (w != null) {
        w.qlock = -1;                             // ensure set
        w.transferStealCount(this);
        w.cancelAll();                            // cancel remaining tasks
    }
  	//4.如果线程未终止，替换被移除的workQueue并唤醒内部线程
    for (;;) {                                    // possibly replace
        WorkQueue[] ws; int m, sp;
      	//尝试终止线程池
        if (tryTerminate(false, false) || w == null || w.array == null ||
            (runState & STOP) != 0 || (ws = workQueues) == null ||
            (m = ws.length - 1) < 0)              // already terminating
            break;
      	//唤醒被替换的线程，依赖于下一步
        if ((sp = (int)(c = ctl)) != 0) {         // wake up replacement
            if (tryRelease(c, ws[sp & m], AC_UNIT))
                break;
        }
      	//创建工作线程替换
        else if (ex != null && (c & ADD_WORKER) != 0L) {
            tryAddWorker(c);                      // create replacement
            break;
        }
        else                                      // don't need replacement
            break;
    }
  	//5.处理异常
    if (ex == null)                               // help clean on way out
        ForkJoinTask.helpExpungeStaleExceptions();
    else                                          // rethrow
        ForkJoinTask.rethrow(ex);
}
```

**说明**：`deregisterWorker`方法用于工作线程运行完毕之后终止线程或处理工作线程异常，主要就是清除已关闭的工作线程或回滚创建线程之前的操作，并把传入的异常抛给 ForkJoinTask 来处理。



###### 获取任务结果

1. ForkJoinTask.join() 合并任务结果

```java
public final V join() {
    int s;
    if ((s = doJoin() & DONE_MASK) != NORMAL)
        reportException(s);
    return getRawResult();
}

private int doJoin() {
        int s; Thread t; ForkJoinWorkerThread wt; ForkJoinPool.WorkQueue w;
        return (s = status) < 0 ? s :
            ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
            (w = (wt = (ForkJoinWorkerThread)t).workQueue).
            tryUnpush(this) && (s = doExec()) < 0 ? s :
            wt.pool.awaitJoin(w, this, 0L) :
            externalAwaitDone();
    }
```

2. ForkJoinTask.invoke()  执行任务，并等待任务完成并返回结果

```java
public final V invoke() {
    int s;
    if ((s = doInvoke() & DONE_MASK) != NORMAL)
        reportException(s);
    return getRawResult();
}

private int doInvoke() {
        int s; Thread t; ForkJoinWorkerThread wt;
        return (s = doExec()) < 0 ? s :
            ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) ?
            (wt = (ForkJoinWorkerThread)t).pool.
            awaitJoin(wt.workQueue, this, 0L) :
            externalAwaitDone();
    }
```

**说明：** `join()`方法一般是在任务`fork()`之后调用，用来获取（或者叫“合并”）任务的执行结果。

ForkJoinTask的`join()`和`invoke()`方法都可以用来获取任务的执行结果（另外还有`get`方法也是调用了`doJoin`来获取任务结果，但是会响应运行时异常），它们对外部提交任务的执行方式一致，都是通过`externalAwaitDone`方法等待执行结果。不同的是`invoke()`方法会直接执行当前任务；而`join()`方法则是在当前任务在队列 top 位时（通过`tryUnpush`方法判断）才能执行，如果当前任务不在 top 位或者任务执行失败调用`ForkJoinPool.awaitJoin`方法帮助执行或阻塞当前 join 任务。join()的执行流程：

![ForkJoinPool-work-queue-Join流程](forkjoin.assets/ForkJoinPool-work-queue-Join流程.svg)



```java
private int externalAwaitDone() {
	  //执行任务
    int s = ((this instanceof CountedCompleter) ? // try helping
             // CountedCompleter任务
             ForkJoinPool.common.externalHelpComplete(
                 (CountedCompleter<?>)this, 0) :
             ForkJoinPool.common.tryExternalUnpush(this) ? doExec() : 0);
  	//执行失败，进入等待
    if (s >= 0 && (s = status) >= 0) {
        boolean interrupted = false;
        do {
          	//更新state
            if (U.compareAndSwapInt(this, STATUS, s, s | SIGNAL)) {
                synchronized (this) {
                  	//SIGNAL 等待信号
                    if (status >= 0) {
                        try {
                            wait(0L);
                        } catch (InterruptedException ie) {
                            interrupted = true;
                        }
                    }
                    else
                        notifyAll();
                }
            }
        } while ((s = status) >= 0);
        if (interrupted)
            Thread.currentThread().interrupt();
    }
    return s;
}
```

**说明：** 如果当前`join`为外部调用，则调用此方法执行任务，如果任务执行失败就进入等待。方法本身是很简单的，需要注意的是对不同的任务类型分两种情况：

- 如果我们的任务为 CountedCompleter 类型的任务，则调用`externalHelpComplete`方法来执行任务。

- 其他类型的 ForkJoinTask 任务调用`tryExternalUnpush`来执行，源码如下：

  ```java
  //为外部提交者提供 tryUnpush 功能（给定任务在top位时弹出任务）
  final boolean tryExternalUnpush(ForkJoinTask<?> task) {
      WorkQueue[] ws; WorkQueue w; ForkJoinTask<?>[] a; int m, s;
      int r = ThreadLocalRandom.getProbe();
      if ((ws = workQueues) != null && (m = ws.length - 1) >= 0 &&
          (w = ws[m & r & SQMASK]) != null &&
          (a = w.array) != null && (s = w.top) != w.base) {
        	//取top位任务
          long j = (((a.length - 1) & (s - 1)) << ASHIFT) + ABASE;
          if (U.compareAndSwapInt(w, QLOCK, 0, 1)) {//加锁
              if (w.top == s && w.array == a &&
                  U.getObject(a, j) == task &&
                  U.compareAndSwapObject(a, j, task, null)) {//符合条件，弹出
                  U.putOrderedInt(w, QTOP, s - 1);//更新top
                  U.putOrderedInt(w, QLOCK, 0);//解锁，返回true
                  return true;
              }
              U.compareAndSwapInt(w, QLOCK, 1, 0);//当前任务不在top位，解锁返回false
          }
      }
      return false;
  }
  ```

`tryExternalUnpush`的作用就是判断当前任务是否在`top`位，如果是则弹出任务，然后在`externalAwaitDone`中调用`doExec()`执行任务。



3. ForkJoinPool.awaitJoin()

```java
final int awaitJoin(WorkQueue w, ForkJoinTask<?> task, long deadline) {
    int s = 0;
    if (task != null && w != null) {
        ForkJoinTask<?> prevJoin = w.currentJoin;//获取给定Worker的join任务
        U.putOrderedObject(w, QCURRENTJOIN, task);//把currentJoin替换为给定任务
      	//判断是否为CountedCompleter类型的任务
        CountedCompleter<?> cc = (task instanceof CountedCompleter) ?
            (CountedCompleter<?>)task : null;
        for (;;) {
            if ((s = task.status) < 0)//已经完成|取消|异常 跳出循环
                break;
            if (cc != null)
                helpComplete(w, cc, 0);//CountedCompleter任务由helpComplete来完成join
            else if (w.base == w.top || w.tryRemoveAndExec(task))//尝试执行
                helpStealer(w, task);//队列为空或执行失败，任务可能被偷，帮助偷取者执行该任务
            if ((s = task.status) < 0)//已经完成|取消|异常，跳出循环
                break;
	          //计算任务等待时间
            long ms, ns;
            if (deadline == 0L)
                ms = 0L;
            else if ((ns = deadline - System.nanoTime()) <= 0L)
                break;
            else if ((ms = TimeUnit.NANOSECONDS.toMillis(ns)) <= 0L)
                ms = 1L;
            if (tryCompensate(w)) {//执行补偿操作
                task.internalWait(ms);//补偿执行成功，任务等待指定时间
                U.getAndAddLong(this, CTL, AC_UNIT);//更新活跃线程数
            }
        }
        U.putOrderedObject(w, QCURRENTJOIN, prevJoin);//循环结束，替换为原来的join任务
    }
    return s;
}
```

**说明：** 如果当前 join 任务不在Worker等待队列的`top`位，或者任务执行失败，调用此方法来帮助执行或阻塞当前 join 的任务。函数执行流程如下：

- 由于每次调用`awaitJoin`都会优先执行当前join的任务，所以首先会更新`currentJoin`为当前join任务；
- 进入自旋：

1. 首先检查任务是否已经完成（通过`task.status < 0`判断），如果给定任务执行完毕|取消|异常 则跳出循环返回执行状态`s`；

2. 如果是 CountedCompleter 任务类型，调用`helpComplete`方法来完成join操作

3. 非 CountedCompleter 任务类型调用`WorkQueue.tryRemoveAndExec`尝试执行任务；

4. 如果给定 WorkQueue 的等待队列为空或任务执行失败，说明任务可能被偷，调用`helpStealer`帮助偷取者执行任务（也就是说，偷取者帮我执行任务，我去帮偷取者执行它的任务）；

5. 再次判断任务是否执行完毕（`task.status < 0`），如果任务执行失败，计算一个等待时间准备进行补偿操作；

6. 调用`tryCompensate`方法为给定 WorkQueue 尝试执行补偿操作。在执行补偿期间，如果发现 资源争用|池处于unstable状态|当前Worker已终止，则调用`ForkJoinTask.internalWait()`方法等待指定的时间，任务唤醒之后继续自旋，`ForkJoinTask.internalWait()`源码如下：

   ```java
   final void internalWait(long timeout) {
       int s;
       if ((s = status) >= 0 && // force completer to issue notify
           U.compareAndSwapInt(this, STATUS, s, s | SIGNAL)) {//更新任务状态为SIGNAL(等待唤醒)
           synchronized (this) {
               if (status >= 0)
                   try { wait(timeout); } catch (InterruptedException ie) { }
               else
                   notifyAll();
           }
       }
   }
   ```



3.1 WorkQueue.tryRemoveAndExec(ForkJoinTask<?> task)

```java
final boolean tryRemoveAndExec(ForkJoinTask<?> task) {
    ForkJoinTask<?>[] a;
    int m, s, b, n;
    if ((a = array) != null && (m = a.length - 1) >= 0 &&
            task != null) {
        while ((n = (s = top) - (b = base)) > 0) {
            //从top往下自旋查找
            for (ForkJoinTask<?> t; ; ) {      // traverse from s to b
                long j = ((--s & m) << ASHIFT) + ABASE;//计算任务索引
                if ((t = (ForkJoinTask<?>) U.getObject(a, j)) == null) //获取索引到的任务
                    return s + 1 == top;     // shorter than expected
                else if (t == task) { //给定任务为索引任务
                    boolean removed = false;
                    if (s + 1 == top) {      // pop
                        if (U.compareAndSwapObject(a, j, task, null)) { //弹出任务
                            U.putOrderedInt(this, QTOP, s); //更新top
                            removed = true;
                        }
                    } else if (base == b)      // replace with proxy
                        removed = U.compareAndSwapObject(
                                a, j, task, new EmptyTask()); //join任务已经被移除，替换为一个占位任务
                    if (removed)
                        task.doExec(); //执行
                    break;
                } else if (t.status < 0 && s + 1 == top) { //给定任务不是top任务
                    if (U.compareAndSwapObject(a, j, t, null)) //弹出任务
                        U.putOrderedInt(this, QTOP, s);//更新top
                    break;                  // was cancelled
                }
                if (--n == 0) //遍历结束
                    return false;
            }
            if (task.status < 0) //任务执行完毕
                return false;
        }
    }
    return true;
}
```

**说明：** 从`top`位开始自旋向下找到给定任务，如果找到把它从当前 Worker 的任务队列中移除并执行它。注意返回的参数：如果任务队列为空或者任务**未**执行完毕返回`true`；任务执行完毕返回`false`。



3.2 ForkJoinPool.helpStealer(WorkQueue w, ForkJoinTask<?> task)

```java
private void helpStealer(WorkQueue w, ForkJoinTask<?> task) {
    WorkQueue[] ws = workQueues;
    int oldSum = 0, checkSum, m;
    if (ws != null && (m = ws.length - 1) >= 0 && w != null &&
            task != null) {
        do {                                       // restart point
            checkSum = 0;                          // for stability check
            ForkJoinTask<?> subtask;
            WorkQueue j = w, v;                    // v is subtask stealer
            descent:
            for (subtask = task; subtask.status >= 0; ) {
                //1. 找到给定WorkQueue的偷取者v
                for (int h = j.hint | 1, k = 0, i; ; k += 2) {//跳两个索引，因为Worker在奇数索引位
                    if (k > m)                     // can't find stealer
                        break descent;
                    if ((v = ws[i = (h + k) & m]) != null) {
                        if (v.currentSteal == subtask) {//定位到偷取者
                            j.hint = i;//更新stealer索引
                            break;
                        }
                        checkSum += v.base;
                    }
                }
                //2. 帮助偷取者v执行任务
                for (; ; ) {                         // help v or descend
                    ForkJoinTask<?>[] a;            //偷取者内部的任务
                    int b;
                    checkSum += (b = v.base);
                    ForkJoinTask<?> next = v.currentJoin;//获取偷取者的join任务
                    if (subtask.status < 0 || j.currentJoin != subtask ||
                            v.currentSteal != subtask) // stale
                        break descent; // stale，跳出descent循环重来
                    if (b - v.top >= 0 || (a = v.array) == null) {
                        if ((subtask = next) == null)   //偷取者的join任务为null，跳出descent循环
                            break descent;
                        j = v;
                        break; //偷取者内部任务为空，可能任务也被偷走了；跳出本次循环，查找偷取者的偷取者
                    }
                    int i = (((a.length - 1) & b) << ASHIFT) + ABASE;//获取base偏移地址
                    ForkJoinTask<?> t = ((ForkJoinTask<?>)
                            U.getObjectVolatile(a, i));//获取偷取者的base任务
                    if (v.base == b) {
                        if (t == null)             // stale
                            break descent; // stale，跳出descent循环重来
                        if (U.compareAndSwapObject(a, i, t, null)) {//弹出任务
                            v.base = b + 1;         //更新偷取者的base位
                            ForkJoinTask<?> ps = w.currentSteal;//获取调用者偷来的任务
                            int top = w.top;
                            //首先更新给定workQueue的currentSteal为偷取者的base任务，然后执行该任务
                            //然后通过检查top来判断给定workQueue是否有自己的任务，如果有，
                            // 则依次弹出任务(LIFO)->更新currentSteal->执行该任务（注意这里是自己偷自己的任务执行）
                            do {
                                U.putOrderedObject(w, QCURRENTSTEAL, t);
                                t.doExec();        // clear local tasks too
                            } while (task.status >= 0 &&
                                    w.top != top && //内部有自己的任务，依次弹出执行
                                    (t = w.pop()) != null);
                            U.putOrderedObject(w, QCURRENTSTEAL, ps);//还原给定workQueue的currentSteal
                            if (w.base != w.top)//给定workQueue有自己的任务了，帮助结束，返回
                                return;            // can't further help
                        }
                    }
                }
            }
        } while (task.status >= 0 && oldSum != (oldSum = checkSum));
    }
}
```

**说明：** 如果队列为空或任务执行失败，说明任务可能被偷，调用此方法来帮助偷取者执行任务。基本思想是：偷取者帮助我执行任务，我去帮助偷取者执行它的任务。
 函数执行流程如下：

1. 循环定位偷取者，由于Worker是在奇数索引位，所以每次会跳两个索引位。定位到偷取者之后，更新调用者 WorkQueue 的`hint`为偷取者的索引，方便下次定位；
2. 定位到偷取者后，开始帮助偷取者执行任务。从偷取者的`base`索引开始，每次偷取一个任务执行。在帮助偷取者执行任务后，如果调用者发现本身已经有任务（`w.top != top`），则依次弹出自己的任务(LIFO顺序)并执行（也就是说自己偷自己的任务执行）。



3.3 ForkJoinPool.tryCompensate(WorkQueue w)

```java
//执行补偿操作：尝试缩减活动线程量，可能释放或创建一个补偿线程来准备阻塞
private boolean tryCompensate(WorkQueue w) {
    boolean canBlock;
    WorkQueue[] ws;
    long c;
    int m, pc, sp;
    if (w == null || w.qlock < 0 ||           // caller terminating
            (ws = workQueues) == null || (m = ws.length - 1) <= 0 ||
            (pc = config & SMASK) == 0)           // parallelism disabled
        canBlock = false; //调用者已终止
    else if ((sp = (int) (c = ctl)) != 0)      // release idle worker
        canBlock = tryRelease(c, ws[sp & m], 0L);//唤醒等待的工作线程
    else {//没有空闲线程
        int ac = (int) (c >> AC_SHIFT) + pc; //活跃线程数
        int tc = (short) (c >> TC_SHIFT) + pc;//总线程数
        int nbusy = 0;                        // validate saturation
        for (int i = 0; i <= m; ++i) {        // two passes of odd indices
            WorkQueue v;
            if ((v = ws[((i << 1) | 1) & m]) != null) {//取奇数索引位
                if ((v.scanState & SCANNING) != 0)//没有正在运行任务，跳出
                    break;
                ++nbusy;//正在运行任务，添加标记
            }
        }
        if (nbusy != (tc << 1) || ctl != c)
            canBlock = false;                 // unstable or stale
        else if (tc >= pc && ac > 1 && w.isEmpty()) {//总线程数大于并行度 && 活动线程数大于1 && 调用者任务队列为空，不需要补偿
            long nc = ((AC_MASK & (c - AC_UNIT)) |
                    (~AC_MASK & c));       // uncompensated
            canBlock = U.compareAndSwapLong(this, CTL, c, nc);//更新活跃线程数
        } else if (tc >= MAX_CAP ||
                (this == common && tc >= pc + commonMaxSpares))//超出最大线程数
            throw new RejectedExecutionException(
                    "Thread limit exceeded replacing blocked worker");
        else {                                // similar to tryAddWorker
            boolean add = false;
            int rs;      // CAS within lock
            long nc = ((AC_MASK & c) |
                    (TC_MASK & (c + TC_UNIT)));//计算总线程数
            if (((rs = lockRunState()) & STOP) == 0)
                add = U.compareAndSwapLong(this, CTL, c, nc);//更新总线程数
            unlockRunState(rs, rs & ~RSLOCK);
            //运行到这里说明活跃工作线程数不足，需要创建一个新的工作线程来补偿
            canBlock = add && createWorker(); // throws on exception
        }
    }
    return canBlock;
}
```

**说明：** 具体的执行看源码及注释，这里我们简单总结一下需要和不需要补偿的几种情况：

- 需要补偿： 
  - 调用者队列不为空，并且有空闲工作线程，这种情况会唤醒空闲线程（调用`tryRelease`方法）
  - 池尚未停止，活跃线程数不足，这时会新建一个工作线程（调用`createWorker`方法）
- 不需要补偿： 
  - 调用者已终止或池处于不稳定状态
  - 总线程数大于并行度 && 活动线程数大于1 && 调用者任务队列为空。