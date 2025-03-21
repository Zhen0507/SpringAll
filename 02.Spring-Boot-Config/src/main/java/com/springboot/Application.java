package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.springboot.bean.ConfigBean;
import com.springboot.bean.TestConfigBean;

/**
 * Application类是Spring Boot应用的启动类。
 * 使用@SpringBootApplication注解标记该类为Spring Boot应用的主配置类，
 * 并且自动启用Spring Boot的自动配置和组件扫描。
 *
 * 使用@EnableConfigurationProperties注解启用对指定配置类的支持，
 * 这里启用了ConfigBean和TestConfigBean两个配置类。
 */
@SpringBootApplication
@EnableConfigurationProperties({ConfigBean.class, TestConfigBean.class})
//@ImportResource({"classpath:some-application.xml"})
public class Application {

    /**
     * main方法是Spring Boot应用的入口方法。
     * 该方法创建并配置SpringApplication对象，然后启动Spring Boot应用。
     *
     * @param args 命令行参数，传递给Spring Boot应用。
     */
    public static void main(String[] args) {
        // 创建SpringApplication对象，指定Application类为配置源
        SpringApplication app = new SpringApplication(Application.class);

        // 禁用命令行属性的自动添加，确保应用仅使用配置文件中的属性
        app.setAddCommandLineProperties(false);

        // 启动Spring Boot应用
        app.run(args);
    }
}
