package com.ladybird.hkd.model.pojo;

import com.ladybird.hkd.exception.BusinessException;
import com.ladybird.hkd.exception.ParamException;

import java.sql.Time;
import java.util.Date;

/**
 * @author Shen
 * @description: 考试实体类
 * @create: 2019-03-20
 */
public class Exam {
    private String exam_id;     //考试号
    private String course;     //考试课程
    private String teacher;     //老师
    private String grade;       //考试班级
    private String dept;        //考试专业
    private Date finish_time;      //预设开始时间
    private Date begin_time;    //考试开始时间
    private Integer duration;   //考试时长  （毫秒）
    private Integer state;      //状态 默认0 表示考试未开始

    public static void ValidExamIn(Exam exam) throws ParamException{
        if (exam.getCourse() == null || "".equals(exam.getCourse()))
            throw new ParamException("没有选择科目！");
        if (exam.getDept()==null || "".equals(exam.getDept().trim()))
            throw new ParamException("没有选择专业");
//        if (exam.getfinish_time() == null)
//            throw new ParamException("没有预定时间！");
        if (exam.getDuration()==null || exam.getDuration() == 0)
            throw new ParamException("没有考试时长！");
    }

    public Exam() {

    }

    public Exam(String exam_id, String course,String teacher, String grade, Date finish_time,Date begin_time, Integer duration, Integer state) {
        this.teacher = teacher;
        this.exam_id = exam_id;
        this.course = course;
        this.grade = grade;
        this.finish_time = finish_time;
        this.begin_time = begin_time;
        this.duration = duration;
        this.state = state;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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
