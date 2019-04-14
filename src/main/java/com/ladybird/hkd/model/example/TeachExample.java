package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Course;
import com.ladybird.hkd.model.pojo.Teacher;

import java.io.Serializable;
import java.util.List;

/**
 * @author Shen
 * @description:
 * @create: 2019-04-12
 */
public class TeachExample implements Serializable {
    private String teach_id;                    //标记
    private Teacher teacher;                    //教课老师
    private DepartmentExample dept;             //授课专业
    private Course course;                      //教授课程
    private String grade;
    private List<GradeExample> grades;           //教授年级
    private String[] gradesIn;

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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public List<GradeExample> getGrades() {
        return grades;
    }

    public String[] getGradesIn() {
        return gradesIn;
    }

    public void setGradesIn(String[] gradesIn) {
        this.gradesIn = gradesIn;
    }

    public void setGrades(List<GradeExample> grades) {


        this.grades = grades;
    }
}
