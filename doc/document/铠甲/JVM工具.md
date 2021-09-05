#### JVM工具

Dump 文件分析工具：https://fastthread.io/#



JDK下面的工具程序都是调用的**tools.jar**、**sa-jdi.jar**类库下的函数，仅仅是对这个类库进行简单的封装，所以这些工具一般都很小。如果需要监控远程JVM的话，需要在启动时开启JMX管理功能（参数是：-Dcom.sun.management.jmxremote），当然还需要设置监控端口、用户名、密码、身份认证开关、SSL验证开关等。

##### JPS

JVM Process Status Tool，虚拟机进程状况工具，可以列出正在运行的虚拟机进程，并显示虚拟机执行主类名称，以及这些进程的本地虚拟机唯一ID（LVMID）。

命令格式：

> jps [ options ] [ hostid ]
>
> -q 输出虚拟机唯一ID，不输出类名、Jar名和传入main方法的参数
> -m 输出传入main方法的参数
> -l 输出main类或Jar的全名
> -v 输出虚拟机进程启动时JVM的参数
>
> 说明：如果不指定hostid就默认为当前主机或服务器，参数可以联合使用，比如*jps -lv*。



##### jstat

JVM Statistics Monitoring Tool，虚拟机统计信息监视工具，用于监视虚拟机各种运行状态信息的命令行工具，可以显示本地或远程虚拟机进程的类装载、内存、垃圾收集、JIT编译等运行数据。它是运行期定位虚拟机性能问题的首选工具。

命令格式：

> jstat [ option vmid [interval [s | ms] [count] ] ]
>
> jstat [-命令选项] [vmid] [间隔时间/毫秒] [查询次数]



1. 类加载

   > -class  监视类装载、卸载数量、总空间以及类装载所耗费的时间

   ```
   $ jstat -class 10780
   Loaded  Bytes  Unloaded  Bytes     Time   
     3295  6333.3        0     0.0       1.59
   ```

   - Loaded: 加载class的数量
   - Bytes： 所占用空间大小
   - Unloaded： 未加载数量
   - Bytes: 未加载占用空间
   - Time： 时间

2. 垃圾收集

   > -gc  监视Java堆状况，包括Eden区、两个Survivor区、老年代等容量、已用空间、GC时间合计等信息

   ```
   $ jstat -gc 10780
    S0C    S1C    S0U    S1U      EC       EU        OC         OU       MC     MU    CCSC   CCSU   YGC     YGCT    FGC    FGCT     GCT   
   5120.0 5120.0  0.0    0.0   33280.0   8683.0   87552.0      0.0     4480.0 779.9  384.0   76.4       0    0.000   0      0.000    0.000
   ```

   - S0C：第一个Survive区的大小
   - S1C：第二个Survive区的大小
   - S0U：第一个Survive区的使用大小
   - S1U：第二个Survive区的使用大小
   - EC：Eden区的大小
   - EU：Eden的使用大小
   - OC：老年代大小
   - OU：老年代使用大小
   - MC：方法区大小
   - MU：方法区使用大小
   - CCSC: 压缩类空间大小
   - CCSU: 压缩类空间使用大小
   - YGC：年轻代垃圾回收次数
   - YGCT：年轻代垃圾回收消耗时间
   - FGC：老年代垃圾回收次数
   - FGCT：老年代垃圾回收消耗时间
   - GCT：垃圾回收消耗总时间

3. 编译

   > -compiler  输出JIT编译器编译过的方法、耗时等信息

   ```
   $ jstat -compiler 10780
   Compiled Failed Invalid   Time   FailedType FailedMethod
        228      0       0     0.10          0     
   ```

   - Compiled：编译数量。
   - Failed：失败数量
   - Invalid：不可用数量
   - Time：时间
   - FailedType：失败类型
   - FailedMethod：失败的方法

4. 对内存

   > -gccapacity  监视内容与-gc基本相同，但输出主要关注Java堆各个区域使用到的最大、最小空间

   ```
   $ jstat -gccapacity 10780
    NGCMN    NGCMX     NGC     S0C   S1C       EC      OGCMN      OGCMX       OGC         OC       MCMN     MCMX      MC     CCSMN    CCSMX     CCSC    YGC    FGC 
    43520.0 698880.0  43520.0 5120.0 5120.0  33280.0    87552.0  1398272.0    87552.0    87552.0      0.0 1056768.0   4480.0      0.0 1048576.0    384.0      0     0
   ```

   - NGCMN：新生代最小容量
   - NGCMX：新生代最大容量
   - NGC：当前新生代容量
   - S0C：第一个Survive区大小
   - S1C：第二个Survive区的大小
   - EC：Eden区的大小
   - OGCMN：老年代最小容量
   - OGCMX：老年代最大容量
   - OGC：当前老年代大小
   - OC: 当前老年代大小
   - MCMN:最小元数据容量
   - MCMX：最大元数据容量
   - MC：当前元数据空间大小
   - CCSMN：最小压缩类空间大小
   - CCSMX：最大压缩类空间大小
   - CCSC：当前压缩类空间大小
   - YGC：年轻代gc次数
   - FGC：老年代GC次数

5. 新生代垃圾回收

   > -gcnew 监视新生代GC状况

   ```
   $ jstat -gcnew 10780
    S0C    S1C    S0U    S1U   TT MTT  DSS      EC       EU     YGC     YGCT  
   5120.0 5120.0    0.0    0.0 15  15    0.0  33280.0   8683.0      0    0.000
   ```

   - S0C：第一个Survive区大小
   - S1C：第二个Survive区的大小
   - S0U：第一个Survive区的使用大小
   - S1U：第二个Survive区的使用大小
   - TT:  对象在新生代存活的次数
   - MTT:  对象在新生代存活的最大次数
   - DSS:  期望的Survive区大小
   - EC：Eden区的大小
   - EU：Eden区的使用大小
   - YGC：年轻代垃圾回收次数
   - YGCT：年轻代垃圾回收消耗时间

6. 新生代内存

   > -gcnewcapacity  监视内容与-gcnew基本相同，主要输出使用到的最大、最小空间

   ```
   $ jstat -gcnewcapacity 10780
     NGCMN      NGCMX       NGC      S0CMX     S0C     S1CMX     S1C       ECMX        EC      YGC   FGC 
      43520.0   698880.0    43520.0 232960.0   5120.0 232960.0   5120.0   697856.0    33280.0     0     0
   ```

   - NGCMN：新生代最小容量
   - NGCMX：新生代最大容量
   - NGC：当前新生代容量
   - S0CMX：最大Survive1区大小
   - S0C：当前Survive1区大小
   - S1CMX：最大Survive2区大小
   - S1C：当前Survive2区大小
   - ECMX：最大Eden区大小
   - EC：当前Eden区大小
   - YGC：年轻代垃圾回收次数
   - FGC：老年代回收次数

7. 老年代垃圾回收

   > -gcold 监视老年代GC状况

   ```
   $ jstat -gcold 10780
      MC       MU      CCSC     CCSU       OC          OU       YGC    FGC    FGCT     GCT   
     4480.0    779.9    384.0     76.4     87552.0         0.0      0     0    0.000    0.000
   ```

   - MC：方法区大小
   - MU：方法区使用大小
   - CCSC:压缩类空间大小
   - CCSU:压缩类空间使用大小
   - OC：老年代大小
   - OU：老年代使用大小
   - YGC：年轻代垃圾回收次数
   - FGC：老年代垃圾回收次数
   - FGCT：老年代垃圾回收消耗时间
   - GCT：垃圾回收消耗总时间

8. 老年代内存

   > -gcoldcapacity 监视内容与-gcold基本相同，主要输出使用到的最大、最小空间

   ```
   $ jstat -gcoldcapacity 12502
      OGCMN       OGCMX        OGC         OC       YGC   FGC    FGCT     GCT   
       87552.0   1398272.0     87552.0     87552.0     0     0    0.000    0.000
   ```

   - OGCMN：老年代最小容量
   - OGCMX：老年代最大容量
   - OGC：当前老年代大小
   - OC：老年代大小
   - YGC：年轻代垃圾回收次数
   - FGC：老年代垃圾回收次数
   - FGCT：老年代垃圾回收消耗时间
   - GCT：垃圾回收消耗总时间

9. 愿数据空间

   > -gcmetacapacity  监视元数据空间内存情况（jdk1.8之后）

   ```
   $ jstat -gcmetacapacity 12502
      MCMN       MCMX        MC       CCSMN      CCSMX       CCSC     YGC   FGC    FGCT     GCT   
          0.0  1056768.0     4480.0        0.0  1048576.0      384.0     0     0    0.000    0.000
   ```

   - MCMN:最小元数据容量
   - MCMX：最大元数据容量
   - MC：当前元数据空间大小
   - CCSMN：最小压缩类空间大小
   - CCSMX：最大压缩类空间大小
   - CCSC：当前压缩类空间大小
   - YGC：年轻代垃圾回收次数
   - FGC：老年代垃圾回收次数
   - FGCT：老年代垃圾回收消耗时间
   - GCT：垃圾回收消耗总时间

10. 垃圾回收统计

    > -gcutil 统计所有垃圾回收的数据

    ```
    $ jstat -gcutil 12502
      S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT   
      0.00   0.00  26.09   0.00  17.41  19.90      0    0.000     0    0.000    0.000
    ```

    - S0：Survive1区当前使用比例
    - S1：Survive2区当前使用比例
    - E：Eden区使用比例
    - O：老年代使用比例
    - M：元数据区使用比例
    - CCS：压缩使用比例
    - YGC：年轻代垃圾回收次数
    - FGC：老年代垃圾回收次数
    - FGCT：老年代垃圾回收消耗时间
    - GCT：垃圾回收消耗总时间

11. 编译方法

    > -printcompilation  输出已经被JIT编译的方法

    ```
    $ jstat -printcompilation 12502
    Compiled  Size  Type Method
         229     47    1 java/lang/StringBuffer toString
    ```

    - Compiled：最近编译方法的数量
    - Size：最近编译方法的字节码数量
    - Type：最近编译方法的编译类型。
    - Method：方法名标识。

##### jinfo

Java配置信息工具，**能实时地查看和调整虚拟机各项参数。**

命令格式：

> jinfo [option] pid



##### jmap

Memory Map for Java，Java内存映像工具，用于生成堆存储快照（一般称为heapdump或dump文件），还可以查询finalize执行队列、Java堆的详细信息，如空间使用率、当前用的是哪种收集器等。如果不适用jmap命令，可以使用-XX:+HeapDumpOnOutOfMemoryError参数，当虚拟机发生内存溢出的时候可以产生快照。或者使用kill -3 pid也可以产生

jmap命令格式：

> jmap [option] vmid

```
-dump:[live,]format=b,file=<filename> 使用hprof二进制形式,输出jvm的heap内容到文件=. live子选项是可选的，假如指定live选项,那么只输出活的对象到文件.
-finalizerinfo 打印正等候回收的对象的信息.
-heap 打印heap的概要信息，GC使用的算法，heap的配置及wise heap的使用情况.
-histo[:live] 打印每个class的实例数目,内存占用,类全名信息. VM的内部类名字开头会加上前缀”*”. 如果live子参数加上后,只统计活的对象数量.
-permstat 打印classload和jvm heap长久层的信息. 包含每个classloader的名字,活泼性,地址,父classloader和加载的class数量. 另外,内部String的数量和占用内存数也会打印出来.
-F 强迫.在pid没有相应的时候使用-dump或者-histo参数. 在这个模式下,live子参数无效.
-h | -help 打印辅助信息
-J 传递参数给jmap启动的jvm.
```



##### jhat

JVM Heap Analysis Tool，虚拟机堆转储快照分析工具，可与jmap配合使用，这个工具是用来分析jmap dump出来的文件。由于这个工具功能比较简陋，运行起来也比较耗时，所以这个工具不推荐使用，推荐使用MAT或Visual VM。

例如我要解析上面生成的dump文件可以用命令：

> jhat vote.dump



##### jstack

Stack Trace for Java，Java堆栈跟踪工具，用于生成虚拟机当前时刻的线程快照（一般称为threaddump或者javacore文件）。线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合，生成线程快照的主要目的是定位线程出现长时间停顿的原因，比如线程间死锁、死循环、请求外部资源导致的长时间等待等都是其常见原因。

jstack命令格式：

> jstack [option] vmid

参数含义：

```
-F  当正常输出的请求不被响应时，强制输出线程堆栈
-l  除堆栈外，显示关于锁的附加信息
-m  如果调用到本地方法的话，可以显示C/C++的堆栈
```



##### javap

```
-help  --help  -?        输出此用法消息
 -version                 版本信息，其实是当前javap所在jdk的版本信息，不是class在哪个jdk下生成的。
 -v  -verbose             输出附加信息（包括行号、本地变量表，反汇编等详细信息）
 -l                         输出行号和本地变量表
 -public                    仅显示公共类和成员
 -protected               显示受保护的/公共类和成员
 -package                 显示程序包/受保护的/公共类 和成员 (默认)
 -p  -private             显示所有类和成员
 -c                       对代码进行反汇编
 -s                       输出内部类型签名
 -sysinfo                 显示正在处理的类的系统信息 (路径, 大小, 日期, MD5 散列)
 -constants               显示静态最终常量
 -classpath <path>        指定查找用户类文件的位置
 -bootclasspath <path>    覆盖引导类文件的位置
```

一般常用的是-v -l -c三个选项。
 javap -v classxx，不仅会输出行号、本地变量表信息、反编译汇编代码，还会输出当前类用到的常量池等信息。
 javap -l 会输出行号和本地变量表信息。
 javap -c 会对当前class字节码进行反编译生成汇编代码。



##### Jconsol



##### VisualVM

visual gc插件
