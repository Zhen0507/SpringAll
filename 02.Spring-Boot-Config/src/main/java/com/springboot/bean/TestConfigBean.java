package com.springboot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * TestConfigBean 类用于从配置文件中加载以 "test" 为前缀的属性，并将其映射到类的字段中。
 * 该类使用了 Spring 的配置注解，能够自动将配置文件中的属性值注入到类的实例中。
 */
@Configuration
@ConfigurationProperties(prefix="test")
@PropertySource("classpath:test.properties")
@Component
public class TestConfigBean {
    private String name; // 映射配置文件中的 test.name 属性
    private int age;     // 映射配置文件中的 test.age 属性

    /**
     * 获取 name 属性的值。
     *
     * @return 返回 name 属性的值。
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 name 属性的值。
     *
     * @param name 要设置的 name 属性值。
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取 age 属性的值。
     *
     * @return 返回 age 属性的值。
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置 age 属性的值。
     *
     * @param age 要设置的 age 属性值。
     */
    public void setAge(int age) {
        this.age = age;
    }
}
