package com.springboot.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.bean.Student;
import com.springboot.service.StudentService;

/**
 * 学生信息管理控制器，提供学生数据的增删查操作
 *
 * @RestController 标注为RESTful风格的控制器，自动将返回结果转换为JSON格式
 */
@RestController
public class TestController {

    @Autowired
    private StudentService studentService;

    /**
     * 根据学号查询学生详细信息
     * @param sno 学生学号（唯一标识）
     * @return Student 包含学生基本信息的数据对象
     */
    @RequestMapping(value = "/querystudent", method = RequestMethod.GET)
    public Student queryStudentBySno(String sno) {
        return this.studentService.queryStudentBySno(sno);
    }

    /**
     * 获取所有学生信息的列表（以通用数据结构返回）
     * @return List<Map<String, Object>> 包含学生数据的集合，
     *         每个Map对应一个学生记录，键为字段名，值为字段数据
     */
    @RequestMapping(value = "/queryallstudent")
    public List<Map<String, Object>> queryAllStudent() {
        return this.studentService.queryStudentListMap();
    }

    /**
     * 新增学生记录
     * @param sno 学生学号（唯一标识）
     * @param name 学生姓名
     * @param sex 学生性别
     * @return int 受影响的行数（1-添加成功，0-添加失败）
     */
    @RequestMapping(value = "/addstudent", method = RequestMethod.GET)
    public int saveStudent(String sno,String name,String sex) {
        // 封装学生实体对象用于服务层处理
        Student student = new Student();
        student.setSno(sno);
        student.setName(name);
        student.setSex(sex);
        return this.studentService.add(student);
    }

    /**
     * 根据学号删除学生记录
     * @param sno 要删除的学生学号
     * @return int 受影响的行数（1-删除成功，0-删除失败）
     */
    @RequestMapping(value = "deletestudent", method = RequestMethod.GET)
    public int deleteStudentBySno(String sno) {
        return this.studentService.deleteBysno(sno);
    }
}
