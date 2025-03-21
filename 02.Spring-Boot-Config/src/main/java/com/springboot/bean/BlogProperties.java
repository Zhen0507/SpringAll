package com.springboot.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * BlogProperties类用于从配置文件中读取博客相关的属性，并将其注入到Spring管理的Bean中。
 * 该类通过@Value注解从配置文件中获取属性值，并提供了相应的getter和setter方法。
 */
@Component
public class BlogProperties {

	/**
	 * 博客名称，从配置文件中读取，配置文件中的键为"mrbird.blog.name"。
	 */
	@Value("${mrbird.blog.name}")
	private String name;

	/**
	 * 博客标题，从配置文件中读取，配置文件中的键为"mrbird.blog.title"。
	 */
	@Value("${mrbird.blog.title}")
	private String title;

	/**
	 * 获取博客名称。
	 *
	 * @return 博客名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置博客名称。
	 *
	 * @param name 博客名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取博客标题。
	 *
	 * @return 博客标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置博客标题。
	 *
	 * @param title 博客标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
}
