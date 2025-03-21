package com.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.springboot.mapper")  // 确保扫描到 com.springboot.mapper 包
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}