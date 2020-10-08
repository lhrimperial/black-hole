#### Spring Bean 创建过程

`org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#createBean`

```java
AbstractAutowireCapableBeanFactory{
	……

    protected Object createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
			throws BeanCreationException {

		if (logger.isTraceEnabled()) {
			logger.trace("Creating instance of bean '" + beanName + "'");
		}
		RootBeanDefinition mbdToUse = mbd;

		// 解析beanName对应的Bean的类型
		Class<?> resolvedClass = resolveBeanClass(mbd, beanName);
		if (resolvedClass != null && !mbd.hasBeanClass() && mbd.getBeanClassName() != null) {
            // 如果resolvedClass存在，并且mdb的beanClass类型不是Class，并且mdb的beanClass不为空（则代表beanClass存的是Class的name）,则使用mdb深拷贝一个新的RootBeanDefinition副本，并且将解析的Class赋值给拷贝的RootBeanDefinition副本的beanClass属性，该拷贝副本取代mdb用于后续的操作
			mbdToUse = new RootBeanDefinition(mbd);
			mbdToUse.setBeanClass(resolvedClass);
		}

		// 验证及准备覆盖的方法（对override属性进行标记及验证）
		try {
			mbdToUse.prepareMethodOverrides();
		}
		catch (BeanDefinitionValidationException ex) {
			throw new BeanDefinitionStoreException(mbdToUse.getResourceDescription(),
					beanName, "Validation of method overrides failed", ex);
		}

		try {
			
        	// 实例化前的处理，给InstantiationAwareBeanPostProcessor一个机会返回代理对象来替代真正的bean实例，达到“短路”效果
			Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
			if (bean != null) {
				return bean;
			}
		}
		catch (Throwable ex) {
			throw new BeanCreationException(mbdToUse.getResourceDescription(), beanName,
					"BeanPostProcessor before instantiation of bean failed", ex);
		}

		try {
            // 创建Bean实例（真正创建Bean的方法）
			Object beanInstance = doCreateBean(beanName, mbdToUse, args);
			if (logger.isTraceEnabled()) {
				logger.trace("Finished creating instance of bean '" + beanName + "'");
			}
            // 返回创建的Bean实例
			return beanInstance;
		}
		catch (BeanCreationException | ImplicitlyAppearedSingletonException ex) {
			// A previously detected exception with proper bean creation context already,
			// or illegal singleton state to be communicated up to DefaultSingletonBeanRegistry.
			throw ex;
		}
		catch (Throwable ex) {
			throw new BeanCreationException(
					mbdToUse.getResourceDescription(), beanName, "Unexpected exception during bean creation", ex);
		}
	}

	……
}
```

##### resolveBeforeInstantiation

```java
protected Object resolveBeforeInstantiation(String beanName, RootBeanDefinition mbd) {
   Object bean = null;
   if (!Boolean.FALSE.equals(mbd.beforeInstantiationResolved)) {
       //mbd不是合成的，并且BeanFactory中存在InstantiationAwareBeanPostProcessor
      if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
          // 解析beanName对应的Bean实例的类型
         Class<?> targetType = determineTargetType(beanName, mbd);
         if (targetType != null) {
             // 实例化前的后置处理器应用（处理InstantiationAwareBeanPostProcessor）
            bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
            if (bean != null) {
                // 如果返回的bean不为空，会跳过Spring默认的实例化过程， 所以只能在这里调用BeanPostProcessor实现类的postProcessAfterInitialization方法

               bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
            }
         }
      }
      mbd.beforeInstantiationResolved = (bean != null);
   }
   return bean;
}

protected Object applyBeanPostProcessorsBeforeInstantiation(Class<?> beanClass, String beanName) {
    // 遍历当前BeanFactory中的BeanPostProcessor
    for (BeanPostProcessor bp : getBeanPostProcessors()) {
        //应用InstantiationAwareBeanPostProcessor后置处理器，允许postProcessBeforeInstantiation方法返回bean对象的代理
        if (bp instanceof InstantiationAwareBeanPostProcessor) {
            InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
            // 执行postProcessBeforeInstantiation方法，在Bean实例化前操作，该方法可以返回一个构造完成的Bean实例，从而不会继续执行创建Bean实例的“正规的流程”，达到“短路”的效果。
            Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
            if (result != null) {
                //如果result不为空，也就是有后置处理器返回了bean实例对象，则会跳过Spring默认的实例化过程
                return result;
            }
        }
    }
    return null;
}
```

##### doCreateBean

```java
protected Object doCreateBean(final String beanName, final RootBeanDefinition mbd, final Object[] args)
        throws BeanCreationException {
 
    // Instantiate the bean.
    // 新建Bean包装类
    BeanWrapper instanceWrapper = null;
    if (mbd.isSingleton()) {
        //如果是FactoryBean，则需要先移除未完成的FactoryBean实例的缓存
        instanceWrapper = this.factoryBeanInstanceCache.remove(beanName);
    }
    if (instanceWrapper == null) {
        // 根据beanName、mbd、args，使用对应的策略创建Bean实例，并返回包装类BeanWrapper
        instanceWrapper = createBeanInstance(beanName, mbd, args);
    }
    // 4.拿到创建好的Bean实例
    final Object bean = (instanceWrapper != null ? instanceWrapper.getWrappedInstance() : null);
    // 5.拿到Bean实例的类型
    Class<?> beanType = (instanceWrapper != null ? instanceWrapper.getWrappedClass() : null);
    mbd.resolvedTargetType = beanType;
 
    // Allow post-processors to modify the merged bean definition.
    synchronized (mbd.postProcessingLock) {
        if (!mbd.postProcessed) {
            try {
                // 6.应用后置处理器MergedBeanDefinitionPostProcessor，允许修改MergedBeanDefinition，
                // Autowired注解正是通过此方法实现注入类型的预解析
                applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
            } catch (Throwable ex) {
                throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                        "Post-processing of merged bean definition failed", ex);
            }
            mbd.postProcessed = true;
        }
    }
 
    // Eagerly cache singletons to be able to resolve circular references
    // even when triggered by lifecycle interfaces like BeanFactoryAware.
    // 7.判断是否需要提早曝光实例：单例 && 允许循环依赖 && 当前bean正在创建中
    boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
            isSingletonCurrentlyInCreation(beanName));
    if (earlySingletonExposure) {
        if (logger.isDebugEnabled()) {
            logger.debug("Eagerly caching bean '" + beanName +
                    "' to allow for resolving potential circular references");
        }
 
        // 8.提前曝光beanName的ObjectFactory，用于解决循环引用
        addSingletonFactory(beanName, new ObjectFactory<Object>() {
            @Override
            public Object getObject() throws BeansException {
                // 8.1 应用后置处理器SmartInstantiationAwareBeanPostProcessor，允许返回指定bean的早期引用，若没有则直接返回bean
                return getEarlyBeanReference(beanName, mbd, bean);
            }
        });
    }
 
    // Initialize the bean instance.  初始化bean实例。
    Object exposedObject = bean;
    try {
        // 9.对bean进行属性填充；其中，可能存在依赖于其他bean的属性，则会递归初始化依赖的bean实例
        populateBean(beanName, mbd, instanceWrapper);
        if (exposedObject != null) {
            // 10.对bean进行初始化
            exposedObject = initializeBean(beanName, exposedObject, mbd);
        }
    } catch (Throwable ex) {
        if (ex instanceof BeanCreationException && beanName.equals(((BeanCreationException) ex).getBeanName())) {
            throw (BeanCreationException) ex;
        } else {
            throw new BeanCreationException(
                    mbd.getResourceDescription(), beanName, "Initialization of bean failed", ex);
        }
    }
 
    if (earlySingletonExposure) {
        // 11.如果允许提前曝光实例，则进行循环依赖检查
        Object earlySingletonReference = getSingleton(beanName, false);
        // 11.1 earlySingletonReference只有在当前解析的bean存在循环依赖的情况下才会不为空
        if (earlySingletonReference != null) {
            if (exposedObject == bean) {
                // 11.2 如果exposedObject没有在initializeBean方法中被增强，则不影响之前的循环引用
                exposedObject = earlySingletonReference;
            } else if (!this.allowRawInjectionDespiteWrapping && hasDependentBean(beanName)) {
                // 11.3 如果exposedObject在initializeBean方法中被增强 && 不允许在循环引用的情况下使用注入原始bean实例
                // && 当前bean有被其他bean依赖
 
                // 11.4 拿到依赖当前bean的所有bean的beanName数组
                String[] dependentBeans = getDependentBeans(beanName);
                Set<String> actualDependentBeans = new LinkedHashSet<String>(dependentBeans.length);
                for (String dependentBean : dependentBeans) {
                    // 11.5 尝试移除这些bean的实例，因为这些bean依赖的bean已经被增强了，他们依赖的bean相当于脏数据
                    if (!removeSingletonIfCreatedForTypeCheckOnly(dependentBean)) {
                        // 11.6 移除失败的添加到 actualDependentBeans
                        actualDependentBeans.add(dependentBean);
                    }
                }
 
                if (!actualDependentBeans.isEmpty()) {
                    // 11.7 如果存在移除失败的，则抛出异常，因为存在bean依赖了“脏数据”
                    throw new BeanCurrentlyInCreationException(beanName,
                            "Bean with name '" + beanName + "' has been injected into other beans [" +
                                    StringUtils.collectionToCommaDelimitedString(actualDependentBeans) +
                                    "] in its raw version as part of a circular reference, but has eventually been " +
                                    "wrapped. This means that said other beans do not use the final version of the " +
                                    "bean. This is often the result of over-eager type matching - consider using " +
                                    "'getBeanNamesOfType' with the 'allowEagerInit' flag turned off, for example.");
                }
            }
        }
    }
 
    // Register bean as disposable.
    try {
        // 12.注册用于销毁的bean，执行销毁操作的有三种：自定义destroy方法、DisposableBean接口、DestructionAwareBeanPostProcessor
        registerDisposableBeanIfNecessary(beanName, bean, mbd);
    } catch (BeanDefinitionValidationException ex) {
        throw new BeanCreationException(
                mbd.getResourceDescription(), beanName, "Invalid destruction signature", ex);
    }
    // 13.完成创建并返回
    return exposedObject;
}
```

##### createBeanInstance

```java
protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, Object[] args) {
    // Make sure bean class is actually resolved at this point.
    // 解析bean的类型信息
    Class<?> beanClass = resolveBeanClass(mbd, beanName);
 
    if (beanClass != null && !Modifier.isPublic(beanClass.getModifiers()) && !mbd.isNonPublicAccessAllowed()) {
        // beanClass不为空 && beanClass不是公开类（不是public修饰） && 该bean不允许访问非公共构造函数和方法，则抛异常
        throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                "Bean class isn't public, and non-public access not allowed: " + beanClass.getName());
    }
 
    // 如果存在工厂方法则使用工厂方法实例化bean对象
    if (mbd.getFactoryMethodName() != null) {
        return instantiateUsingFactoryMethod(beanName, mbd, args);
    }
 
    // Shortcut when re-creating the same bean...
    // resolved: 构造函数或工厂方法是否已经解析过
    boolean resolved = false;
    // autowireNecessary: 是否需要自动注入（即是否需要解析构造函数参数）
    boolean autowireNecessary = false;
    if (args == null) {
        // 加锁
        synchronized (mbd.constructorArgumentLock) {
            if (mbd.resolvedConstructorOrFactoryMethod != null) {
                //  如果resolvedConstructorOrFactoryMethod缓存不为空，则将resolved标记为已解析
                resolved = true;
                // 根据constructorArgumentsResolved判断是否需要自动注入
                autowireNecessary = mbd.constructorArgumentsResolved;
            }
        }
    }
 
    if (resolved) {
        // 如果已经解析过，则使用resolvedConstructorOrFactoryMethod缓存里解析好的构造函数方法
        if (autowireNecessary) {
            // 需要自动注入，则执行构造函数自动注入
            return autowireConstructor(beanName, mbd, null, null);
        } else {
            // 否则使用默认的构造函数进行bean的实例化
            return instantiateBean(beanName, mbd);
        }
    }
 
    // Need to determine the constructor...
    // 应用后置处理器SmartInstantiationAwareBeanPostProcessor，拿到bean的候选构造函数
    Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
    if (ctors != null ||
            mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_CONSTRUCTOR ||
            mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args)) {
        // 5.如果ctors不为空 || mbd的注入方式为AUTOWIRE_CONSTRUCTOR || mdb定义了构造函数的参数值 || args不为空，则执行构造函数自动注入
        return autowireConstructor(beanName, mbd, ctors, args);
    }
 
    // No special handling: simply use no-arg constructor.
    // 6.没有特殊处理，则使用默认的构造函数进行bean的实例化
    return instantiateBean(beanName, mbd);
}
```

##### 

```java
protected Constructor<?>[] determineConstructorsFromBeanPostProcessors(Class<?> beanClass, String beanName)
        throws BeansException {
 
    if (beanClass != null && hasInstantiationAwareBeanPostProcessors()) {
        // 遍历所有的BeanPostProcessor
        for (BeanPostProcessor bp : getBeanPostProcessors()) {
            if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
                SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
                // 调用SmartInstantiationAwareBeanPostProcessor的determineCandidateConstructors方法，
                // 该方法可以返回要用于beanClass的候选构造函数
                // 例如：使用@Autowire注解修饰构造函数，则该构造函数在这边会被AutowiredAnnotationBeanPostProcessor找到
                Constructor<?>[] ctors = ibp.determineCandidateConstructors(beanClass, beanName);
                if (ctors != null) {
                    //如果ctors不为空，则不再继续执行其他的SmartInstantiationAwareBeanPostProcessor
                    return ctors;
                }
            }
        }
    }
    return null;
}
```

