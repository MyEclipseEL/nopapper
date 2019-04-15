package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teacher;

import java.util.List;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-12
 */
public class TeachExample {
    private String teach_id;                    //标记
    private Teacher teacher;                    //教课老师
    private DepartmentExample dept;             //授课专业
    private Course course;                      //教授课程
    private List<GradeExample> grade;           //教授年级

    public String getTeach_id() {
        return teach_id;
    }

    public void setTeach_id(String teach_id) {
        this.teach_id = teach_id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public DepartmentExample getDept() {
        return dept;
    }

    public void setDept(DepartmentExample dept) {
        this.dept = dept;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<GradeExample> getGrade() {
        return grade;
    }

    public void setGrade(List<GradeExample> grade) {
        this.grade = grade;
    }
}
