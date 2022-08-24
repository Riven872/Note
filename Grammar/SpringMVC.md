##### 一、SpringMVC简介

###### 1、什么是MVC

- 是一种软件架构思想，将软件按照模型、视图、控制器来划分
- M：Model，模型层，即工程中的JavaBean，作用是处理数据
    - 实体类Bean：专门存储业务数据的
    - 业务处理Bean：指Service或Dao对象，专门用来处理业务逻辑和数据访问
- V：View，视图层，工程中的html或jsp等页面，作用是与用户进行交互，展示数据
- C：Controller，控制层，工程中的Servlet，作用是接收请求和响应浏览器



##### 二、@RequestMapping注解

###### 1、@RequestMapping注解（请求映射）

- 作用是将请求和处理请求的控制器方法关联起来，建立映射关系，SpringMVC接收到指定的请求，就会来找到在映射关系中对应的控制器方法来处理这个请求

- RequestMapping中value的值必须唯一，否则会定位不到哪个控制器方法来处理指定的请求
- RequestMapping注解的位置
    -  标识一个类：设置映射请求的请求路径的初始信息
    - 标识一个方法：设置映射请求的请求路径的具体信息

###### 2、@RequestMapping注解中的value属性

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

###### 3、@RequestMapping注解中的method属性

- method属性通过请求的请求方式（POST、GET）来匹配请求映射

- method属性是一个字符串类型的数组，表示该请求映射能够匹配多个请求地址所对应的请求

    ```java
    //请求地址只要为数组中的其中一个，即可匹配到请求映射的方法
    //请求方式不写的话，默认支持POST、GET方式，如果写明，则请求方式必须满足数组中的其中之一
    public class RequestMappingController {
        @RequestMapping(value = {"/testRequestMapping","/test"},
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

###### 4、@RequestMapping注解中的params属性

- params属性通过请求的请求参数匹配请求映射

- params属性是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射的匹配关系

    - "foo"：要求请求映射所匹配的请求必须携带foo这个请求参数
    - "!foo"：要求请求映射所匹配的请求不能携带foo这个请求参数
    - "foo=bar"：要求请求映射所匹配的请求必须携带foo这个请求参数且参数值为bar
    - "foo!=bar"：要求请求映射所匹配的请求必须携带foo这个请求参数但是参数值不能为bar

    ```java
    @RequestMapping(value = {"/testRequestMapping","/test"},
                    method = {RequestMethod.GET},
                    params = {"username", "password != 123456"}
                   )
    public String foo() {
        ...
    }
    ```

- 如果不满足请求方式，则会报错`400错误`

###### 5、@RequestMapping注解中的headers属性

- headers属性通过请求的请求头信息匹配请求映射
- headers属性是一个字符串数组，与params规则相同
- 如果当前请求满足value和method属性，但不满足headers属性，页面会报错`404错误`

###### 6、SpringMVC支持路径中的占位符

- 该知识通常用在RestFul风格中

- 当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的@RequestMapping注解的value属性中通过占位符{xxx}表示传输的数据，再通过@PathVariable注解，将占位符所表示的数据赋值给控制器方法的形参

    ```html
    <a th:href="@{/testPath/3/admin}">测试testPath</a>
    <!-- 如 http://localhost:8080/SpringMVC/testPath/3/admin -->
    ```

    ```java
    //testPath表示资源路径 {id}表示传入的第一个参数 {username}表示传入的第二个参数
    @RequestMapping("/testPath/{id}/{username}")
    public String testPath(@PathVariable("id") int id, @PathVariable("username") String username) {
        System.out.println("id:" + id + " username:" + username);
        return "success";
    }
    ```



##### 三、SpringMVC获取请求参数

###### 1、通过控制器方法的形参获取请求参数

- 在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在DispatcherServlet中就会将请求参数赋值给相应的形参

    ```html
    <a th:href="@{/testParam(username='admin',password=123456)}">测试获取请求参数-->/testParam</a>
    ```

    ```java
    @RequestMapping("/testParam")
    //SpringMVC会自动将请求参数赋值给同名的形参
    public String testParam(String username, String password){
        System.out.println("username:"+username+",password:"+password);
        return "success";
    }
    ```

- 若请求所传输的请求参数中有多个同名的请求参数，此时可以在控制器方法的形参中设置字符串数组或者字符串类型的形参接收此请求参数

    - 若使用字符串数组类型的形参，此参数的数组中包含了每一个数据。如：`username : [1, 2, 3]`
    - 若使用字符串类型的形参，此参数的值为每个数据中间使用逗号拼接的结果。如：`username : "1, 2, 3"`

###### 2、@RequestParam注解

- @RequestParam是将请求参数和控制器方法的形参创建映射关系

    - value：指定为形参赋值的请求参数的参数名
    - required：设置是否必须传输此请求参数，默认值为true
    - defaultValue：不管required属性值为true或false，当value所指定的请求参数没有传输或传输的值为""时，则使用默认值为形参赋值

    ```java
    @RequestMapping("/testParam")
    //value = "foo"意味着请求参数为foo时，也会将参数值赋值给该形参
    //required = false意味着该参数不一定要传，默认为true，如果会true时不传参，会报400错误
    //defaultValue = "bar"意味着请求不传该参数时，默认设置为"bar"
    public String testParam(@RequestParam(value = "foo", required = false, defaultValue = "bar") String username, 
                            String password){
        System.out.println("username:"+username+",password:"+password);
        return "success";
    }
    ```

###### 3、@RequestHeader注解

- @RequestHeader是将请求头信息和控制器方法的形参创建映射关系

- @RequestHeader注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

###### 4、@CookieValue注解

- @CookieValue是将cookie数据和控制器方法的形参创建映射关系

- @CookieValue注解一共有三个属性：value、required、defaultValue，用法同@RequestParam

###### 5、通过实体类获取请求参数

- 可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致，那么请求参数就会为此属性赋值

    ```java
    @RequestMapping("/testBean")
    public String testPOJO(User user){
        System.out.println(user);
        return "success";
    }
    //最终结果-->User{id=null, username='张三', password='123', age=23, sex='男', email='123@qq.com'}
    ```

###### 6、解决获取请求参数的乱码问题

- Get请求是Tomcat中的配置文件解决的，因此主要解决的是POST请求

- 原先解决可以在ServletRequest中设置编码，但由于SpringMVC会提前使用ServletRequest，再设置不会生效，因此需要在ServletRequest更早之前去设置

- 过滤器比ServletRequest要早一步执行，因此可以在过滤器阶段使用SpringMVC提供的过滤器进行设置编码格式

    ```xml
    <!--配置springMVC的编码过滤器-->
    <filter>
        <filter-name>CharacterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CharacterEncodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ```

- SpringMVC中处理编码的过滤器一定要配置到其他过滤器之前，否则无效



##### 四、域对象共享数据

###### 1、使用ModelAndView向request域对象共享数据

- 使用ModelAndView对象时，需要完成Model和View的功能，且返回ModelAndView对象

```java
@RequestMapping("/testModelAndView")
public ModelAndView testModelAndView(){
    /**
     * ModelAndView有Model和View的功能
     * Model主要用于向请求域共享数据
     * View主要用于设置视图，实现页面跳转
     */
    ModelAndView mav = new ModelAndView();
    //处理数据模型，即向请求域request共享数据
    mav.addObject("testScope", "hello,ModelAndView");
    //设置视图名称，实现页面跳转
    mav.setViewName("success");
    return mav;
}
```

###### 2、使用Model向request域对象共享数据

- 使用Model对象时，可以完成处理数据的部分，视图部分可以直接return出来

```java
    @RequestMapping("/testModel")
    public String testModel(Model model){
        model.addAttribute("testScope", "hello,Model");
        return "success";
    }
```

###### 3、使用map向request域对象共享数据

- 使用的是普通的Map键值对结构

```java
@RequestMapping("/testMap")
public String testMap(Map<String, Object> map){
    map.put("testScope", "hello,Map");
    return "success";
}
```

###### 4、使用ModelMap向request域对象共享数据

```java
@RequestMapping("/testModelMap")
public String testModelMap(ModelMap modelMap){
    modelMap.addAttribute("testScope", "hello,ModelMap");
    return "success";
}
```

###### 5、Model、ModelMap、Map的关系

- Model、ModelMap、Map类型的参数其实本质上都是 BindingAwareModelMap 类型的

```java
    public interface Model{}
    public class ModelMap extends LinkedHashMap<String, Object> {}
    public class ExtendedModelMap extends ModelMap implements Model {}
    public class BindingAwareModelMap extends ExtendedModelMap {}
```

###### 6、向request域对象共享数据四种方式总结

- 无论哪种方式，底层都会走到ModelAndView中，因此推荐使用ModelAndView

###### 7、向session域共享数据

- SpringMVC提供的向session域共享数据的方法不太好用，建议用原生的方法

```java
@RequestMapping("/testSession")
public String testSession(HttpSession session){
    session.setAttribute("testSessionScope", "hello,session");
    return "success";
}
```

###### 8、向application域共享数据（表示整个应用的范围）

- 即ServletContext域

```java
@RequestMapping("/testApplication")
public String testApplication(HttpSession session){
	ServletContext application = session.getServletContext();
    application.setAttribute("testApplicationScope", "hello,application");
    return "success";
}
```



##### 五、SpringMVC的视图

- SpringMVC中的视图是View接口，视图的作用渲染数据，将模型Model中的数据展示给用户

- SpringMVC视图的种类很多，默认有转发视图和重定向视图

- 若使用的视图技术为Thymeleaf，在SpringMVC的配置文件中配置了Thymeleaf的视图解析器，由此视图解析器解析之后所得到的是ThymeleafView

###### 1、ThymeleafView

- 当控制器方法中所设置的视图名称没有任何前缀时，此时的视图名称会被SpringMVC配置文件中所配置的视图解析器解析，视图名称拼接视图前缀和视图后缀所得到的最终路径，会通过转发的方式实现跳转

    ```java
    @RequestMapping("/testHello")
    public String testHello(){
        //"hello"这个视图名称没有任何前后缀，会被Thymeleaf解析器解析，得到的视图就是ThymeleafView
        return "hello";
    }
    ```

###### 2、转发视图

- SpringMVC中默认的转发视图是InternalResourceView

- 当控制器方法中所设置的视图名称以"forward:"为前缀时，创建InternalResourceView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"forward:"去掉，剩余部分作为最终路径通过转发的方式实现跳转

    ```java
    @RequestMapping("/testForward")
    public String testForward(){
        //以forward:为前缀时，会去找到/testHello请求，再进行视图的解析
        return "forward:/testHello";
    }
    
    @RequestMapping("/testHello")
    public String testHello(){
        //通过转发到达这里，且该视图没有前缀，因此会跳转到success视图
        return "success";
    }
    ```

- 通过该方式，首先View会创建InternalResourceView类型的视图进行转发，然后会创建ThymeleafView类型的视图实现跳转

###### 3、重定向视图

- 转发：

    - 浏览器发送一次请求（之后的请求是发生在服务器内部，因此请求的地址还是浏览器第一次发送请求的地址）
    - 可以获取请求域中的数据（因为转发是一次请求，所以用到的Request对象是同一个）
    - 可以访问Web-INF下的资源（因为Web-INF下的资源具有隐藏性，只能通过服务器内部去访问）
    - 不能跨域（转发行为发生在服务器内部，因此只能访问服务器内部的资源）

- 重定向：

    - 浏览器发送两次请求，一次是访问Servlet，另一次访问重定向的地址（最终的地址是重定向的地址）
    - 不可以获取请求域中的数据（因为重定向是两次请求，是两个不同的Request对象）
    - 不可以访问Web-INF下的资源（不能通过浏览器去访问）
    - 可以跨域（重定向行为发生在浏览器，因此可以访问更多的资源）

- SpringMVC中默认的重定向视图是RedirectView

- 当控制器方法中所设置的视图名称以"redirect:"为前缀时，创建RedirectView视图，此时的视图名称不会被SpringMVC配置文件中所配置的视图解析器解析，而是会将前缀"redirect:"去掉，剩余部分作为最终路径通过重定向的方式实现跳转

    ```java
    @RequestMapping("/testRedirect")
    public String testRedirect(){
        return "redirect:/testHello";
    }
    ```

###### 4、视图控制器view-controller

- 当控制器方法中，仅仅用来实现页面跳转，即只需要设置视图名称时，可以将处理器方法使用view-controller标签进行表示

    ```xml
    <!--
    	path：设置处理的请求地址
    	view-name：设置请求地址所对应的视图名称
    -->
    <mvc:view-controller path="/testView" view-name="success"></mvc:view-controller>
    ```

- 当SpringMVC中设置任何一个view-controller时，其他控制器中的请求映射将全部失效，此时需要在SpringMVC的核心配置文件中设置开启mvc注解驱动的标签：`<mvc:annotation-driven></mvc:annotation-driven>`



##### 六、RESTful 

- 资源：资源是一种看待服务器的方式，即，将服务器看作是由很多离散的资源组成。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词。一个资源可以由一个或多个URI来标识。URI既是资源的名称，也是资源在Web上的地址。对某个资源感兴趣的客户端应用，可以通过资源的URI与其进行交互。
- 资源的表述：资源的表述是一段对于资源在某个特定时刻的状态的描述。可以在客户端-服务器端之间转移（交换）。资源的表述可以有多种格式，例如HTML/XML/JSON/纯文本/图片/视频/音频等等。资源的表述格式可以通过协商机制来确定。请求-响应方向的表述通常使用不同的格式。
- 状态转移：在客户端和服务器端之间转移（transfer）代表资源状态的表述。通过转移和操作资源的表述，来间接实现操作资源的目的。

###### 1、RESTful的实现

- HTTP 协议里面，四个表示操作方式的动词：GET、POST、PUT、DELETE
- GET 用来获取资源，POST 用来新建资源，PUT 用来更新资源，DELETE 用来删除资源

###### 2、HiddenHttpMethodFilter

- SpringMVC 提供了 HiddenHttpMethodFilter 帮助我们将 POST 请求转换为 DELETE 或 PUT 请求
- HiddenHttpMethodFilter 处理put和delete请求的条件：
    - 1、当前请求的请求方式必须为post
    - 2、当前请求必须传输请求参数_method



##### 七、HttpMessageConverter

- HttpMessageConverter，报文信息转换器，将请求报文转换为Java对象，或将Java对象转换为响应报文
- HttpMessageConverter提供了两个注解和两个类型：
    - @RequestBody，@ResponseBody
    - RequestEntity，ResponseEntity

###### 1、@RequestBody

- @RequestBody可以获取请求体，需要在控制器方法设置一个形参，使用@RequestBody进行标识，当前请求的请求体就会为当前注解所标识的形参赋值

    ```java
    @RequestMapping("/testRequestBody")
    //POST类型的请求才有请求体，且请求体类型为String
    public String testRequestBody(@RequestBody String requestBody){
        System.out.println("requestBody:"+requestBody);
        return "success";
    }
    //输出结果为：requestBody:username=admin&password=123456
    ```

###### 2、RequestEntity

- RequestEntity封装请求报文的一种类型，需要在控制器方法的形参中设置该类型的形参，当前请求的请求报文就会赋值给该形参

- 通过getHeaders()获取请求头信息，通过getBody()获取请求体信息

    ```java
    @RequestMapping("/testRequestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity){
        System.out.println("requestHeader:"+requestEntity.getHeaders());
        System.out.println("requestBody:"+requestEntity.getBody());
        return "success";
    }
    //输出结果为：
    //requestHeader:[host:“localhost:8080”, connection:“keep-alive”, content-length:“27”, cache-control:“max-age=0”, sec-ch-ua:"" Not A;Brand";v=“99”, “Chromium”;v=“90”, “Google Chrome”;v=“90"”, sec-ch-ua-mobile:"?0", upgrade-insecure-requests:“1”, origin:“http://localhost:8080”, user-agent:“Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36”]
    //requestBody:username=admin&password=123
    ```

###### 3、@ResponseBody

- @ResponseBody用于标识一个控制器方法，可以将该方法的返回值直接作为响应报文的响应体响应到浏览器

    ```java
    @RequestMapping("/testResponseBody")
    @ResponseBody
    public String testResponseBody(){
        return "foobar";
    }
    //结果：浏览器页面显示foobar
    ```


###### 4、SpringMVC处理json

- 使用@ResponseBody处理json的步骤

    - 导入jackson的依赖

        ```xml
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.1</version>
        </dependency>
        
        ```

    - 在SpringMVC的核心配置文件中开启mvc的注解驱动，此时在HandlerAdaptor中会自动装配一个消息转换器：MappingJackson2HttpMessageConverter，可以将响应到浏览器的Java对象转换为Json格式的字符串

        ```xml
        <mvc:annotation-driven />
        ```

    - 在处理器方法上使用@ResponseBody注解进行标识

        ```java
        @RequestMapping("/testResponseUser")
        @ResponseBody
        public User testResponseUser() {
            return new User(1001, "admin", "123456", 23, "男");
        }
        ```

    - 将Java对象直接作为控制器方法的返回值返回，就会自动转换为Json格式的字符串

    - 输出结果为：{“id”:1001,“username”:“admin”,“password”:“123456”,“age”:23,“sex”:“男”}

###### 5、SpringMVC处理ajax

- 请求超链接：

    ```html
    <div id="app">
    	<a th:href="@{/testAjax}" @click="testAjax">testAjax</a><br>
    </div>
    ```

- 通过vue和axios处理点击事件

    ```html
    <script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
    <script type="text/javascript" th:src="@{/static/js/axios.min.js}"></script>
    <script type="text/javascript">
        var vue = new Vue({
            el:"#app",
            methods:{
                testAjax:function (event) {
                    axios({
                        method:"post",
                        url:event.target.href,
                        params:{
                            username:"admin",
                            password:"123456"
                        }
                    }).then(function (response) {
                        alert(response.data);
                    });
                    event.preventDefault();
                }
            }
        });
    </script>
    ```

- 控制器方法

    ```java
    @RequestMapping("/testAjax")
    @ResponseBody
    public String testAjax(String username, String password){
        System.out.println("username:"+username+",password:"+password);
        //由@ResponseBody修饰，因此返回值是"hello,ajax"，又因为调用的是Ajax所以不会有页面跳转，只有alert弹窗显示"hello,ajax"
        return "hello,ajax";
    }
    ```

###### 6、@RestController注解

- @RestController注解是springMVC提供的一个复合注解，标识在控制器的类上，就相当于为类添加了@Controller注解，并且为其中的每个方法添加了@ResponseBody注解

###### 7、ResponseEntity

- ResponseEntity用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文，可用于文件下载



##### 八、文件上传和下载

###### 1、文件下载

- 使用ResponseEntity实现下载文件的功能

    ```java
    @RequestMapping("/testDown")
    //自定义响应体类型为Byte数组
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException {
        //获取ServletContext对象
        ServletContext servletContext = session.getServletContext();
        //获取服务器中文件的真实路径
        String realPath = servletContext.getRealPath("/static/img/1.jpg");
        //创建输入流
        InputStream is = new FileInputStream(realPath);
        //创建字节数组
        byte[] bytes = new byte[is.available()];
        //将流读到字节数组中
        is.read(bytes);
        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();
        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=1.jpg");
        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;
        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);
        //关闭输入流
        is.close();
        return responseEntity;
    }
    ```

###### 2、文件上传

- 文件上传要求form表单的请求方式必须为post，并且添加属性enctype=“multipart/form-data”

- SpringMVC中将上传的文件封装到MultipartFile对象中，通过此对象可以获取文件相关信息

    - 添加依赖

        ```xml
        <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
        <dependency>
            <groupId>commons-fileupload</groupId>
            <artifactId>commons-fileupload</artifactId>
            <version>1.3.1</version>
        </dependency>
        ```

    - 在SpringMVC的配置文件中添加配置：

        ```xml
        <!--必须通过文件解析器的解析才能将文件转换为MultipartFile对象-->
        <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"></bean>
        ```

    - 控制器方法：

        ```java
        @RequestMapping("/testUp")
        public String testUp(MultipartFile photo, HttpSession session) throws IOException {
            //获取上传的文件的文件名
            String fileName = photo.getOriginalFilename();
            //处理文件重名问题
            String hzName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID().toString() + hzName;
            //获取服务器中photo目录的路径
            ServletContext servletContext = session.getServletContext();
            String photoPath = servletContext.getRealPath("photo");
            File file = new File(photoPath);
            if(!file.exists()){
                file.mkdir();
            }
            String finalPath = photoPath + File.separator + fileName;
            //实现上传功能
            photo.transferTo(new File(finalPath));
            return "success";
        }
        ```

        

##### 九、拦截器

###### 1、拦截器的配置

- SpringMVC中的拦截器用于拦截控制器方法的执行

- SpringMVC中的拦截器需要实现HandlerInterceptor

- SpringMVC的拦截器必须在SpringMVC的配置文件中进行配置

    ```xml
    <!-- 配置方法一：拦截所有请求-->
    <bean class="com.atguigu.interceptor.FirstInterceptor"></bean>
    <ref bean="firstInterceptor"></ref>
    <!-- 以上两种配置方式都是对DispatcherServlet所处理的所有的请求进行拦截 -->
    
    <!-- 配置方法二：自定义拦截-->
    <mvc:interceptor>
        <mvc:mapping path="/**"/>
        <mvc:exclude-mapping path="/testRequestEntity"/>
        <ref bean="firstInterceptor"></ref>
    </mvc:interceptor>
    <!-- 
    	以上配置方式可以通过ref或bean标签设置拦截器，通过mvc:mapping设置需要拦截的请求，通过mvc:exclude-mapping设置需要排除的请求，即不需要拦截的请求
    -->
    ```

###### 2、拦截器的三个抽象方法

- preHandle：控制器方法执行之前执行preHandle()，其boolean类型的返回值表示是否拦截或放行，返回true为放行，即调用控制器方法；返回false表示拦截，即不调用控制器方法
- postHandle：控制器方法执行之后执行postHandle()
- afterComplation：处理完视图和模型数据，渲染视图完毕之后执行afterComplation()

###### 3、preHandle()返回true时（即放行Controller方法），拦截器各个方法的执行顺序（可以参照源码来理解，很简单）

```xml
<!-- 配置两个拦截器，来演示执行的顺序 -->
<ref bean="firstInterceptor"></ref>
<ref bean="secondInterceptor"></ref>
```

- preHandle
    - 会先执行firstInterceptor的，然后再执行secondInterceptor
    - 因为有一个interceptorList，会按照配置顺序装入拦截器，然后遍历interceptorList列表，每次索引值加1（且该索引值会放入常量中保存下来），按顺序执行preHandle
    - 如执行了3个拦截器之后，索引值常量是4（每次执行都++，因此是顺序执行，而且次数其实是5，因为MVC内置一个，配置了三个）
- postHandle
    - 会先执行secondInterceptor，再执行firstInterceptor
    - 同样的interceptorList，但是此次会逆序执行，且没有保存索引值常量
- afterComplation
    - 会先执行secondInterceptor，再执行firstInterceptor
    - 同样的interceptorList，但是此次会逆序执行，且是根据保存的索引值常量进行逆序执行（根据索引值5依次相减，因此是逆序执行）

###### 4、preHandle()返回false时（即不调用Controller方法），拦截器各个方法的执行顺序（可以参照源码来理解，很简单）

```xml
<!-- 配置四个拦截器，来演示执行的顺序 -->
<ref bean="firstInterceptor"></ref>
<ref bean="secondInterceptor"></ref>
<ref bean="thirdInterceptor"></ref><!-- 第三个拦截器置为false -->
<ref bean="forthInterceptor"></ref>
```

- preHandle
    - 因为第三个拦截器返回false，因此从第四个拦截器（是因为第三个返回了false，所以从第四个开始）开始之后的所有preHandle都不会去执行，而其他的preHandle根据顺序正常执行，且记录执行次数的索引值
- postHandle
    - 所有的postHandle都不会执行
- afterComplation
    - 因为第三个拦截器返回false，因此从第三个拦截器（因为索引值最大到有false之前一个的索引值）开始之后的所有afterComplation都不会去执行，且根据已经记录的索引值进行逆序执行



##### 十、异常处理器

- SpringMVC提供了一个处理控制器方法执行过程中所出现的异常的接口：HandlerExceptionResolver
- HandlerExceptionResolver接口的实现类有：
    - DefaultHandlerExceptionResolver：默认使用的
    - SimpleMappingExceptionResolver：可以自定义出现异常之后的操作
- SpringMVC提供了自定义的异常处理器SimpleMappingExceptionResolver

###### 1、基于配置的异常处理

```xml
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
        	<!--
        		properties的键表示处理器方法执行过程中出现的异常
        		properties的值表示若出现指定异常时，设置一个新的视图名称，跳转到指定页面
        	-->
            <!-- 当出现ArithmeticException时，跳转到error页面 -->
            <prop key="java.lang.ArithmeticException">error</prop>
        </props>
    </property>
    <!-- exceptionAttribute属性设置一个属性名，将出现的异常信息在请求域中进行共享，即“foo”为键，得到的异常为值放在请求域中 -->
    <property name="exceptionAttribute" value="foo"></property>
</bean>
```

###### 2、基于注解的异常处理

```java
//@ControllerAdvice将当前类标识为异常处理的组件
@ControllerAdvice
public class ExceptionController {

    //@ExceptionHandler用于设置所标识方法处理的异常，即出现该类型的异常时，会执行该方法
    @ExceptionHandler(ArithmeticException.class)
    //ex表示当前请求处理中出现的异常对象
    public String handleArithmeticException(Exception ex, Model model){
    	//通过Model类型，将异常信息放在请求域中
        model.addAttribute("ex", ex);
        //若出现了该类型异常，则跳转至foo页面
        return "foo";
    }

}
```



##### 十一、注解配置SpringMVC

- 使用配置类和注解代替web.xml和SpringMVC配置文件的功能

###### 1、创建初始化类，代替web.xml

- 在Servlet3.0环境中，容器会在类路径中查找实现javax.servlet.ServletContainerInitializer接口的类，如果找到的话就用它来配置Servlet容器（Servlet容器->Tomcat服务器）
- Spring提供了这个接口的实现，名为SpringServletContainerInitializer，这个类反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成。Spring3.2引入了一个便利的WebApplicationInitializer基础实现，名为AbstractAnnotationConfigDispatcherServletInitializer，当我们的类扩展了AbstractAnnotationConfigDispatcherServletInitializer并将其部署到Servlet3.0容器的时候，容器会自动发现它，并用它来配置Servlet上下文

```java
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 指定spring的配置类
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    /**
     * 指定SpringMVC的配置类
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebConfig.class};
    }

    /**
     * 指定DispatcherServlet的映射规则，即url-pattern
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * 添加过滤器
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceRequestEncoding(true);
        HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
        return new Filter[]{encodingFilter, hiddenHttpMethodFilter};
    }
}
```

###### 2、创建SpringConfig配置类，代替spring的配置文件

```java
@Configuration
public class SpringConfig {
	//ssm整合之后，spring的配置信息写在此类中
}
```

###### 3、创建WebConfig配置类，代替SpringMVC的配置文件

```java
@Configuration
//扫描组件
@ComponentScan("com.atguigu.mvc.controller")
//开启MVC注解驱动
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    //使用默认的servlet处理静态资源
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //配置文件上传解析器
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        FirstInterceptor firstInterceptor = new FirstInterceptor();
        registry.addInterceptor(firstInterceptor).addPathPatterns("/**");
    }
    
    //配置视图控制
    
    /*@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }*/
    
    //配置异常映射
    /*@Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties prop = new Properties();
        prop.setProperty("java.lang.ArithmeticException", "error");
        //设置异常映射
        exceptionResolver.setExceptionMappings(prop);
        //设置共享异常信息的键
        exceptionResolver.setExceptionAttribute("ex");
        resolvers.add(exceptionResolver);
    }*/

    //配置生成模板解析器
    @Bean
    public ITemplateResolver templateResolver() {
        WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        // ServletContextTemplateResolver需要一个ServletContext作为构造参数，可通过WebApplicationContext 的方法获得
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(
                webApplicationContext.getServletContext());
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        return templateResolver;
    }

    //生成模板引擎并为模板引擎注入模板解析器
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    //生成视图解析器并未解析器注入模板引擎
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }
}
```



##### 十二、SpringMVC执行流程

###### 1、SpringMVC常用组件

- DispatcherServlet：前端控制器，不需要工程师开发，由框架提供
    - 作用：统一处理请求和响应，整个流程控制的中心，由它调用其它组件处理用户的请求

- HandlerMapping：处理器映射器，不需要工程师开发，由框架提供
    - 作用：根据请求的url、method等信息查找Handler，即控制器方法
- Handler：处理器，需要工程师开发
    - 作用：在DispatcherServlet的控制下Handler对具体的用户请求进行处理
- HandlerAdapter：处理器适配器，不需要工程师开发，由框架提供
    - 作用：通过HandlerAdapter对处理器（控制器方法）进行执行
- ViewResolver：视图解析器，不需要工程师开发，由框架提供
    - 作用：进行视图解析，得到相应的视图，例如：ThymeleafView、InternalResourceView、RedirectView
- View：视图
    - 作用：将模型数据通过页面展示给用户

###### 2、DispatcherServlet初始化过程

- DispatcherServlet 本质上是一个 Servlet，所以天然的遵循 Servlet 的生命周期。所以宏观上是 Servlet 生命周期来进行调度

- 初始化WebApplicationContext

    ```java
    protected WebApplicationContext initWebApplicationContext() {
        WebApplicationContext rootContext =
            WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        WebApplicationContext wac = null;
    
        if (this.webApplicationContext != null) {
            // A context instance was injected at construction time -> use it
            wac = this.webApplicationContext;
            if (wac instanceof ConfigurableWebApplicationContext) {
                ConfigurableWebApplicationContext cwac = (ConfigurableWebApplicationContext) wac;
                if (!cwac.isActive()) {
                    // The context has not yet been refreshed -> provide services such as
                    // setting the parent context, setting the application context id, etc
                    if (cwac.getParent() == null) {
                        // The context instance was injected without an explicit parent -> set
                        // the root application context (if any; may be null) as the parent
                        cwac.setParent(rootContext);
                    }
                    configureAndRefreshWebApplicationContext(cwac);
                }
            }
        }
        if (wac == null) {
            // No context instance was injected at construction time -> see if one
            // has been registered in the servlet context. If one exists, it is assumed
            // that the parent context (if any) has already been set and that the
            // user has performed any initialization such as setting the context id
            wac = findWebApplicationContext();
        }
        if (wac == null) {
            // No context instance is defined for this servlet -> create a local one
            // 创建WebApplicationContext
            wac = createWebApplicationContext(rootContext);
        }
    
        if (!this.refreshEventReceived) {
            // Either the context is not a ConfigurableApplicationContext with refresh
            // support or the context injected at construction time had already been
            // refreshed -> trigger initial onRefresh manually here.
            synchronized (this.onRefreshMonitor) {
                // 刷新WebApplicationContext
                onRefresh(wac);
            }
        }
    
        if (this.publishContext) {
            // Publish the context as a servlet context attribute.
            // 将IOC容器在应用域共享
            String attrName = getServletContextAttributeName();
            getServletContext().setAttribute(attrName, wac);
        }
    
        return wac;
    }
    ```

- 创建WebApplicationContext

    ```java
    protected WebApplicationContext createWebApplicationContext(@Nullable ApplicationContext parent) {
        Class<?> contextClass = getContextClass();
        if (!ConfigurableWebApplicationContext.class.isAssignableFrom(contextClass)) {
            throw new ApplicationContextException(
                "Fatal initialization error in servlet with name '" + getServletName() +
                "': custom WebApplicationContext class [" + contextClass.getName() +
                "] is not of type ConfigurableWebApplicationContext");
        }
        // 通过反射创建 IOC 容器对象
        ConfigurableWebApplicationContext wac =
            (ConfigurableWebApplicationContext) BeanUtils.instantiateClass(contextClass);
    
        wac.setEnvironment(getEnvironment());
        // 设置父容器
        wac.setParent(parent);
        String configLocation = getContextConfigLocation();
        if (configLocation != null) {
            wac.setConfigLocation(configLocation);
        }
        configureAndRefreshWebApplicationContext(wac);
    
        return wac;
    }
    ```

- DispatcherServlet初始化策略

    - FrameworkServlet创建WebApplicationContext后，刷新容器，调用onRefresh(wac)，此方法在DispatcherServlet中进行了重写，调用了initStrategies(context)方法，初始化策略，即初始化DispatcherServlet的各个组件

    ```java
    protected void initStrategies(ApplicationContext context) {
       initMultipartResolver(context);
       initLocaleResolver(context);
       initThemeResolver(context);
       initHandlerMappings(context);
       initHandlerAdapters(context);
       initHandlerExceptionResolvers(context);
       initRequestToViewNameTranslator(context);
       initViewResolvers(context);
       initFlashMapManager(context);
    }
    ```

###### 3、DispatcherServlet调用组件处理请求

- processRequest()

    - FrameworkServlet重写HttpServlet中的service()和doXxx()，这些方法中调用了processRequest(request, response)

    ```java
    protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        long startTime = System.currentTimeMillis();
        Throwable failureCause = null;
    
        LocaleContext previousLocaleContext = LocaleContextHolder.getLocaleContext();
        LocaleContext localeContext = buildLocaleContext(request);
    
        RequestAttributes previousAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = buildRequestAttributes(request, response, previousAttributes);
    
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
        asyncManager.registerCallableInterceptor(FrameworkServlet.class.getName(), new RequestBindingInterceptor());
    
        initContextHolders(request, localeContext, requestAttributes);
    
        try {
    		// 执行服务，doService()是一个抽象方法，在DispatcherServlet中进行了重写
            doService(request, response);
        }
        catch (ServletException | IOException ex) {
            failureCause = ex;
            throw ex;
        }
        catch (Throwable ex) {
            failureCause = ex;
            throw new NestedServletException("Request processing failed", ex);
        }
    
        finally {
            resetContextHolders(request, previousLocaleContext, previousAttributes);
            if (requestAttributes != null) {
                requestAttributes.requestCompleted();
            }
            logResult(request, response, failureCause, asyncManager);
            publishRequestHandledEvent(request, response, startTime, failureCause);
        }
    }
    ```

- doService()

    ```java
    @Override
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logRequest(request);
    
        // Keep a snapshot of the request attributes in case of an include,
        // to be able to restore the original attributes after the include.
        Map<String, Object> attributesSnapshot = null;
        if (WebUtils.isIncludeRequest(request)) {
            attributesSnapshot = new HashMap<>();
            Enumeration<?> attrNames = request.getAttributeNames();
            while (attrNames.hasMoreElements()) {
                String attrName = (String) attrNames.nextElement();
                if (this.cleanupAfterInclude || attrName.startsWith(DEFAULT_STRATEGIES_PREFIX)) {
                    attributesSnapshot.put(attrName, request.getAttribute(attrName));
                }
            }
        }
    
        // Make framework objects available to handlers and view objects.
        request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, getWebApplicationContext());
        request.setAttribute(LOCALE_RESOLVER_ATTRIBUTE, this.localeResolver);
        request.setAttribute(THEME_RESOLVER_ATTRIBUTE, this.themeResolver);
        request.setAttribute(THEME_SOURCE_ATTRIBUTE, getThemeSource());
    
        if (this.flashMapManager != null) {
            FlashMap inputFlashMap = this.flashMapManager.retrieveAndUpdate(request, response);
            if (inputFlashMap != null) {
                request.setAttribute(INPUT_FLASH_MAP_ATTRIBUTE, Collections.unmodifiableMap(inputFlashMap));
            }
            request.setAttribute(OUTPUT_FLASH_MAP_ATTRIBUTE, new FlashMap());
            request.setAttribute(FLASH_MAP_MANAGER_ATTRIBUTE, this.flashMapManager);
        }
    
        RequestPath requestPath = null;
        if (this.parseRequestPath && !ServletRequestPathUtils.hasParsedRequestPath(request)) {
            requestPath = ServletRequestPathUtils.parseAndCache(request);
        }
    
        try {
            // 处理请求和响应
            doDispatch(request, response);
        }
        finally {
            if (!WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
                // Restore the original attribute snapshot, in case of an include.
                if (attributesSnapshot != null) {
                    restoreAttributesAfterInclude(request, attributesSnapshot);
                }
            }
            if (requestPath != null) {
                ServletRequestPathUtils.clearParsedRequestPath(request);
            }
        }
    }
    ```

- doDispatch()

    ```java
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerExecutionChain mappedHandler = null;
        boolean multipartRequestParsed = false;
    
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
    
        try {
            ModelAndView mv = null;
            Exception dispatchException = null;
    
            try {
                processedRequest = checkMultipart(request);
                multipartRequestParsed = (processedRequest != request);
    
                // Determine handler for the current request.
                /*
                	mappedHandler：调用链
                    包含handler、interceptorList、interceptorIndex
                	handler：浏览器发送的请求所匹配的控制器方法
                	interceptorList：处理控制器方法的所有拦截器集合
                	interceptorIndex：拦截器索引，控制拦截器afterCompletion()的执行
                */
                mappedHandler = getHandler(processedRequest);
                if (mappedHandler == null) {
                    noHandlerFound(processedRequest, response);
                    return;
                }
    
                // Determine handler adapter for the current request.
               	// 通过控制器方法创建相应的处理器适配器，调用所对应的控制器方法
                HandlerAdapter ha = getHandlerAdapter(mappedHandler.getHandler());
    
                // Process last-modified header, if supported by the handler.
                String method = request.getMethod();
                boolean isGet = "GET".equals(method);
                if (isGet || "HEAD".equals(method)) {
                    long lastModified = ha.getLastModified(request, mappedHandler.getHandler());
                    if (new ServletWebRequest(request, response).checkNotModified(lastModified) && isGet) {
                        return;
                    }
                }
    			
                // 调用拦截器的preHandle()
                if (!mappedHandler.applyPreHandle(processedRequest, response)) {
                    return;
                }
    
                // Actually invoke the handler.
                // 由处理器适配器调用具体的控制器方法，最终获得ModelAndView对象
                mv = ha.handle(processedRequest, response, mappedHandler.getHandler());
    
                if (asyncManager.isConcurrentHandlingStarted()) {
                    return;
                }
    
                applyDefaultViewName(processedRequest, mv);
                // 调用拦截器的postHandle()
                mappedHandler.applyPostHandle(processedRequest, response, mv);
            }
            catch (Exception ex) {
                dispatchException = ex;
            }
            catch (Throwable err) {
                // As of 4.3, we're processing Errors thrown from handler methods as well,
                // making them available for @ExceptionHandler methods and other scenarios.
                dispatchException = new NestedServletException("Handler dispatch failed", err);
            }
            // 后续处理：处理模型数据和渲染视图
            processDispatchResult(processedRequest, response, mappedHandler, mv, dispatchException);
        }
        catch (Exception ex) {
            triggerAfterCompletion(processedRequest, response, mappedHandler, ex);
        }
        catch (Throwable err) {
            triggerAfterCompletion(processedRequest, response, mappedHandler,
                                   new NestedServletException("Handler processing failed", err));
        }
        finally {
            if (asyncManager.isConcurrentHandlingStarted()) {
                // Instead of postHandle and afterCompletion
                if (mappedHandler != null) {
                    mappedHandler.applyAfterConcurrentHandlingStarted(processedRequest, response);
                }
            }
            else {
                // Clean up any resources used by a multipart request.
                if (multipartRequestParsed) {
                    cleanupMultipart(processedRequest);
                }
            }
        }
    }
    ```

- processDispatchResult()

    ```java
    private void processDispatchResult(HttpServletRequest request, HttpServletResponse response,
                                       @Nullable HandlerExecutionChain mappedHandler, @Nullable ModelAndView mv,
                                       @Nullable Exception exception) throws Exception {
    
        boolean errorView = false;
    
        if (exception != null) {
            if (exception instanceof ModelAndViewDefiningException) {
                logger.debug("ModelAndViewDefiningException encountered", exception);
                mv = ((ModelAndViewDefiningException) exception).getModelAndView();
            }
            else {
                Object handler = (mappedHandler != null ? mappedHandler.getHandler() : null);
                mv = processHandlerException(request, response, handler, exception);
                errorView = (mv != null);
            }
        }
    
        // Did the handler return a view to render?
        if (mv != null && !mv.wasCleared()) {
            // 处理模型数据和渲染视图
            render(mv, request, response);
            if (errorView) {
                WebUtils.clearErrorRequestAttributes(request);
            }
        }
        else {
            if (logger.isTraceEnabled()) {
                logger.trace("No view rendering, null ModelAndView returned.");
            }
        }
    
        if (WebAsyncUtils.getAsyncManager(request).isConcurrentHandlingStarted()) {
            // Concurrent handling started during a forward
            return;
        }
    
        if (mappedHandler != null) {
            // Exception (if any) is already handled..
            // 调用拦截器的afterCompletion()
            mappedHandler.triggerAfterCompletion(request, response, null);
        }
    }
    ```

###### 4、SpringMVC的执行流程

- 用户向服务器发送请求，请求被SpringMVC 前端控制器 DispatcherServlet捕获
- DispatcherServlet对请求URL进行解析，得到请求资源标识符（URI），判断请求URI对应的映射
    - 该映射不存在（控制器方法不存在）
        - 再判断是否配置了mvc:default-servlet-handler（默认的servlet处理器）
        - 如果没配置，则控制台报映射查找不到，客户端展示404错误
        -  如果有配置，则访问目标资源（一般为静态资源，如：JS,CSS,HTML），找不到客户端也会展示404错误
    - 该映射存在（控制器方法存在）
        - 根据该URI，调用HandlerMapping获得该Handler配置的所有相关的对象（包括Handler对象以及Handler对象对应的拦截器），最后以HandlerExecutionChain执行链对象的形式返回
        - DispatcherServlet 根据获得的Handler，选择一个合适的HandlerAdapter
        - 如果成功获得HandlerAdapter，此时将开始执行拦截器的preHandler(…)方法【正向】
        - 提取Request中的模型数据，填充Handler入参，开始执行Handler（Controller)方法，处理请求。在填充Handler的入参过程中，根据你的配置，Spring将帮你做一些额外的工作
            - HttpMessageConverter： 将请求消息（如Json、xml等数据）转换成一个对象，将对象转换为指定的响应信息
            - 数据转换：对请求消息进行数据转换。如String转换成Integer、Double等
            - 数据格式化：对请求消息进行数据格式化。 如将字符串转换成格式化数字或格式化日期等
            - 数据验证： 验证数据的有效性（长度、格式等），验证结果存储到BindingResult或Error中
        - Handler执行完成后，向DispatcherServlet 返回一个ModelAndView对象
        - 此时将开始执行拦截器的postHandle(…)方法【逆向】
        - 根据返回的ModelAndView（此时会判断是否存在异常：如果存在异常，则执行HandlerExceptionResolver进行异常处理）选择一个适合的ViewResolver进行视图解析，根据Model和View，来渲染视图
        - 渲染视图完毕执行拦截器的afterCompletion(…)方法【逆向】
        - 将渲染结果返回给客户端
