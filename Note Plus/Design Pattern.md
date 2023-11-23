## 模板方法模式 Template Method

### 1、概述

1. 组成模板的抽象方法被定义在父类中，所以只查看父类的代码是无法知道这些方法最终会进行何种具体的操作，唯一能知道的就是父类是如何调用这些方法的。
2. 子类实现抽象方法的同时也决定了具体的处理，即子类不同处理方式不同。
3. 但不论子类中的具体实现如何，处理的流程都会按照父类中所定义的那样进行。
4. 即父类中定义处理流程的框架，在子类中实现具体处理的模式。

### 2、角色

1. AbstractClass 抽象类
    1. 负责实现模板方法，负责声明在模板方法中使用到的抽象方法。
    2. 这些抽象方法由其自己负责实现。
2. ConcreteClass 具体类
    1. 实现抽象类中定义的抽象方法。

### 3、类关系

1. 抽象父类 AbstractComputeRemoteFee，计算远程上门费用
    1. abstract orderRole()：根据上门的角色添加权重（如学徒、普通师傅、工程师）
    2. abstract orderLevel()：根据客户的等级进行阶梯式优惠（如普通用户、中级用户、高级用户）
    3. abstract computeDistance()：根据百度 API 计算经纬度与距离。
    4. final computeTotalFee()：根据以上的流程计算总的费用。
2. 实现类 CustomerComputeRemoteFee，根据模板的流程，计算普通客户类型单据的上门费用
    1. orderRole()：判断上门的角色（该实现类自己的逻辑：如向客户发送短信、查看该客户的权益等）
    2. orderLevel()：判断客户的等级（该实现类自己的逻辑：因为是普通用户，因此需要查看该用户的等级）
    3. computeDistance()：计算与客户的距离（该实现类自己的逻辑：因为是普通用户，定位一般是家里）
3. 实现类 BusinessComputeRemoteFee，根据模板的流程，计算企业类型单据的上门费用
    1. orderRole()：判断上门的角色（该实现类自己的逻辑：因为面向的是企业，可以筛选判断师傅的等级或者由企业挑选上门师傅）
    2. orderLevel()：判断客户的等级（该实现类自己的逻辑：面向企业，因此可以选择判断企业的信用等级等）
    3. computeDistance()：计算与客户的距离（该实现类自己的逻辑：面向企业，因此可以选择多个企业地址，并选择师傅是否长期上门）

### 4、示例

1. 计算远程费用流程不管是面向用户还是面向企业，流程都是相同的一套，只是在其中的步骤实现上有一定的差异。如判断用户的等级、企业的信用等。

2. 
    ```java
    // 抽象类
    public abstract class AbstractComputeRemoteFee {
        public abstract void orderRole();// 根据上门的角色添加权重
        public abstract void orderLevel();// 根据客户的等级进行阶梯式优惠
        public abstract void computeDistance();// 根据百度 API 计算经纬度与距离
        public final void computeTotalFee() { // 定义流程是先校验角色、再查询用户等级、最后再计算距离
            orderRole();
            orderLevel();
            computeDistance();
            // 一些共用的逻辑，如计算完成后修改单据的字段、状态等等
        }
    }
    ```

3. ```java
    // 客户实现类
    public class CustomerComputeRemoteFee {
        @Override
        public void orderRole() { // 向客户发送短信、查看该客户的权益等
            // ...
        }
        @Override
        public void orderLevel() { // 因为是普通用户，因此需要查看该用户的等级
            // ...
        }
        @Override
        public void computeDistance() { // 因为是普通用户，定位一般是家里
            // ...
        }
    }
    ```

4. ```java
    // 企业实现类
    public class BusinessComputeRemoteFee {
        @Override
        public void orderRole() { // 可以筛选判断师傅的等级或者由企业挑选上门师傅
            // ...
        }
        @Override
        public void orderLevel() { // 面向企业，因此可以选择判断企业的信用等级等
            // ...
        }
        @Override
        public void computeDistance() { // 面向企业，因此可以选择多个企业地址，并选择师傅是否长期上门
            // ...
        }
    }
    ```

5. ```java
    // 实际使用
    public class demo {
        public static void main(String[] args) {
            CustomerComputeRemoteFee customerComputeRemoteFee = new CustomerComputeRemoteFee();
            customerComputeRemoteFee.computeTotalFee();// 客户子类调用父类规定的流程
            BusinessComputeRemoteFee businessComputeRemoteFee = new BusinessComputeRemoteFee();
            businessComputeRemoteFee.computeTotalFee();// 企业子类调用父类规定的流程
        }
    }
    ```

### 5、总结

1. 总体就是，多个业务的流程是相同的，但是该流程的每个步骤实现的方式不尽相同。因此使用模板方法模式，定义一套流程，而流程的细节由实现类完成。
2. 需要注意的是，模板抽象类中的流程方法需要 final 修饰（在这个 final 方法中根据定义的流程完成了对每个抽象方法的有序调用）避免子类重写影响父类定义的流程。
3. 抽象类定义一个 final 类型的 “执行流程” 方法，该方法按照业务顺序调用该抽象类中的抽象方法（因此只查看父类的代码是无法知道这些方法最终会进行何种具体的操作，唯一能知道的就是父类是如何调用这些方法的）
4. 抽象类再定义多个 abstract 方法，用来组成流程中的每个小步骤，并交由子类去实现，从而达成流程相同但是细节不同的效果。

### 6、优缺点

1. 优点：
    1. 代码复用：通用部分写在抽象类中并提供默认实现，所有子类都可以直接使用（即模板方法中的 final 方法）
    2. 提供了流程框架：定义了整个流程的骨架，使流程中的每个步骤都清晰可见。
    3. 符合 OCP 原则：对修改关闭，对扩展开放。
    4. 提高了一致性：确保所有的子类都按规定的骨架流程进行操作。
2. 缺点：
    1. 限制子类的灵活性：要求子类必须遵循父类的骨架以及执行流程。
    2. 增加了类的数量：如果系统复杂的话，每个步骤需要一个类去实现。因此步骤越多，需要实现的类就越多。



## 工厂方法模式 Factory Method

### 1、概述

1. 父类决定实例的生成方式，但并不觉得所要生成的具体的类，具体的处理全部交给子类负责。这样就可以将生成实例的框架和实际负责生成实例的类解耦。
2. 抽象工厂类 Factory 相当于总工厂类，里面定义了生产各种实例的车间。
3. 

### 2、角色

1. 父类
    1. abstract productPart 抽象父类：
        1. 用于定义生产出来的实例默认有哪些 API，如所有的配件 part 要求有默认的上级产品，所有的手机 phone 要求有默认的权益（如保修等）。
        2. 但是具体的处理需要子类去实现（如 A 类型的 part 的上级是什么，B 类型的 part 上级是什么）
    2. abstract ProductFactory 抽象父类：
        1. 负责生成 productPart 角色的抽象类，但是具体的处理由子类实现。
        2. 该角色唯一知道的是，调用 productPart 角色和生产实例的方法就可以生成具体的实例。
        3. 不用 new 关键字来生成实例，而是调用生产实例的专用方法来生成， 这样就可以防止父类与其他具体类耦合。
2. 子类
    1. electionPart 具体子类（决定产品种类）：
        1. 实现了 productPart，是具体的产品，作用是决定生产的是哪一款产品，并默认实现产品本身的 API，如本类生产的是电器类的配件，查找出上级产品并赋值。
    2. electionPartFactory 具体子类（生成确切的实例）：
        1. 实现了 ProductFactory，是具体的创建者，作用是正儿八经的生成了具体的哪一款电器类的配件。
        2. 也就是 new 实例的类。

### 3、类关系

1. 



## 策略模式 Strategy

### 1、概述

1. 在业务执行过程中，可能会使用某种算法或是某一种解决方案。策略模式的目的是可以整体的替换算法或解决方案的实现部分，以不同的算法（策略）去解决同一个问题。

### 2、角色

1. Strategy 接口
    1. 定义了多个方法，作用是处于判断某种状态时，使用某个算法或解决方案。
2. ConcreteStrategy 接口实现类
    1. 负责实现具体的算法或解决方案。
3. Context 上下文
    1. 负责使用 Strategy 角色。
    2. Context 角色保存了 ConcreteStrategy 角色的实例，并使用 ConcreteStrategy 角色去实现需求。

### 3、类关系

1. 接口 interface SmsRoleStrategy，用来表示在什么情况时，向哪个角色发送短信。
    1. abstract User getSuperiorUser(User user)：根据传入的用户拿到其上级的联系方式。
    2. abstract void sendSuperior(boolean isNeed)：根据逻辑判断是否需要发送至其上级。
    3. abstract void sendSms()：通用方法，可以提供默认实现，就是指定角色发送短信。
2. 实现类 SmsOwnStrategy，用来表示只需要向当前用户发送短信即可。
    1. private boolean isNeed，需要用字段表示是否需要发送到上级。
    2. private User user，需要用字段表示其上级的用户数据。
    3. void sendSuperior(boolean isNeed)：通过重写该方法，将 isNeed 字段赋值为 false，表示不需要发送给上级。
    4. User getSuperiorUser(User user)：通过重写该方法，查询出对应的上级用户信息，并保存在 User 字段中。
3. 实现类 SmsSuperiorStrategy，用来表示需要向当前用户的上级发送短信。
    1. private boolean isNeed，需要用字段表示是否需要发送到上级。
    2. private User user，需要用字段表示其上级的用户数据。
    3. void sendSuperior(boolean isNeed)：通过重写该方法，将 isNeed 字段赋值为 true，表示需要发送给上级。
    4. User getSuperiorUser(User user)：通过重写该方法，查询出对应的上级用户信息，并保存在 User 字段中。

### 4、示例

### 5、总结

1. 实际应用时，实例化每个具体的策略类，通过简单的比较判断使用哪个策略，避免了过多的 if-else。
2. 



## 观察者模式 Observer

### 1、概述

1. 当观察对象的状态发生变化时，会通知给观察者，因此适用于根据对象状态进行相应处理的场景。

### 2、角色

1. Subject 抽象类 观察对象（被观察者）
    1. 是被观察者的一系列的定义。
    2. 定义并默认实现了注册观察者、删除观察者、向观察者发送通知的方法。
    3. 定义了获取状态的抽象方法，交给子类去实现更新状态。
2. ConcreteSubject 实现类 具体的观察对象
    1. 是 Subject 观察对象的实现类，主要实现了抽象类更改状态的方法。
3. Observer 接口 观察者
    1. 是观察者一系列的定义。
    2. 负责接收来着 Subject 角色状态变化的通知。
    3. 提供的抽象方法参数是被观察者。
4. ConcreteObserver 实现类 具体的观察者
    1. 当它某个方法被调用后，会去获取要观察对象的最新状态。

### 3、类关系

1. 接口 IWorkOrderObserver，定义观察者
    1. abstract void update(WorkOrderObserver workOrderObserver)：定义观察者负责接收观察对象状态变化的通知。
2. 抽象类 WorkOrderStateAbstract，定义观察对象
    1. List observers：持有集合字段，用来保存观察者。
    2. void addObserver(IWorkOrderObserver workOrderObserver)：该方法有默认实现，目的是注册观察者。
    3. void deleteObserver(IWorkOrderObserver workOrderObserver)：该方法有默认实现，目的是删除观察者。
    4. void notifyObservers()：该方法有默认实现，向每个观察者发送通知，在该示例中，使用了观察者的 update 方法。
    5. abstract WorkOrder getWorkOrder()：抽象方法，用来获取具体的单据。
    6. abstract void updateState()：抽象方法，用来定义修改获取到的单据的状态以及其他字段。
3. 实现类 WorkOrderState，定义具体的观察对象
    1. WorkOrder getWorkOrder()：实现父类的抽象方法，用来获取具体的单据。
    2. void updateState()：实现父类的抽象方法，修改单据后，调用父类的 notifyObservers 方法通知观察者。
4. 实现类 SendWorkOrderObserver，定义具体的观察者，观察到单据状态为派工时进行派工操作。
    1. void update(WorkOrderObserver workOrderObserver)：实现接口方法，并使用观察对象的方法来获取更新的状态字段。获取字段新值后进行自己的逻辑，如派工。
5. 实现类 CompleteWorkOrderObserver，定义具体的观察者，观察到单据状态为完工时进行完工操作。