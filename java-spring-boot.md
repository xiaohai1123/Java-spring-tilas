# java-spring-boot

------

## HTTP

概念：超文本传输协议，规定了浏览器和服务器之间数据传输的规则

特点：

​		1.基于TCP协议：面向连接，安全

​		2.基于请求-响应模型的：一次请求对应一次响应

​		3.HTTP协议是无状态的协议：对于事务处理没有记忆能力。每次请求-响应都是独立的

​				缺点：多次请求间不能共享数据

​				优点：速度快

------

### HTTP-请求数据格式

**请求行：**请求数据的第一行(请求方式、资源路径、协议)

**请求头：**第二行开始，格式key：value

**请求体：**POST请求，存放请求参数

------

### 请求响应介绍-HTTP响应格式

**响应行：**响应数据第一行(协议、状态码、描述)

**响应头：**第二行开始，格式key：value

**响应体：**最后一部分，存放响应数据

```javascript
1xx		响应中-临时状态码，表示请求已经接收，告诉客户端应该继续请求或者如果它已经完成则忽略它
2xx		成功-表示请求已经被接收，处理已完成
3xx		重定向-重定向到其他地方；让客户端再发起一次请求以完成整个处理
4xx		客户端错误-处理发生错误，责任在客户端。如：请求了不存在的资源、客户端未被授权、禁止访问等
5xx		服务器错误-处理发生错误，责任在服务端。如：程序抛出异常等	
```

------

## Web服务器

Web服务器是一个软件程序，对HTTP协议的操作进行封装，使得程序员不必直接对协议进行操作，让Web开发更加便捷。主要功能是"提供网上信息浏览服务"。

**请求响应：**

​		请求：获取请求数据

​		响应：请求响应数据

​		BS架构：Browser/Server，浏览器/服务器架构模式。客户端只需要浏览器，应用程序的逻辑和数据都存储在服务端。(维护方便、体验一般)

​		CS架构：Client/Server，客户端/服务器架构模式。(开发、维护麻烦、体验不错)

------

**原始方式获取请求参数：**

​		Controller方法形参中声明HttpServletRequest对象

​		调用对象的getParameter(参数名)

**SpringBoot中接收简单参数：**

​		请求参数名与方法形参变量名相同

​		会自动进行类型转换

**@RequestParam注解：**

​		方法形参名称与请求参数名称不匹配，通过该注解完成映射

​		该注解的required属性默认是true，代表请求参数必须传递

------

**数组集合参数：**

​	数组：请求参数名与形参中数组变量名相同，可以直接使用数组封装

​	集合：请求参数名与形参中集合变量名相同，通过@RequestParam绑定参数关系

```java
@RestController
public class RequestController {

    @RequestMapping("/simpleParam")
    public String simpleParam(HttpServletRequest request) {
        String name = request.getParameter("name");
        String ageStr = request.getParameter("age");

        int age = Integer.parseInt(ageStr);
        System.out.println(name + ":" + age);
        return "ok";
    }

    @RequestMapping("/simpleParam2")
    public String simpleParam2(@RequestParam(name = "name") String username, Integer age) {

        System.out.println(username + ":" + age);
        return "ok";
    }

    //实体参数
    @RequestMapping("/simplePojo")
    public String simplePojo(User user) {
        System.out.println(user);
        return "ok";
    }

    //数组集合参数
    @RequestMapping("/arrayParam")
    public String arrayParam(String[] hobby) {
        System.out.println(Arrays.toString(hobby));
        return "ok";
    }

    @RequestMapping("/listParam")
    public String arrayParam(@RequestParam List<String> hobby) {
        System.out.println(hobby);
        return "ok";
    }

    //日期时间参数
    @RequestMapping("/dateParam")
    public String dateParam(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime updateTime) {
        System.out.println(updateTime);
        return "ok";
    }

    //json参数
    @RequestMapping("/jsonParam")
    public String jsonParam(@RequestBody User user) {
        System.out.println(user);
        return "ok";
    }

    //路径参数
    @RequestMapping("/path/{id}")
    public String pathParam(@PathVariable Integer id) {
        System.out.println(id);
        return "ok";
    }

    @RequestMapping("/path/{id}/{name}")
    public String pathParam2(@PathVariable Integer id, @PathVariable String name) {
        System.out.println(name + ":" + id);
        return "ok";
    }
}
```

------

### 响应数据

**@ResponseBody：**

​	类型：方法注解、类注解

​	位置：Controller方法上/类上

​	作用：将方法返回值直接响应，如果返回值类型是 实体对象/集合，将会转换为json格式响应

​	说明：@RestController = @Controller + @ResponseBody

------

**统一响应结果：**

Result(code、msg、data)

```java
@RestController
public class ResponseController {

    @RequestMapping("/hello")
    public Result hello() {
        System.out.println("Hello World!~");
        return Result.success("Hello World!~");
    }

    @RequestMapping("/getAddr")
    public Result getAddr() {
        Address address = new Address();
        address.setProvince("广东");
        address.setCity("深圳");
        return Result.success(address);
    }

    @RequestMapping("/listAddr")
    public Result listAddr() {
        List<Address> list = new ArrayList<>();

        Address address = new Address();
        address.setProvince("广东");
        address.setCity("深圳");

        Address address2 = new Address();
        address2.setProvince("陕西");
        address2.setCity("西安");

        list.add(address);
        list.add(address2);

        return Result.success(list);
    }
}
```

------

### 三层架构

Controller：控制层，接收前端发送的请求，对请求进行处理，并响应数据。

service：业务逻辑层，处理具体的业务逻辑。

dao：数据访问层(Data Access Object)(持久层)，负责数据访问操作，包括数据的增删改查

------

### 分层解耦

内聚：软件中各个功能模块内部的功能联系

耦合：衡量软件中各个层/模块之间的依赖、关联的程度

软件设计原则：高内聚低耦合

------

控制反转：简称IOC。对象的创建控制权由程序自身转移到外部(容器)，这种思想称为控制反转。

依赖注入：简称DI。容器为应用程序提供运行时，所依赖的资源，称之为依赖注入。

Bean对象：IOC容器中创建、管理的对象，称之为Bean。

------

**Bean的声明**

要把某个对象交给IOC容器管理，需要在对应的类上加上如下注解之一：

```java
@Component		声明bean的基础注解				 不属于以下三类，用此注解
@Controller		@Component的衍生注解			  标注在控制器类上
@Service		@Component的衍生注解			  标注在业务类上
@Respository	@Component的衍生注解			  标注在数据访问类上(由于与mybatis整合,用的少)
```

注意事项：

​	1.声明bean的时候，可以通过value属性指定bean的名字，如果没有指定，默认为类名首字母小写

​	2.使用以上四个注解都可以声明bean，但是在springboot集成web开发中，声明控制器bean只能用@Controller

------

**Bean组件扫描**

1.前面声明的bean的四大注解，要想生效，还需要被组件扫描注解@ComponentScan扫描

2.@ConponentScan注解虽然没有显式配置，但是实际上已经包含在启动类声明注解@SpringBootApplication中，默认扫描范围是启动类所在包及其子包

------

**Bean注入**

1.@Autowired注解，默认是按照类型进行，如果存在多个相同类型的bean，将会报错

2.如果同类型的bean存在多个：

​		1.@Primary

​		2.@Autowired + @Qualifier("bean的名称")

​		3.@Resource(name="bean的名称")

3.@Resource与@Autowired区别

​		1.@Autowired是spring框架提供的注解，而@Resource是JDK提供的注解

​		2.@Autowired默认是按照类型注入，而@Resource默认是按照名称注入

------

## Mybatis

Mybatis是一款优秀的持久层框架，用于简化JDBC的开发

**JDBC：**就是使用java语言操作关系型数据库的一套API

------

### 数据库连接池

数据库连接池是个容器，负责分配、管理数据库连接(Connection)

它允许应用程序重复使用一个现有的数据库连接，而不是重新建立一个

释放空闲时间超过最大空闲时间的连接，来避免因为没有释放连接而引起数据库连接遗漏

**优势：**

​		资源重用

​		提升系统响应速度

​		避免数据库连接遗漏

------

标准接口：DataSource

​		官方sun提供的数据库连接池接口，由第三方组织实现此接口

​		功能：获取连接		

```java
Connection getConnetcion() throws SQLExcption;
```

Druid(德鲁伊)

​		Druid连接池是阿里巴巴开源的数据库连接池项目

​		功能强大，性能优秀，是java语言最好的数据库连接池之一

------

### Lombok

Lombok是一个实用的java类库，能通过注解的形式自动生成构造器，getter/setter、equals、hashcode、toString等方法，并可以自动化生成日志变量，简化java开发、提高效率

```java
@Getter/@Setter				为所有的属性提供get/set方法
@ToString					会给类自动生成易阅读的toString方法
@EqualsAndHashCode			根据类所拥有的非静态字段自动重写equals方法和hashCode方法
@Data						为实体类生成无参的构造器方法
@AllArgsConstructor			为实体类生成除了static修饰的字段之外带有各参数的构造器方法
```

注意事项：Lombok会在编译时，自动生成对应的java代码。我们使用Lombok时，还需要安装一个lombok的插件

------

**预编译SQL**

​		性能更高

​		更安全(防止SQL注入)

**SQL注入：**

SQL注入是通过操作输入的数据来修改事先定义好的SQL语句，以达到执行代码对服务器攻击的方法

------

**数据封装**

实体类属性名和数据库表查询返回的字段名一致，mybatis会自动封装。

如果实体类属性名和数据库表查询返回的字段名不一致，不能自动封装

**起别名：**在SQL语句中，对不一样的列名起别名，别名和实体类属性名一样

```java
@Select("select id,username,password,name,gender,image,jod,dept_id deptId,create_time createTime from emp where id = #{id}")
public Emp getByld(integer id);
```

**手动结果映射：**通过@Results及@Result进行手动结果映射

```java
@Select("select * from emp where id=#{id}")
@Result({
	@Result(column="dept_id",property="deptId"),
	@Result(column="create_time",property="createTime"),
	@Result(column="update_time",property="updateTime")})
public Emp getById(integer id);
```

**开启驼峰命名：**如果字段名与属性名符合驼峰命名规则，mybatis会自动通过驼峰命名规则映射

```java
#开始mybatis的驼峰命名自动映射开关    a_column->aColumn
mybatis.configuration.map-underscore-to-camel-case=true
```

------

### XML映射文件

XML映射文件的名称与Mapper接口名称一致，并且将XML映射文件和Mapper接口放置在相同包下(同包同名)

XML映射文件的namespace属性为Mapper接口全限定名一致

XML映射文件中sql语句的id与Mapper接口中的方法名一致，并保持返回类型一致

![image-20230530083121884](C:\Users\小海\AppData\Roaming\Typora\typora-user-images\image-20230530083121884.png)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.xiaohai.mapper.UserMapper">

    <!--resultType单条记录所封装的类型-->
    <select id="list" resultType="com.xiaohai.pojo.User">
        select * from user
    </select>
</mapper>
```

------

### 动态SQL

<if>：用于判断条件是否成立。使用test属性进行条件判断，如果条件问true，则拼接SQL

```xml
<if test="name != null">
	name lisk concat('%',#{name},'%')
</if>
```

<where>：where元素只会在子元素有内容的情况下才插入where子句。而且会自动的去除子句开头的AND或OR。

```xml
<where>
            <if test="name != null">
                name like concat('%',#{name},'%')
            </if>
            <if test="sex != null">
                and sex = #{sex}
            </if>
            <if test="firsttime != null">
                and firsttime = #{firsttime}
            </if>
</where>
```

<set>：动态地在行首插入SET关键字，并会删掉额外的逗号(用在update语句中)

<foreach>：

​	collection:遍历的集合
​    item:遍历出来的元素
​    separator:分隔符
​    open:遍历开始前拼接的SQL字段
​    close:遍历结束后拼接的SQL字段

```xml
delete  from user where id in
        <foreach collection="ids" item="id" separator="," open="(" close=")">
            #{id}
 		</foreach>
```

#### SQL片段

<sql>:定义可重用的SQL片段

<include>：通过属性refid，指定包含的sql片段

------

## 开发规范-Restful

REST，表述性状态转换，它是一种软件架构风格

![image-20230530140822806](C:\Users\小海\AppData\Roaming\Typora\typora-user-images\image-20230530140822806.png)

注意事项：REST是风格，是约定方式，约定不是规定，可以打破

​					描述模块的功能通常使用复数，也就是加s的格式来描述，表示此类资源，而非单个资源。如：users、emps、books

------

## Spring boot

**`@RequestParam`：**

常用来处理简单类型的绑定，其原理是通过`Request.getParameter()`获取参数值的。
 注解有两个属性:`value`、`required`;`value`用来指定要传入值的id名称(即请求参数的key对应,也是表单属性的name的值对应),`required`用来指示参数是否必须绑定,默认为`true`即请求参数必须携带该参数,不携带将报错。

**`@RequestBody`：**

适用于以POST为请求方式,请求参数以Json格式放在请求体中的前端请求,所以在使用\接收数据时，一般都用POST方式进行提交，并将参数放以Json的格式放在请求体中。

在后端接口中，`@RequestBody`与`@RequestParam()`可以同时使用，但得注意`@RequestBody`最多只能有一个，而`@RequestParam()`可以有多个。

**`@PathVariable`：**

是spring3.0的一个新功能：接收请求路径中占位符的值,这里的所谓占位符就是将请求参数放在请求路径中成为路径的一部分,通过`@PathVariable`来获取路径中的参数,从而获取其中的参数值。

**`@DateTimeFormat`：**

用来处理前端传入的时间数据，转换为相应的时间格式

```java
@DateTimeFormat(pattern = "yyyy-MM-dd")
private Date date;
```

**`@Mapper`：**

在接口类上面添加了`@Mapper`，在编译之后会生成相应的接口实现类

添加位置：接口类上面

**`@MapperScan`：**

指定要扫描的`Mapper`接口类的包路径，然后包下面的所有接口在编译之后都会生成相应的实现类

添加位置：是在`Springboot`启动类上面添加

**`@MapperScan`和`@Mapper`区别及理解：**

扫描项目中的`Dao`层，将`dao`接口类注入到`Spring`，能够让其他类进行引用；

`@Mapper`：在dao接口类中，添加此注解；麻烦的在于，每个`dao`接口类都必须添加此注解；
`@MapperScan`：可以指定要扫描的`dao`接口类的路径，可以在启动类中添加此注解，可替代`@Mapper`注解(此模块内dao接口类不用都添加`@Mapper`注解)

**`@autowired`和`@resource`区别：**

`@Autowired` 是 `Spring` 框架中的注解，可以用来标注字段、构造器、方法等，来告诉 `Spring` 容器需要自动注入的对象。它可以通过类型和名称来查找匹配的 `Bean`。

`@Resource` 是 `Java` 标准库中的注解，是 JSR-250 规范的一部分。和 `@Autowired` 一样可以用来标注字段和方法。不过`@Resource` 默认按照名称来查找 `Bean`，如果找不到，才会按类型来查找。

总结，两者都可以用来完成依赖注入，主要区别在于使用场景不同：使用`@Autowired` 更适用于 `Spring` 框架中，而`@Resource` 更适用于 `Java SE`环境 。

**`@Slf4j`：**

 该注解的作用主要是操作在idea中的控制台中打印的日志信息。该注解相当于代替了以下的代码：`private final Logger logger = LoggerFactory.getLogger(当前类名.class);`

使用指定类初始化日志对象，在日志输出的时候，可以打印出日志信息所在的类。

**`@Value注解:`**

通常用于外部配置的属性注入，具体用法为：`@Value("${配置文件中的key}")`

------

### **配置信息**

```
server.port=8081
#配置数据库的连接信息
#驱动类名称
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#数据库连接的url
spring.datasource.url=jdbc:mysql://localhost:3306/tlias?autoReconnect=true&useSSL=false&characterEncoding=utf-8&serverTimezone=UTC
#连接数据库的用户名
spring.datasource.username=root
#连接数据库的密码
spring.datasource.password=522620995hai

#开始mybatis的驼峰命名自动映射开关    a_column->aColumn
mybatis.configuration.map-underscore-to-camel-case=true

#配置mybatis的日志，指定输出到控制台
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl

#配置单个文件最大上传大小
spring.servlet.multipart.max-file-size=10MB

#配置单个请求最大上传大小(一次性上传大小)
spring.servlet.multipart.max-request-size=100MB
```

**yml配置文件：**

大小写敏感

数值前面必须有空格，作为分隔符

使用缩进表示层级关系，缩进时，不允许使用Tab键，只能使用空格(idea中会自动将Tab转换为空格)

缩进的空格数目不重要，只要相同层级的元素对齐即可

#表示注释，从这个字符一直到行尾，都会被解析器忽略

```yml
#配置服务器信息
server:
	port: 8080
	address: 127.0.0.1
```

**yml数据格式：**

对象/Map集合：

```yml
user:
 name: zhangsan
 age: 18
 passsword: 123456
```

**数组/List/Set集合：**

```yml
hobby:
 - java
 - game
 - sport
```

