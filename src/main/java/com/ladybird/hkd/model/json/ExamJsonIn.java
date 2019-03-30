package com.ladybird.hkd.model.json;

import com.ladybird.hkd.exception.ParamException;

import java.util.Date;

/**
 * @author Shen
 * @description:
 * @create: 2019-03-17
 */
public class ExamJsonIn {
    private String exam_id;     //考试号
    private Integer course;     //考试课程
    private int[] grades;       //考试班级
    private String dept;        //考试专业
//    private Date pre_time;      //预设开始时间
    private Date begin_time;    //考试开始时间
    private Integer duration;   //考试时长  （毫秒）
    private Integer state;      //状态 默认0 表示考试未开始

    public static void ValidExamIn(ExamJsonIn exam) throws ParamException {
        if (exam.getCourse() == null || exam.getCourse() == 0)
            throw new ParamException("没有选择科目！");
        if (exam.getDept()==null || "".equals(exam.getDept().trim()))
            throw new ParamException("没有选择专业");
        if (exam.getGrades().length==0)
            throw new ParamException("没有选择班级！");
//        if (exam.getPre_time() == null)
//            throw new ParamException("没有预定时间！");
//        if ((new Date().getTime())>exam.getPre_time().getTime())
//            throw new ParamException("你设置了一个过去的时间！");
        if (exam.getDuration()==null || exam.getDuration() == 0)
            throw new ParamException("没有考试时长！");
    }

    public String getExam_id() {
        return exam_id;
    }

    public void setExam_id(String exam_id) {
        this.exam_id = exam_id;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public int[] getGrades() {
        return grades;
    }

    public void setGrades(int[] grades) {
        this.grades = grades;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

//    public Date getPre_time() {
//        return pre_time;
//    }
//
//    public void setPre_time(Date pre_time) {
//        this.pre_time = pre_time;
//    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
