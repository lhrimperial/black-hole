### Mybatis

> Setting 文件配置顺序
>
> ```
> <configuration>的子元素必须按照: <properties>、<settings>、<typeAliases>、<typeHandlers>、<objectFactory>、<plugins>、<enviroments>、<databaseIdProvider>、<mappers>
> ```



【源码分析】
https://blog.csdn.net/shenchaohao12321/article/category/7547047
https://blog.csdn.net/qq_38182963/article/category/7350085

【mybatis-spring】
https://blog.csdn.net/u012291108/article/details/51943688
https://blog.csdn.net/lgq2626/article/details/78891496



#### 关键类

- SqlSessionaFactoryBuilder 该类主要用于创建 SqlSessionFactory
- SqlSessionFactory 该类的作用了创建 SqlSession，每次应用程序访问数据库, 我们就要通过 SqlSessionFactory 创建 SqlSession, 所以SqlSessionFactory 和整个 Mybatis 的生命周期是相同的. 
- SqlSession 相当于一个会话
- Mapper 映射器，他的作用是发送 SQL, 然后返回我们需要的结果.
- Configuration Mybatis配置类，包含了所有配置文件及Mapper文件



##### SqlSession 创建过程



##### SqlSession 执行过程

