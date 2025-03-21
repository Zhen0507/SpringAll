package com.springboot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ConfigBean类用于映射配置文件中的属性，前缀为"mrbird.blog"。
 * 通过@ConfigurationProperties注解，将配置文件中的属性值注入到该类的字段中。
 */
@ConfigurationProperties(prefix="mrbird.blog")
public class ConfigBean {
    private String name;    // 博客的名称
    private String title;   // 博客的标题
    private String wholeTitle; // 博客的完整标题

    /**
     * 获取博客的名称。
     *
     * @return 博客的名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置博客的名称。
     *
     * @param name 博客的名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取博客的标题。
     *
     * @return 博客的标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置博客的标题。
     *
     * @param title 博客的标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取博客的完整标题。
     *
     * @return 博客的完整标题
     */
    public String getWholeTitle() {
        return wholeTitle;
    }

    /**
     * 设置博客的完整标题。
     *
     * @param wholeTitle 博客的完整标题
     */
    public void setWholeTitle(String wholeTitle) {
        this.wholeTitle = wholeTitle;
    }
}
