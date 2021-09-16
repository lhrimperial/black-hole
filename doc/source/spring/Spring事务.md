### Spring 事务

#### Spring事务抽象

统一一致的事务抽象是Spring框架的一大优势，无论是全局事务还是本地事务，JTA、JDBC、Hibernate还是JPA，Spring都使用统一的编程模型，使得应用程序可以很容易地在全局事务与本地事务，或者不同的事务框架之间进行切换。下图是Spring事务抽象的核心类图：<img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210912100444731.png" alt="image-20210912100444731" style="zoom:40%;" />

接口`PlatformTransactionManager`定义了事务操作的行为，其依赖`TransactionDefinition`和`TransactionStatus`接口，其实大部分的事务属性和行为我们以MySQL数据库为例已经有过了解，这里再对应介绍下。

- `PlatformTransactionManager`：事务管理器
- `getTransaction`方法：事务获取操作，根据事务属性定义，获取当前事务或者创建新事物；
- `commit`方法：事务提交操作，注意这里所说的提交并非直接提交事务，而是根据当前事务状态执行提交或者回滚操作；
- `rollback`方法：事务回滚操作，同样，也并非一定直接回滚事务，也有可能只是标记事务为只读，等待其他调用方执行回滚。
- `TransactionDefinition`：事务属性定义
- `getPropagationBehavior`方法：返回事务的传播属性，默认是`PROPAGATION_REQUIRED`；
- `getIsolationLevel`方法：返回事务隔离级别，事务隔离级别只有在创建新事务时才有效，也就是说只对应传播属性`PROPAGATION_REQUIRED`和`PROPAGATION_REQUIRES_NEW`；
- `getTimeout`方法：返回事务超时时间，以秒为单位，同样只有在创建新事务时才有效；
- `isReadOnly`方法：是否优化为只读事务，支持这项属性的事务管理器会将事务标记为只读，只读事务不允许有写操作，不支持只读属性的事务管理器需要忽略这项设置，这一点跟其他事务属性定义不同，针对其他不支持的属性设置，事务管理器应该抛出异常。
- `getName`方法：返回事务名称，声明式事务中默认值为“类的完全限定名.方法名”。
- `TransactionStatus`：当前事务状态
- `isNewTransaction`方法：当前方法是否创建了新事务（区别于使用现有事务以及没有事务）；
- `hasSavepoint`方法：在嵌套事务场景中，判断当前事务是否包含保存点；
- `setRollbackOnly`和`isRollbackOnly`方法：只读属性设置（主要用于标记事务，等待回滚）和查询；
- `flush`方法：刷新底层会话中的修改到数据库，一般用于刷新如Hibernate/JPA的会话，是否生效由具体事务资源实现决定；
- `isCompleted`方法：判断当前事务是否已完成（已提交或者已回滚）。



部分Spring包含的对`PlatformTransactionManager`的实现类如下图所示：

<img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210912100716295.png" alt="image-20210912100716295" style="zoom:50%;" />

`AbstractPlatformTransactionManager`抽象类实现了Spring事务的标准流程，其子类`DataSourceTransactionManager`是我们使用较多的JDBC单数据源事务管理器



#### Spring事务切面

之前提到，Spring采用AOP来实现声明式事务，那么事务的AOP切面是如何织入的呢？这一点涉及到AOP动态代理对象的生成过程。

代理对象生成的核心类是`AbstractAutoProxyCreator`，实现了`BeanPostProcessor`接口，会在**Bean初始化完成之后**，通过`postProcessAfterInitialization`方法生成代理对象，关于`BeanPostProcessor`在Bean生命周期中的作用

`AbstractAutoProxyCreator`类的核心代码，主要关注三个方法：postProcessAfterInitialization、wrapIfNecessary和createProxy



#### Spring事务拦截

我们已经了解了AOP切面织入生成代理对象的过程，当Bean方法通过代理对象调用时，会触发对应的AOP增强拦截器，前面提到声明式事务是一种环绕增强，对应接口为`MethodInterceptor`，事务增强对该接口的实现为`TransactionInterceptor`，类图如下：

<img src="/Users/eleme/Library/Application Support/typora-user-images/image-20210912101049361.png" alt="image-20210912101049361" style="zoom:50%;" />

事务拦截器`TransactionInterceptor`在`invoke`方法中，通过调用父类`TransactionAspectSupport`的`invokeWithinTransaction`方法进行事务处理，该方法支持声明式事务和编程式事务。

