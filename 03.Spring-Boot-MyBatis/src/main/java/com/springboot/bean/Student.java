package com.springboot.bean;

import java.io.Serializable;

/**
 * Student类用于表示学生信息，实现了Serializable接口以支持序列化。
 * 该类包含学生的学号、姓名和性别等基本信息，并提供了相应的getter和setter方法。
 */
public class Student implements Serializable{

	/**
	 * serialVersionUID用于确保序列化时的版本一致性。
	 */
	private static final long serialVersionUID = -339516038496531943L;

	/**
	 * 学生的学号。
	 */
	private String sno;

	/**
	 * 学生的姓名。
	 */
	private String name;

	/**
	 * 学生的性别。
	 */
	private String sex;

	/**
	 * 获取学生的学号。
	 *
	 * @return 学生的学号。
	 */
	public String getSno() {
		return sno;
	}

	/**
	 * 设置学生的学号。
	 *
	 * @param sno 学生的学号。
	 */
	public void setSno(String sno) {
		this.sno = sno;
	}

	/**
	 * 获取学生的姓名。
	 *
	 * @return 学生的姓名。
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置学生的姓名。
	 *
	 * @param name 学生的姓名。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取学生的性别。
	 *
	 * @return 学生的性别。
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * 设置学生的性别。
	 *
	 * @param sex 学生的性别。
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}


}