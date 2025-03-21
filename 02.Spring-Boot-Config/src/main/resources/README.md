# Spring Boot 配置

## 项目简介
本项目展示了Spring Boot的配置功能，包括属性注入、配置类、配置文件等多种配置方式。

## 技术栈
- Spring Boot 3.2.3
- Java 21
- Maven

## 项目结构
```
src/main/java/com/springboot
├── Application.java                    - 应用程序入口类
├── bean
│   ├── BlogProperties.java             - 通过@Value注解读取配置
│   ├── ConfigBean.java                 - 通过@ConfigurationProperties读取配置
│   └── TestConfigBean.java             - 读取指定配置文件
├── controller
│   └── IndexController.java            - REST控制器
└── resources
    ├── application.properties          - 主配置文件
    ├── application-dev.properties      - 开发环境配置
    ├── application-prod.properties     - 生产环境配置
    └── test.properties                 - 测试配置文件
```

## 关键代码解析

### 应用入口类 (Application.java)
```java
@SpringBootApplication
@EnableConfigurationProperties({ConfigBean.class, TestConfigBean.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setAddCommandLineProperties(false);
        app.run(args);
    }
}
```

- `@EnableConfigurationProperties`: 启用对`ConfigBean`和`TestConfigBean`配置类的支持
- `app.setAddCommandLineProperties(false)`: 禁用命令行属性的自动添加

### 配置方式

#### 1. 通过@Value注入配置 (BlogProperties.java)
```java
@Component
public class BlogProperties {
    @Value("${mrbird.blog.name}")
    private String name;
    
    @Value("${mrbird.blog.title}")
    private String title;
    
    // getters and setters
}
```

#### 2. 通过@ConfigurationProperties注入配置 (ConfigBean.java)
```java
@ConfigurationProperties(prefix = "mrbird.blog")
public class ConfigBean {
    private String name;
    private String title;
    private String wholeTitle;
    
    // getters and setters
}
```

#### 3. 读取指定配置文件 (TestConfigBean.java)
```java
@ConfigurationProperties(prefix = "test")
@PropertySource("classpath:test.properties")
public class TestConfigBean {
    private String name;
    private int age;
    
    // getters and setters
}
```

### 配置文件

#### application.properties
```properties
mrbird.blog.name=mrbird's blog
mrbird.blog.title=Spring Boot
mrbird.blog.wholeTitle=${mrbird.blog.name}--${mrbird.blog.title}

spring.profiles.active=prod
```

#### 多环境配置
- `application-dev.properties`: 开发环境配置
- `application-prod.properties`: 生产环境配置

### 控制器 (IndexController.java)
```java
@RestController
public class IndexController {
    @Autowired
    private BlogProperties blogProperties;
    @Autowired
    private ConfigBean configBean;
    @Autowired
    private TestConfigBean testConfigBean;
    
    @RequestMapping("/")
    String index() {
        return testConfigBean.getName()+"，"+testConfigBean.getAge();
    }
}
```

## 配置方式总结
1. **@Value注解**: 适用于简单配置项的注入
2. **@ConfigurationProperties**: 适用于批量配置项的注入
3. **@PropertySource**: 指定加载特定的配置文件
4. **多环境配置**: 通过`spring.profiles.active`激活不同环境的配置

## 启动应用
1. 在IDE中运行Application类
2. 通过Maven命令启动: `mvn spring-boot:run`
3. 打包后启动: `java -jar target/Spring-Boot-Config-0.0.1-SNAPSHOT.jar` 