package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bean.BlogProperties;
import com.springboot.bean.ConfigBean;
import com.springboot.bean.TestConfigBean;

/**
 * IndexController 是一个Spring Boot的REST控制器，用于处理根路径("/")的请求。
 * 该控制器通过自动注入的方式获取配置信息，并返回一个包含测试配置信息的字符串。
 */
@RestController
public class IndexController {
	@Autowired
	private BlogProperties blogProperties;
	@Autowired
	private ConfigBean configBean;
	@Autowired
	private TestConfigBean testConfigBean;

	/**
	 * 处理根路径("/")的GET请求，返回一个包含测试配置信息的字符串。
	 *
	 * @return 返回一个字符串，格式为"name，age"，其中name和age来自TestConfigBean的配置。
	 */
	@RequestMapping("/")
	String index() {
		System.out.println(blogProperties.getName());
		System.out.println(blogProperties.getTitle());
		System.out.println(configBean);
		return testConfigBean.getName()+"，"+testConfigBean.getAge();
	}
}
