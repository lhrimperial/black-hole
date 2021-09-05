### Question

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
5. 