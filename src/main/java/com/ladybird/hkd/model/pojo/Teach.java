package com.ladybird.hkd.model.pojo;

/**
 * @author Shen
 * @description: 教课实体
 * @create: 2019-03-19
 */
public class Teach {
    private String teach_id;        //标记
    private String teacher;         //教课老师
    private Integer course;         //教授课程
    private String grade;           //教授年级

    public String getTeach_id() {
        return teach_id;
    }

    public void setTeach_id(String teach_id) {
        this.teach_id = teach_id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
