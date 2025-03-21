package com.springboot.controller;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bean.Student;
import com.springboot.service.StudentService;

/**
 * TestController 类是一个Spring Boot的REST控制器，用于处理与学生信息相关的HTTP请求。
 */
@RestController
public class TestController {

	/**
	 * 自动注入的StudentService实例，用于处理学生相关的业务逻辑。
	 */
	@Resource
	private StudentService studentService;

	/**
	 * 根据学生学号查询学生信息。
	 *
	 * @param sno 学生的学号，作为查询条件。
	 * @return 返回与学号对应的Student对象，如果未找到则返回null。
	 */
	@RequestMapping( value = "/querystudent", method = RequestMethod.GET)
	public Student queryStudentBySno(String sno) {
		return this.studentService.queryStudentBySno(sno);
	}
}
