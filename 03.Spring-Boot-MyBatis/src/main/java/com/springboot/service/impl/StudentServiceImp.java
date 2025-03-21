package com.springboot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.bean.Student;
import com.springboot.mapper.StudentMapper;
import com.springboot.service.StudentService;

/**
 * StudentServiceImp 类实现了 StudentService 接口，提供了对学生信息的增删改查操作。
 * 该类通过注入 StudentMapper 对象来与数据库进行交互。
 */
@Service("studentService")
public class StudentServiceImp implements StudentService{

	@Autowired
	private StudentMapper studentMapper;

	/**
	 * 添加学生信息到数据库。
	 *
	 * @param student 要添加的学生对象
	 * @return 返回操作结果，1 表示成功，0 表示失败
	 */
	@Override
	public int add(Student student) {
		return this.studentMapper.add(student);
	}

	/**
	 * 更新数据库中的学生信息。
	 *
	 * @param student 要更新的学生对象
	 * @return 返回操作结果，1 表示成功，0 表示失败
	 */
	@Override
	public int update(Student student) {
		return this.studentMapper.update(student);
	}

	/**
	 * 根据学号删除数据库中的学生信息。
	 *
	 * @param sno 要删除的学生的学号
	 * @return 返回操作结果，1 表示成功，0 表示失败
	 */
	@Override
	public int deleteBysno(String sno) {
		return this.studentMapper.deleteBysno(sno);
	}

	/**
	 * 根据学号查询学生信息。
	 *
	 * @param sno 要查询的学生的学号
	 * @return 返回查询到的学生对象，如果未找到则返回 null
	 */
	@Override
	public Student queryStudentBySno(String sno) {
		return this.studentMapper.queryStudentBySno(sno);
	}
}
