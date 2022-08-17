###### 1、什么是MVC

- 是一种软件架构思想，将软件按照模型、视图、控制器来划分
- M：Model，模型层，即工程中的JavaBean，作用是处理数据
    - 实体类Bean：专门存储业务数据的
    - 业务处理Bean：指Service或Dao对象，专门用来处理业务逻辑和数据访问
- V：View，视图层，工程中的html或jsp等页面，作用是与用户进行交互，展示数据
- C：Controller，控制层，工程中的Servlet，作用是接收请求和响应浏览器

###### 2、@RequestMapping注解（请求映射）

- 作用是将请求和处理请求的控制器方法关联起来，建立映射关系，SpringMVC接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求

- RequestMapping中value的值必须唯一，否则会定位不到哪个控制器方法来处理指定的请求
- RequestMapping注解的位置
    -  标识一个类：设置映射请求的请求路径的初始信息
    - 标识一个方法：设置映射请求的请求路径的具体信息

###### 3、@RequestMapping注解中的value属性

- value属性通过请求的请求地址匹配请求映射

- value属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址所对应的请求

    ```java
    //请求地址只要为数组中的其中一个，即可匹配到请求映射的方法
    public class RequestMappingController {
        @RequestMapping(value = {"/testRequestMapping","/test"}
                       )
        public String success() {
            return "success";
        }
    }
    ```

- value属性必须设置，至少通过请求地址匹配请求映射

###### 4、@RequestMapping注解中的method属性

- method属性通过请求的请求方式（POST、GET）来匹配请求映射

- method属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址所对应的请求

    ```java
    //请求地址只要为数组中的其中一个，即可匹配到请求映射的方法
    //请求方式不写的话，默认支持POST、GET方式，如果写明，则请求方式必须满足数组中的其中之一
    public class RequestMappingController {
        @RequestMapping(value = {"/testRequestMapping","/test"}
                        method = {RequestMethod.GET}
                       )
        public String success() {
            return "success";
        }
    }
    ```

- 如果不满足请求方式，则会抛异常`405错误`

- 对于处理指定请求方式的控制器方法，SpringMVC提供了@RequestMapping的派生注解

    - 处理get请求的映射：@GetMapping，如

        ```java
        @GetMapping("/test")
        public String foo() {
            ...
        }
        ```

    - 处理post请求的映射：@PostMapping
