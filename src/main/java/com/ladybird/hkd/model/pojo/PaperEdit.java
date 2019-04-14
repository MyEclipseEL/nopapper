package com.ladybird.hkd.model.pojo;

import com.ladybird.hkd.exception.ParamException;

import java.io.Serializable;

/**
 * @author Shen
 * @description: 组卷
 * @create: 2019-03-14
 */
public class PaperEdit implements Serializable{
    private String id;                 //id
    private String course;              //课程
    private Integer single_count;      //单选题个数
    private Integer single_score;      //单选题分数
    private Integer multiple_count;    //多选题个数
    private Integer multiple_score;     //多选题分数
    private Integer checking_count;      //判断题个数
    private Integer checking_score;     //判断题分数
    private Integer duration;           //考试时长
    private String tip;                 //备注

    public PaperEdit() {
    }

    public PaperEdit(String id,String course, Integer single_count, Integer single_score, Integer multiple_count,
                     Integer multiple_score, Integer checking_count, Integer checking_score,Integer duration) {
        this.id = id;
        this.course = course;
        this.single_count = single_count;
        this.single_score = single_score;
        this.multiple_count = multiple_count;
        this.multiple_score = multiple_score;
        this.checking_count = checking_count;
        this.checking_score = checking_score;
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public static void checkParams(PaperEdit temp) throws ParamException {
        try {
            if (temp.getCourse() == null || "".equals(temp.getCourse().trim()))
                throw new ParamException("请选择课程！");
            if (temp.getSingle_count() == null || temp.getSingle_count() == 0)
                throw new ParamException("请选择单选题个数！");
            if (temp.getSingle_score() == null || temp.getSingle_score() == 0)
                throw new ParamException("请输入单选题分数！");
            if (temp.getMultiple_count() == null || temp.getMultiple_count() == 0)
                throw new ParamException("请选择多选题个数！");
            if (temp.getMultiple_score() == null || temp.getMultiple_score() == 0)
                throw new ParamException("请输入多选题分数！");
            if (temp.getChecking_count() == null || temp.getChecking_count() == 0)
                throw new ParamException("请选择判断题个数！");
            if (temp.getChecking_score() == null || temp.getChecking_score() == 0)
                throw new ParamException("请输入判断题分数！");
            if (temp.getDuration() == null || temp.getDuration() == 0)
                throw new ParamException("请输入考试时长");
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new ParamException("参数传递错误");
        }

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getSingle_count() {
        return single_count;
    }

    public void setSingle_count(Integer single_count) {
        this.single_count = single_count;
    }

    public Integer getMultiple_count() {
        return multiple_count;
    }

    public void setMultiple_count(Integer multiple_count) {
        this.multiple_count = multiple_count;
    }

    public Integer getChecking_count() {
        return checking_count;
    }

    public void setChecking_count(Integer checking_count) {
        this.checking_count = checking_count;
    }

    public Integer getSingle_score() {
        return single_score;
    }

    public void setSingle_score(Integer single_score) {
        this.single_score = single_score;
    }

    public Integer getMultiple_score() {
        return multiple_score;
    }

    public void setMultiple_score(Integer multiple_score) {
        this.multiple_score = multiple_score;
    }

    public Integer getChecking_score() {
        return checking_score;
    }

    public void setChecking_score(Integer checking_score) {
        this.checking_score = checking_score;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
