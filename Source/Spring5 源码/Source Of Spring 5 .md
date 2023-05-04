## 一、IOC

### 1、创建 Bean 的过程

1. **定义 Bean**：在配置文件中（properties、yml），提前定义好对 Bean 的描述

2. **IoC 读取 Bean**：抽象接口 BeanDefinitionReader 定义读取配置文件的规范，实现接口以让配置文件中的 Bean 加载到 IoC容器中

3. **IoC 加载 Bean**：加载到容器中，会有 Bean 的定义信息，也就是 BeanDefinition

4. **BeanFactoryPostProcessor 处理 Bean**：在 BeanDefinition 送往 BeanFactory 之前（即 Bean 加载完之后，实例化之前），会有一种后置处理器 PostProcessor，称为 BeanFactoryPostProcessor，通过实现该接口可以在 Bean 实例化之前修改 Bean 的一些信息

    ```java
    /**
        实现 BeanFactoryPostProcessor 接口，对指定的 Bean 进行定制化，而且有多个实现类实现了该接口，进行了不同的定制化操作
        1、获取所有的 BeanDefinition 对象，对其进行修改、删除、添加等操作，以适应特定的场景需求，比如注入特定的属性、改变 Bean 的作用域、动态注册 BeanDefinition 等。
        2、允许在 Bean 实例化之前，对 BeanDefinition 对象进行拦截，从而实现对 Bean 实例化过程的定制化。
        3、与BeanPostProcessor配合使用，可以在Bean实例化之前对BeanDefinition对象进行修改，同时在Bean实例化之后对Bean对象进行修改和定制化。
    */
    public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    	@Override
    	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            // 获取定义的 foo 的 bean
    		BeanDefinition fooBeanDefiniton = beanFactory.getBeanDefinition("foo");
            // 获取 foo 中的属性
    		PropertyValues propertyValues = fooBeanDefiniton.getPropertyValues();
            // 给 foo 属性中 name 赋值为 bar
    		propertyValues.addPropertyValue(new PropertyValue("name", "bar"));
    	}
    }
    ```

5. **Bean 实例化**：根据 Bean 的定义信息通过 BeanFactory 的反射进行实例化（在堆中开辟空间）

6. **填充 Bean 的属性**：

7. **BeanPostProcessor 的 Before 方法**：实现 BeanPostProcessor 接口，并重写 postProcessBeforeInitialization 方法，

8. **Bean 初始化**：然后对 Bean 进行填充属性、赋值、并调用具体的初始化方法

9. **BeanPostProcessor 的 After 方法**：实现 BeanPostProcessor 接口，并重写 postProcessAfterInitialization 方法，

    ```java
    /**
    	实现 BeanPostProcessor 接口，在 Bean 初始化前后进行修改或包装
    */
    public class CustomerBeanPostProcessor implements BeanPostProcessor {
        /**
        	Bean 初始化前进行修改
        */
    	@Override
    	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            // 根据 beanName 获取到指定的 Bean
    		if ("foo".equals(beanName)) {
                // 修改 foo 实例中的 name 属性
    			((Foo) bean).setName("before bar");
    		}
    		return bean;
    	}
    
        /**
        	Bean 初始后前进行修改
        */
    	@Override
    	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("Bean 初始化之后打印该日志");
    		return bean;
    	}
    }
    ```

10. **得到完整的 Bean**：完成初始化 Bean 之后，就生成了完整的 Bean 对象

    

### 2、IOC容器

1. Spring 中的容器可以分为两个系列：实现 BeanFactory 接口的容器和实现了 ApplicationContext 接口的容器。
2. BeanFactory 提供的主要功能是 bean 的实例化、依赖注入和管理等简单的功能，而 ApplicationContext 扩展了更高级的功能如国际化、事件处理、应用程序层面的上下文、AOP 等。
3. ApplicationContext 的常用实现类有以下 5 种，其中 SpringBoot 默认使用的是注解驱动的应用上下文（AnnotationConfigApplicationContext）作为其应用上下文容器
    1. ClassPathApplicationContext
    2. FileSystemXmlApplicationContext
    3. XmlWebApplicationContext
    4. AnnotationConfigApplicationContext
    5. AnnotationConfigWebApplicationContext



### 3、Spring 的执行流程

1. ```java
    // 通过 xml 文件将 Bean 装载到 IOC容器中，并返回 IOC 容器
    ApplicationContext ac = new ClassPathXmlApplicationContext("foo.xml");
    ```

2. ```java
    // 告诉读取器配置文件放在哪里，即把 foo.xml 这个字符串传进去
    setConfigLocations(configLocations);
    ```

3. ```java
    // 调用容器准备刷新，获取容器的当前时间，同时给容器设置同步标识
    prepareRefresh();
    ```

4. ```java
    // 设置监听器的集合对象，在 SpringBoot 中做了扩展，applicationListener 是有对应的属性值的，而在 Spring 中是没有的
    this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListener);
    ```

5. ```java
    // 创建容器
    obtainFreshBeanFactory();
    ```

6. 

