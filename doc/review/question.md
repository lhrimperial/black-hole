### Question

- spring循环依赖
- 自增索引优势（性能-页分裂，空间-辅助索引）
- zk与redis选主区别
- spring如何管理bean
- threadlocal 子线程引用
- sycn优化，锁升级
- 栈的结构与执行过程，为什么选用栈
- 删除空表会不会加锁
- mybatis sqlsession，默认分页缺点
- hashmap为什么扩展2n
- mysql减少回表优化
- mysql rr隔离级别如何解决可重复读于幻读
- 高qps下频繁year GC 如何定位和保障稳定性
- RPC 核心需要哪些模块
- 缓存与DB一致性保障
- ConcurrentHashMap HashMap 扩容的时候加入数据
- Jdk1.8 为什么废弃了永久代




算法：
链表找倒数第k个数
二叉树找公共祖先





1. Spring 循环依赖，通过 Spring的三级缓存解决

   - 构造器注入循环依赖：此种循环依赖方式是无法解决的，Spring解决循环依赖依靠的是Bean的“中间态”这个概念，而这个中间态指的是`已经实例化`，但还没初始化的状态。
   - field属性注入（setter方法注入）循环依赖
   - prototype field属性注入循环依赖

2. Spring 三级缓存

   - singletonObjects：用于存放完全初始化好的 bean，从该缓存中取出的 bean 可以直接使用
   - earlySingletonObjects：提前曝光的单例对象的cache，存放原始的 bean 对象（尚未填充属性），用于解决循环依赖
   - singletonFactories：单例对象工厂的cache，存放 bean 工厂对象，用于解决循环依赖
   - singletonsCurrentlyInCreation：表示bean创建过程中都会在里面呆着，在Bean开始创建时放值，创建完成时会将其移出
   - alreadyCreated：当这个Bean被创建完成后，会标记为这个，至少被创建了一次的  都会放进这里

   https://cloud.tencent.com/developer/article/1497692

3. Spring bean 的生命周期

   doCreateBean 调用创建bean方法

   createBeanInstance 实例化bean，调用构造方法

   populateBean 填充属性

   BeanNameAware.setBeanName 

   BeanFactoryAware.setBeanFactory

   BeanPostProcesser.postProcessBeforeInitialization bean 后置处理器预处理

   InitializingBean.afterPropertiesSet 

   Init-method

   BeanPostProcesser.postProcessAfterInitialization

   可用的Spring bean

   DisposableBean.distroy()

4. mysql用自增主键的好处

   - 从空间方面考虑，int 或者bigint占用空间小
   - 查询性能方面，数据类型越小或平均每条记录所占用的空间越小，那么在一个页中，可以放的数据条数就越多。这可以有效的减少磁盘I/O
   - 插入性能方面，MYSQL的索引树是按索引列进行排序的，而如果我们用无序的uuid直接插入数据的话很可能会破坏这个平衡，而自增id则可以避免破坏这个平衡。页分裂时可能要将一部分数据移动到新页中，使用自增id，因为本来就是有序的，直接在新页往下写就是了。

5. Jdk1.8 为什么废弃了永久代

   有经验的同学会发现，对永久代的调优过程非常困难，永久代的大小很难确定，其中涉及到太多因素，如类的总数、常量池大小和方法数量等，而且永久代的数据可能会随着每一次Full GC而发生移动。

   而在JDK8中，类的元数据保存在本地内存中，元空间的最大可分配空间就是系统可用内存空间，可以避免永久代的内存溢出问题，不过需要监控内存的消耗情况，一旦发生内存泄漏，会占用大量的本地内存。

   1、字符串存在永久代中，容易出现性能问题和内存溢出。

   2、类及方法的信息等比较难确定其大小，因此对于永久代的大小指定比较困难，太小容易出现永久代溢出，太大则容易导致老年代溢出。

   3、永久代会为 GC 带来不必要的复杂度，并且回收效率偏低。

6. 双亲委派机制的作用
   1、防止重复加载同一个.class。通过委托去向上面问一问，加载过了，就不用再加载一遍。保证数据安全。
   2、保证核心.class不能被篡改。通过委托方式，不会去篡改核心.clas，即使篡改也不会去加载，即使加载也不会是同一个.class对象了。不同的加载器加载同一个.class也不是同一个Class对象。这样保证了Class执行安全。

7. Mysql MVCC如何实现

   >- 当前读：读取的是记录的最新版本，读取时还要保证其他并发事务不能修改当前记录，会对读取的记录进行加锁
   >- 快照读：快照读是不加锁的非阻塞读，可能读到的并不一定是数据的最新版本，而有可能是之前的历史版本

   MVCC多版本并发控制指的是 “维持一个数据的多个版本，使得读写操作没有冲突” ，在并发读写数据库时，可以做到在读操作时不用阻塞写操作，写操作也不用阻塞读操作，提高了数据库并发读写的性能 同时还可以解决脏读，幻读，不可重复读等事务隔离问题，但不能解决更新丢失问题。

   它的实现原理主要是依赖记录中的 **3个隐式字段**，**undo日志** ，**Read View** 来实现的。每行记录除了我们自定义的字段外，还有数据库隐式定义的DB_TRX_ID,DB_ROLL_PTR,DB_ROW_ID等字段。

   Read View就是事务进行快照读操作的时候生产的读视图，在该事务执行的快照读的那一刻，会生成数据库系统当前的一个快照，记录并维护系统当前活跃事务的ID

   - trx_list 未提交事务ID列表，用来维护Read View生成时刻系统正活跃的事务ID 
   - up_limit_id 记录trx_list列表中事务ID最小的ID 
   - low_limit_id ReadView生成时刻系统尚未分配的下一个事务ID，也就是目前已出现过的事务ID的最大值+1

