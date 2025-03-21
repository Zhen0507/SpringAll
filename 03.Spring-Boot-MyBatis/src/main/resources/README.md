# Spring Boot 集成 MyBatis 示例

## 项目概述
这个项目展示了如何在 Spring Boot 应用中集成 MyBatis 框架，实现对数据库的操作。通过这个项目，你可以学习到如何使用 MyBatis 的注解方式进行数据库查询、添加、更新和删除操作。

## 技术栈
- Spring Boot 3.2.3
- Java 21
- MyBatis 3.0.3
- Druid 1.2.20（数据库连接池）
- MySQL（数据库）
- Maven（项目管理工具）

## 项目结构
```
03.Spring-Boot-MyBatis/
├── src/main/java/com/springboot/
│   ├── Application.java                 # 应用入口类
│   ├── bean/
│   │   └── Student.java                 # 学生实体类
│   ├── mapper/
│   │   └── StudentMapper.java           # MyBatis映射接口
│   ├── service/
│   │   ├── StudentService.java          # 服务接口
│   │   └── impl/
│   │       └── StudentServiceImp.java   # 服务实现类
│   └── controller/
│       └── TestController.java          # REST控制器
└── src/main/resources/
    ├── application.yml                  # 应用配置文件
    └── mapper/                          # XML映射文件目录（本项目使用注解方式）
```

## 核心代码详解

### 应用入口类
`Application.java` 是应用的入口类，使用 `@SpringBootApplication` 注解标识为 Spring Boot 应用，使用 `@MapperScan` 注解指定 MyBatis 要扫描的包。

### 实体类
`Student.java` 定义了学生实体，包含学号（sno）、姓名（name）和性别（sex）属性。

### Mapper接口
`StudentMapper.java` 使用 MyBatis 的注解方式定义了对数据库的操作：
- `@Insert` - 添加学生记录
- `@Update` - 更新学生信息
- `@Delete` - 删除学生记录
- `@Select` - 查询学生信息
- `@Results` - 定义结果映射

### 服务层
- `StudentService.java` - 定义服务接口
- `StudentServiceImp.java` - 实现服务接口，通过注入 StudentMapper 实现对数据库的操作

### 控制器
`TestController.java` 是一个 REST 控制器，提供了查询学生信息的 API 端点。

### 配置文件
`application.yml` 中配置了数据库连接、MyBatis 以及 Druid 连接池的相关参数。

## 如何运行

### 准备工作
1. 确保安装了 Java 21 或更高版本
2. 确保 MySQL 数据库已安装并运行
3. 创建数据库和表：
```sql
CREATE DATABASE springboot;
USE springboot;
CREATE TABLE student (
  sno VARCHAR(10) NOT NULL,
  sname VARCHAR(20) NOT NULL,
  ssex VARCHAR(10) NOT NULL,
  PRIMARY KEY(sno)
);
```

### 运行应用
1. 在 IDE 中打开项目，运行 `Application.java`
2. 或者使用 Maven 命令：
```
mvn spring-boot:run
```

### 测试应用
访问 http://localhost:8080/querystudent?sno=001 可以查询学号为001的学生信息。

## 注意事项
- 确保数据库连接参数（URL、用户名、密码）与你的环境一致
- 项目使用了 MyBatis 的注解方式，没有使用 XML 配置方式
- 使用了 Druid 作为数据库连接池，可以根据需要调整连接池参数 