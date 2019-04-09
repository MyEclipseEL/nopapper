package com.ladybird.hkd.model.example;

import com.ladybird.hkd.model.pojo.Course;

import java.util.Date;

/**
 * @author Shen
 * @description: 考试输出类
 * @create: 2019-03-20
 */
public class ExamExample {
    private String exam_id;     //考试号
    private Course course;     //考试课程
    private String grade;        //考试班级
    private DepartmentExample dept;    //考试专业
    private Date finish_time;      //预设开始时间
    private Date begin_time;    //考试开始时间
    private Integer duration;   //考试时长  （毫秒）
    private Integer state;      //考试状态 默认0 考试未开始 1 表示考试已经开始  -1 表示考试已经结束

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public DepartmentExample getDept() {
        return dept;
    }

    public void setDept(DepartmentExample dept) {
        this.dept = dept;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(Date finish_time) {
        this.finish_time = finish_time;
    }

    public Date getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(Date begin_time) {
        this.begin_time = begin_time;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
