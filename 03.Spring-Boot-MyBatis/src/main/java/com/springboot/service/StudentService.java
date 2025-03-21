package com.springboot.service;

import com.springboot.bean.Student;

/**
 * 学生服务接口，定义了与学生相关的操作。
 */
public interface StudentService {

    /**
     * 添加学生信息。
     *
     * @param student 要添加的学生对象
     * @return 返回操作结果，通常为受影响的行数
     */
    int add(Student student);

    /**
     * 更新学生信息。
     *
     * @param student 要更新的学生对象
     * @return 返回操作结果，通常为受影响的行数
     */
    int update(Student student);

    /**
     * 根据学号删除学生信息。
     *
     * @param sno 要删除的学生的学号
     * @return 返回操作结果，通常为受影响的行数
     */
    int deleteBysno(String sno);

    /**
     * 根据学号查询学生信息。
     *
     * @param sno 要查询的学生的学号
     * @return 返回查询到的学生对象，如果未找到则返回null
     */
    Student queryStudentBySno(String sno);
}
